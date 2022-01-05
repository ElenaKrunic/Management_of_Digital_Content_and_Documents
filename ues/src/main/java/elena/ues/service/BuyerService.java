package elena.ues.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.ues.model.ErrandModel;
import elena.ues.model.ErrandResponse;
import elena.ues.repository.BuyerRepository;
import elena.ues.repository.ErrandModelRepository;

@Service
public class BuyerService {
	
	@Autowired
	private BuyerRepository buyerRepository; 
	
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
}
