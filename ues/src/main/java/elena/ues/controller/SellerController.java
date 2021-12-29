package elena.ues.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.ues.model.ArticleResponse;
import elena.ues.model.LoginRequest;
import elena.ues.security.util.JwtUtil;
import elena.ues.service.SellerService;

@RestController
@RequestMapping(value="/api/sellers")
public class SellerController {
	
	@Autowired
	private JwtUtil jwtUtil; 
	
	@Autowired
	private SellerService sellerService;
	 
	@GetMapping(value="/articles/{id}")
	private List<ArticleResponse> getArticlesForSeller(@PathVariable("id") Long id) {
		return sellerService.findArticlesForSeller(id);
	}
}
