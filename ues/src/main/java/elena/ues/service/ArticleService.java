package elena.ues.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.ues.model.ArticleResponse;
import elena.ues.model.Buyer;
import elena.ues.model.Seller;
import elena.ues.repository.ArticleModelRepository;
import elena.ues.repository.BuyerRepository;
import elena.ues.repository.SellerRepository;
import elena.ues.repository.UserRepository;

@Service
public class ArticleService {
	
	@Autowired
	private BuyerRepository buyerRepository; 
	
	@Autowired 
	private SellerRepository sellerRepository; 
	
	@Autowired 
	private UserRepository userRepository; 
	
	@Autowired ArticleModelRepository articleModelRepository; 

	/*
	public String orderArticle(ArticleResponse response, String address) {
		
		Buyer buyer = buyerRepository.findByAddress(address);
		Seller seller = sellerRepository.findById(response.getSellerID()).orElseThrow();
		
		Article article = new Article(); 
		return null;
	}
	*/

}
