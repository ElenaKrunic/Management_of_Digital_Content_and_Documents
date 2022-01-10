package elena.ues.service;

import java.security.Principal;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import elena.ues.model.Buyer;
import elena.ues.model.BuyerRequest;
import elena.ues.model.CustomPrincipal;
import elena.ues.model.Seller;
import elena.ues.model.SellerRequest;
import elena.ues.model.User;
import elena.ues.repository.BuyerRepository;
import elena.ues.repository.RoleRepository;
import elena.ues.repository.SellerRepository;
import elena.ues.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private SellerRepository sellerRepository; 
	
	@Autowired 
	private BuyerRepository buyerRepository;
	
	@Autowired 
	private RoleRepository roleRepository;
	
	 public String registerSeller(SellerRequest sellerRequest) throws Exception {
		Seller seller = sellerRepository.findByEmail(sellerRequest.getEmail());
		if(seller != null) {
			throw new Exception("User vec postoji!");
		}
		
		seller = new Seller();
		seller.setAddress(sellerRequest.getAddress());
		seller.setBlocked(false);
		seller.setEmail(sellerRequest.getEmail());
		seller.setFirstname(sellerRequest.getFirstname());
		seller.setLastname(sellerRequest.getLastname());
		seller.setOperatesSince(sellerRequest.getOperatesSince());
		seller.setPassword(sellerRequest.getPass());
		seller.setStoreName(sellerRequest.getStoreName());
		seller.setUsername(sellerRequest.getUsername());
		
		System.out.println("seler je " + seller.getEmail());
		
		User user = seller;
		user.setRoles(roleRepository.findByName("SELLER"));
		System.out.println("user je " + user.getFirstname());
		userRepository.save(user);
		sellerRepository.save(seller);
		
		return "Prodavac registrovan";
	}

	public String registerBuyer(BuyerRequest buyerRequest) throws Exception {
		Buyer buyer = buyerRepository.findByEmail(buyerRequest.getEmail()); 
		
		if(buyer != null) {
			throw new Exception("Kupac vec postoji!");
		}
		
		buyer = new Buyer(); 
		buyer.setAddress(buyerRequest.getAddress());
		buyer.setBlocked(false);
		buyer.setFirstname(buyerRequest.getFirstname());
		buyer.setLastname(buyerRequest.getLastname());
		buyer.setPassword(buyerRequest.getPass());
		buyer.setUsername(buyerRequest.getUsername());
		buyer.setEmail(buyerRequest.getEmail());
		
		User user = buyer; 
		user.setRoles(roleRepository.findByName("BUYER"));
		userRepository.save(user);
		buyerRepository.save(buyer);
		
		return "Kupac registrovan";
		
	}
}
