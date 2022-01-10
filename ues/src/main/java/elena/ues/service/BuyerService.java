package elena.ues.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.ues.model.Buyer;
import elena.ues.model.BuyerRequest;
import elena.ues.model.ErrandModel;
import elena.ues.model.ErrandResponse;
import elena.ues.model.Seller;
import elena.ues.model.SellerRequest;
import elena.ues.model.User;
import elena.ues.repository.BuyerRepository;
import elena.ues.repository.ErrandModelRepository;
import elena.ues.repository.RoleRepository;
import elena.ues.repository.UserRepository;

@Service
public class BuyerService {
	
	@Autowired
	private BuyerRepository buyerRepository; 
	
	@Autowired 
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired 
	private ErrandModelRepository errandModelRepository; 

	public List<ErrandResponse> findErrandsForBuyer(Long id) {
		List<ErrandModel> errands = errandModelRepository.findErrandsByBuyerId(id);
		List<ErrandResponse> errandResponse = new ArrayList<>();
		
		for(ErrandModel errand : errands) {
			errandResponse.add(new ErrandResponse(errand));
		}
		
		return errandResponse;
	}

	public void changePassword(BuyerRequest dto, String name) {
		Buyer buyer = buyerRepository.findByEmail(name);
		
		if(buyer == null) {
			System.out.println("Ne postoji kupac");
		}
		
		buyer = new Buyer(); 
		buyer.setAddress(dto.getAddress());
		buyer.setBlocked(false);
		buyer.setFirstname(dto.getFirstname());
		buyer.setLastname(dto.getLastname());
		buyer.setPassword(dto.getPass());
		buyer.setUsername(dto.getUsername());
		buyer.setEmail(dto.getEmail());
		
		User user = buyer; 
		user.setRoles(roleRepository.findByName("BUYER"));
		userRepository.save(user);
		buyerRepository.save(buyer);
		
	}

	public BuyerRequest getMyBuyerProfile(String name) throws Exception {
		Buyer user = buyerRepository.findByEmail(name);
		if(user==null){
			throw new Exception("This user doesn't exists");
		}
		BuyerRequest tmp = new BuyerRequest();
		tmp.setEmail(user.getEmail());
		tmp.setFirstname(user.getFirstname());
		tmp.setPass(user.getPassword());
		tmp.setLastname(user.getLastname());
		tmp.setAddress(user.getAddress());
		tmp.setUsername(user.getUsername());
		
		return tmp;
		
	}
}
