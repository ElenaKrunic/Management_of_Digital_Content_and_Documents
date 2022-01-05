package elena.ues.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.ues.model.Buyer;
import elena.ues.model.ErrandModel;
import elena.ues.model.ErrandResponse;
import elena.ues.model.User;
import elena.ues.repository.BuyerRepository;
import elena.ues.repository.ErrandModelRepository;
import elena.ues.repository.UserRepository;

@Service
public class ErrandService {

	@Autowired
	private ErrandModelRepository errandModelRepository;

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private BuyerRepository buyerRepository;
	
	public List<ErrandResponse> myErrands(String name) {
		
		//User user = userRepository.findByUsername(name).orNull();
		Buyer buyer = buyerRepository.findByEmail(name);
		
		//System.out.println("buyer je " + buyer.getEmail());
		
		if(buyer == null) {
			return null;
		}
		
		List<ErrandResponse> response = new ArrayList<>();
		List<ErrandModel> errands = errandModelRepository.findAllByBuyer(buyer);
		
		for(ErrandModel errand : errands) {
			ErrandResponse tmp = new ErrandResponse();
			tmp.setId(errand.getId());
			tmp.setOrderedAtDate(errand.getOrderedAtDate());
			tmp.setAnonymousComment(errand.isAnonymousComment());
			tmp.setArchivedComment(errand.isArchivedComment());
			tmp.setComment(errand.getComment());
			tmp.setDelivered(errand.isDelivered());
			tmp.setGrade(errand.getGrade());
			
			response.add(tmp);
			//System.out.println(">>> duzina niza je >>> " + response.size());
		}
		
		return response;
	}
}
