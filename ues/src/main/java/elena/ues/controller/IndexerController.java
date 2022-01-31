package elena.ues.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import elena.ues.handler.Handler;
import elena.ues.model.Article;
import elena.ues.model.ArticleResponse;
import elena.ues.model.ErrandResponse;
import elena.ues.model.Seller;
import elena.ues.repository.ArticleRepository;
import elena.ues.repository.SellerRepository;
import elena.ues.service.BuyerService;
import elena.ues.service.Indexer;
import elena.ues.service.SellerService;

@RestController
@RequestMapping(value = "api/index")
public class IndexerController {

	@Autowired
	private Indexer indexer; 
	
	@Autowired
	private SellerRepository sellerRepository; 
	
	@Autowired 
	private ArticleRepository articleRepository; 
	
    @Autowired 
    private SellerService sellerService; 
    
    @Autowired 
    private BuyerService buyerService;
    
	   private File getResourceFilePath(String path) {
	        URL url = this.getClass().getClassLoader().getResource(path);
	        //System.out.println(">>>>> path >>>>>" + path);
	        File file = null;
	        try {
	            file = new File(url.toURI());
	        } catch (URISyntaxException e) {
	            file = new File(url.getPath());
	        }
	        return file;
	    }  
	   
	   
	   private String saveUploadedFile(MultipartFile file) throws IOException {
	        String retVal = null;
	        if (!file.isEmpty()) {
	            byte[] bytes = file.getBytes();
	            Path path = Paths
	                .get(getResourceFilePath("files").getAbsolutePath() + File.separator + file.getOriginalFilename());
	            Files.write(path, bytes);
	            retVal = path.toString();
	            
	            System.out.println("Putanja do fajla je " + path.toString());
	        }
	        return retVal;
	    }
	  
	    
	    @PostMapping("/add")
	    public ResponseEntity<String> multiUploadFileModel(@RequestParam String name, @RequestParam String description,
	        @RequestParam String firstname, @RequestParam MultipartFile file) throws Exception {
	    	
	        try {

	            indexUploadedFile(name, description, firstname, file);

	            try {
	                byte[] bytes = file.getBytes();

	                File newFile = new File("src\\main\\resources\\files\\");
	                if (!newFile.exists()) {
	                    newFile.mkdir();
	                }

	                File check = new File("src\\main\\resources\\files\\" + file.getOriginalFilename());

	                if (check.exists()) {
	                    throw new Exception("Already exists.");
	                }

	                if (!file.getOriginalFilename().contains(".pdf")) {
	                    //TODO: Handle exception
	                }

	                Path path = Paths.get(newFile + "\\" + file.getOriginalFilename());
	                Files.write(path, bytes);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } catch (IOException e) {
	            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	        }

	        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);
	    }
	    
	    
	    @GetMapping
		   public List<ArticleResponse> getAllArticles() {

			   List<ArticleResponse> articleResponses = new ArrayList<>();
			   for(Article article: articleRepository.findAll()) {
				   ArticleResponse articleResponse = new ArticleResponse();
				   articleResponse.setName(article.getName());
				   articleResponse.setDescription(article.getDescription());
				   
				   articleResponses.add(articleResponse);
			   }
			   
			   return articleResponses;
		   }
		
	    
		   @DeleteMapping("/{name}")
		    public void delete(@PathVariable String name) {
		        indexer.delete(name);
		    }
		   
		   
		    private void indexUploadedFile(String name, String description, String firstname, MultipartFile file)

		        throws IOException {
		        if (file.isEmpty()) {
		            return;
		        }
		        
		        System.out.println("File je " + file.getOriginalFilename());
		        
		        Seller seller = sellerRepository.findByFirstname(firstname);
		        //System.out.println("Seller je " + seller.getFirstname());
		        
		       
		        String fileName = saveUploadedFile(file);
		        if (fileName != null) {
		            Handler handler = new Handler();
		            Article article = handler.getArticle(new File(fileName));
		            article.setName(name);
		            article.setDescription(description);
		            //article.setSeller(seller.getFirstname());
		            article.setSeller(seller);
		            indexer.add(article);
		        }
		    }
		    
		    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		 
			@GetMapping("/reindex")
			    public ResponseEntity<String> index() throws IOException {
			        File dataDir = getResourceFilePath("files");
			        long start = new Date().getTime();
			        int numIndexed = indexer.index(dataDir);
			        long end = new Date().getTime();
			        String text = "Indexing " + numIndexed + " files took "
			            + (end - start) + " milliseconds";
			        return new ResponseEntity<String>(text, HttpStatus.OK);
			    }
			      
			     
			//kako postaviti da se indeksirani fajlovi izgenerisu u files folder? 
		  
		    
		    @GetMapping("/reindexArticlesElena/seller/{id}")
		    public ResponseEntity<ArticleResponse> indexArticlesElena(@PathVariable("id") Long id) throws IOException {
		    	long start = new Date().getTime(); 
		    	//int numIndexed = indexer.indexArticle(articleRepository.getBySeller(id));
		    	int numIndexed = indexer.indexArticle(sellerService.findArticlesForSeller(id));
		    	long end = new Date().getTime(); 
		    	List<ArticleResponse> sellerArticles = sellerService.findArticlesForSeller(id);
		    	System.out.println(">>> artikli koji se indeksiraju >>> " + sellerArticles.toString());
		    	return new ResponseEntity<ArticleResponse>(HttpStatus.OK);
		    }
		    
		    @GetMapping("reindexErrands/buyer/{id}")
		    public ResponseEntity<String> indexErrands(@PathVariable("id") Long id) throws IOException {
		    	long start = new Date().getTime(); 
		    	int numIndexed = indexer.indexErrand(buyerService.findErrandsForBuyer(id)); 
		    	long end = new Date().getTime(); 
		    	String text = "Indexing " + numIndexed + " objects took " + (end - start) + " miliseconds"; 
		    	return new ResponseEntity<String>(text, HttpStatus.OK);
		    }
		    
		    
		
}
