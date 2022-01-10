package elena.ues.service;

import java.security.Principal;
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
import elena.ues.model.SellerRequest;
import elena.ues.model.User;
import elena.ues.repository.ArticleModelRepository;
import elena.ues.repository.ArticleRepository;
import elena.ues.repository.RoleRepository;
import elena.ues.repository.SellerRepository;
import elena.ues.repository.UserRepository;

@Service
public class SellerService {
	
	@Autowired
	private SellerRepository sellerRepository; 
	
	@Autowired 
	private RoleRepository roleRepository; 
	
	@Autowired
	private UserRepository userRepository;
	
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

	public void changePassword(SellerRequest dto, String name) {
		Seller seller = sellerRepository.findByEmail(name);
		
		if(seller == null) {
			System.out.println("Ne postoji prodavac");
		}
		
		seller = new Seller();
		seller.setAddress(dto.getAddress());
		seller.setBlocked(false);
		seller.setEmail(dto.getEmail());
		seller.setFirstname(dto.getFirstname());
		seller.setLastname(dto.getLastname());
		seller.setOperatesSince(dto.getOperatesSince());
		seller.setPassword(dto.getPass());
		seller.setStoreName(dto.getStoreName());
		seller.setUsername(dto.getUsername());
				
		User user = seller;
		user.setRoles(roleRepository.findByName("SELLER"));
		userRepository.save(user);
		sellerRepository.save(seller);
				
	}

	public SellerRequest getMySellerProfile(String name) throws Exception {
		Seller user = sellerRepository.findByEmail(name);
		if(user==null){
			throw new Exception("This user doesn't exists");
		}
		SellerRequest tmp = new SellerRequest();
		tmp.setEmail(user.getEmail());
		tmp.setFirstname(user.getFirstname());
		tmp.setPass(user.getPassword());
		tmp.setLastname(user.getLastname());
		tmp.setAddress(user.getAddress());
		tmp.setOperatesSince(user.getOperatesSince());
		tmp.setStoreName(user.getStoreName());
		tmp.setUsername(user.getUsername());
		
		return tmp;
		
	}
}
