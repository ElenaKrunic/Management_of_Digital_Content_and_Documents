package elena.ues.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.google.common.net.HttpHeaders;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;

import elena.ues.model.Article;
import elena.ues.model.ArticleModel;
import elena.ues.model.ArticleResponse;
import elena.ues.model.ErrandResponse;
import elena.ues.model.ItemResponse;
import elena.ues.model.OrderDTO;
import elena.ues.model.Seller;
import elena.ues.model.StringResponse;
import elena.ues.model.User;
import elena.ues.repository.ArticleModelRepository;
import elena.ues.repository.ArticleRepository;
import elena.ues.repository.SellerRepository;
import elena.ues.repository.UserRepository;
import elena.ues.service.ArticleService;

@RestController
@RequestMapping(value = "/api/articles")
@CrossOrigin
public class ArticleController {

	@Autowired
	ServletContext servletContext; 
	
	private TemplateEngine templateEngine; 
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired 
	private SellerRepository sellerRepository;
	
	@Autowired 
	private ArticleModelRepository articleModelRepository; 
	
	@Autowired 
	private ArticleService articleService; 
	
	@Autowired
	private ArticleRepository articleRepository;
	
		
	public ArticleController(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
	
	@GetMapping(value="/all")
	public ResponseEntity<List<ArticleResponse>> getArticles() {
		List<ArticleModel> articles =  (List<ArticleModel>) articleModelRepository.findAll();
		List<ArticleResponse> response = new ArrayList<>();
		
		
		for(ArticleModel article : articles) {
			response.add(new ArticleResponse(article));
		}
		
		return new ResponseEntity<List<ArticleResponse>>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="article/{id}")
	public ResponseEntity<ArticleResponse> getOne(@PathVariable("id") Long id) {
		ArticleModel article = articleModelRepository.getById(id);
		
		if(article == null) {
			return new ResponseEntity<ArticleResponse>(HttpStatus.NOT_FOUND);
		}
				
		return new ResponseEntity<ArticleResponse>(new ArticleResponse(article), HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json", value="/saveArticle/{id}")
	public ResponseEntity<ArticleResponse> saveArticle(@RequestBody ArticleResponse response, @PathVariable("id") Long id) {
		
		ArticleModel article = new ArticleModel();		
		Seller seller = sellerRepository.findById(id).orElseThrow();
		
		article.setDescription(response.getDescription());
		article.setName(response.getName());
		article.setPath(response.getPath());
		article.setPrice(response.getPrice());
		article.setSeller(seller);
		
		article = articleModelRepository.save(article);
		
		return new ResponseEntity<ArticleResponse>(new ArticleResponse(article), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/updateArticle/{id}")
	public ResponseEntity<ArticleResponse> updateArticle(@RequestBody ArticleResponse response, @PathVariable("id") Long id) {
		
		ArticleModel article = articleModelRepository.findById(id).orElseThrow();
				
		System.out.println(">>>> response article >>> " + response.getDescription());
		
		article.setDescription(response.getDescription());
		article.setName(response.getName());
		article.setPath(response.getPath());
		article.setPrice(response.getPrice());
		
		System.out.println(">>> article >>>>" + article.getName());
		
		article = articleModelRepository.save(article);
		
		return new ResponseEntity<ArticleResponse>(new ArticleResponse(article), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteArticle/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
		ArticleModel articleModel = articleModelRepository.findById(id).orElseThrow();
		
		if(articleModel != null) {
			articleModelRepository.deleteById(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(path="/pdf/{id}")
	public ResponseEntity<?> getPdfForArticle(@PathVariable("id") Long id, HttpServletRequest req, HttpServletResponse res) {
		ArticleModel article = articleModelRepository.getById(id);
		
		System.out.println(">>>> id artikla je >>>> " + article.getId());
		WebContext context = new WebContext(req,res, servletContext); 
		
		context.setVariable("articleEntry", article);
		String articleHTML = templateEngine.process("article", context);
		
		ByteArrayOutputStream target = new ByteArrayOutputStream();
		ConverterProperties converterProperties = new ConverterProperties(); 
		converterProperties.setBaseUri("http://localhost:8085");
		
		
		HtmlConverter.convertToPdf(articleHTML, target, converterProperties);
		byte[] bytes = target.toByteArray();
		
		System.out.println(">>> bytes su >>> " + bytes.toString());
				
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=article.pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(bytes);
	}
	
		@RequestMapping(method = RequestMethod.POST ,value="/orderArticle")
		public ResponseEntity<StringResponse> orderArticle(@RequestParam("id") Long id, @RequestParam("quantity") Integer quantity, Principal principal) {
			try {
				String message = articleService.orderSecondArticle(id, quantity, "elenakrunic@gmail.com");
				System.out.println(">>> id je >>>" + id);
				System.out.println(">>> quantity je >>> " + quantity);
				return new ResponseEntity<> (new StringResponse(message), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<> (new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
			}
		}
		
	
}
