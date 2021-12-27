package elena.ues.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import elena.ues.model.Article;
import elena.ues.model.ArticleModel;
import elena.ues.model.ArticleResponse;
import elena.ues.model.ArticleResultData;
import elena.ues.model.CustomPrincipal;
import elena.ues.model.Seller;
import elena.ues.repository.ArticleModelRepository;
import elena.ues.repository.ArticleRepository;
import elena.ues.repository.SellerRepository;

@Service
public class SellerService {
	
	@Autowired
	private SellerRepository sellerRepository; 
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired 
	private ArticleModelRepository articleModelRepository; 
	
	public List<ArticleResponse> findArticlesForSeller(Long id) {
		List<ArticleModel> articles = articleModelRepository.findArticlesBySellerId(id);
		List<ArticleResponse> articleResponse = new ArrayList<>();
		
		for (ArticleModel article : articles) {
			articleResponse.add(new ArticleResponse(article));
		}
		
		return articleResponse; 
	}

	public UserDetails loadByUsernameAndPassword(String username, String password) {
		Seller seller = sellerRepository.loadByUsernameAndPassword(username, password);
		System.out.println("seller" + seller.getFirstname());
        return new CustomPrincipal(seller.getId(), seller.getUsername(), seller.getFirstname(), seller.getLastname(), seller.getPassword(), new HashSet<>());
		
	}
	
	

}
