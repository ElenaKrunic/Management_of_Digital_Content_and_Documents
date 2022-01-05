package elena.ues.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

import elena.ues.model.ArticleModel;
import elena.ues.model.ArticleResponse;
import elena.ues.model.ErrandResponse;
import elena.ues.model.ItemResponse;
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
		
		
		System.out.println(">>> response je duzine >>> "  + response.size());
		return new ResponseEntity<List<ArticleResponse>>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="article/{id}")
	public ResponseEntity<ArticleResponse> getOne(@PathVariable("id") Long id) {
		//ArticleModel article = articleModelRepository.findById(id).orElseThrow();
		ArticleModel article = articleModelRepository.getById(id);
		
		if(article == null) {
			return new ResponseEntity<ArticleResponse>(HttpStatus.NOT_FOUND);
		}
		
		//System.out.println(">>> article je >>> " + article.getName());
		
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
	
	
	@PostMapping("/orderArticle")
	public ResponseEntity<?> order(@RequestBody ArticleResponse articleResponse, 
			@RequestBody ErrandResponse errandResponse, 
			@RequestBody ItemResponse itemResponse, Principal principal) {
		try {
			String message = articleService.orderArticle(articleResponse, errandResponse, itemResponse, "elenakrunic@gmail.com");
			return new ResponseEntity<> (new StringResponse(message), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<> (new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
