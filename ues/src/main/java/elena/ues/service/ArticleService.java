package elena.ues.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.ues.model.ArticleModel;
import elena.ues.model.ArticleResponse;
import elena.ues.model.Buyer;
import elena.ues.model.ErrandModel;
import elena.ues.model.ErrandResponse;
import elena.ues.model.ItemModel;
import elena.ues.model.ItemResponse;
import elena.ues.model.Seller;
import elena.ues.repository.ArticleModelRepository;
import elena.ues.repository.BuyerRepository;
import elena.ues.repository.ErrandModelRepository;
import elena.ues.repository.ItemModelRepository;
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
	
	@Autowired
	ArticleModelRepository articleModelRepository;
	
	@Autowired 
	ErrandModelRepository errandModelRepository; 
	
	@Autowired 
	ItemModelRepository itemModelRepository; 


	public String orderArticle(ArticleResponse articleResponse, ErrandResponse errandResponse, ItemResponse itemResponse, String address) {
		
		Buyer buyer = buyerRepository.findByAddress(address);
		Seller seller = sellerRepository.findById(articleResponse.getSellerID()).orElseThrow();
		
		ArticleModel article = new ArticleModel();
		article.setDescription(articleResponse.getDescription());
		article.setName(articleResponse.getName());
		article.setPath(articleResponse.getPath());
		article.setPrice(articleResponse.getPrice());
		article.setSeller(seller);
		
		ErrandModel errand = new ErrandModel(); 
		errand.setBuyer(buyer);
		errand.setDelivered(true);
		errand.setOrderedAtDate(errandResponse.getOrderedAtDate());
		
		ItemModel item = new ItemModel();
		item.setArticle(article);
		item.setErrand(errand);
		item.setQuantity(itemResponse.getQuantity());
		
		article = articleModelRepository.save(article);
		errand = errandModelRepository.save(errand);
		item = itemModelRepository.save(item);
		
		return "You successfully ordered an article! ";
	}
	

}
