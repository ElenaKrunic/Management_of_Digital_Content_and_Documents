package elena.ues.service;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import elena.ues.model.ArticleModel;
import elena.ues.model.ArticleResponse;
import elena.ues.model.Buyer;
import elena.ues.model.ErrandModel;
import elena.ues.model.ErrandRequest;
import elena.ues.model.ErrandResponse;
import elena.ues.model.ItemModel;
import elena.ues.model.User;
import elena.ues.repository.ArticleModelRepository;
import elena.ues.repository.BuyerRepository;
import elena.ues.repository.ErrandModelRepository;
import elena.ues.repository.ItemModelRepository;
import elena.ues.repository.UserRepository;
import elena.ues.service.search.SearchUtil;

@Service
public class ErrandService {

	@Autowired 
    private RestHighLevelClient client;
		
	@Autowired
	private ErrandModelRepository errandModelRepository;

	@Autowired 
	private BuyerRepository buyerRepository; 
	
	@Autowired 
	private ItemModelRepository itemModelRepository; 
	
	@Autowired 
	private ArticleModelRepository articleModelRepository; 
	
    private static final com.fasterxml.jackson.databind.ObjectMapper MAPPER = new ObjectMapper();
	
	public List<ErrandResponse> myErrands(String name) {
		Buyer buyer = buyerRepository.findByEmail(name);
				
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
			tmp.setGrade(errand.getGrade());
			
			response.add(tmp);
		}
		return response;
	}
	
	public List<ErrandResponse> myErr(Long id) {
		Buyer buyer = buyerRepository.findById(id).orElseThrow();
		
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
			tmp.setGrade(errand.getGrade());
			tmp.setDelivered(errand.isDelivered());
			
			response.add(tmp);
		}
		return response;
		
	}
	
	private List<ErrandResponse> searchInternal(final SearchRequest request) {
		if (request == null) {
			return null; 
		}
		
		try {
			final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
			final SearchHit[] searchHits = response.getHits().getHits();
			final List<ErrandResponse> errands = new ArrayList<>(searchHits.length);
			for(SearchHit hit : searchHits) {
				System.out.println(" >>> search hits >>> " + searchHits.length);
				errands.add(MAPPER.readValue(hit.getSourceAsString(), ErrandResponse.class));
			}
			
			return errands;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null; 
	}

	public List<ErrandResponse> getAllGteErrands(int grade) {
		final SearchRequest request = SearchUtil.buildGteErrandsSearchRequest("porudzbine", "grade", grade);
		return searchInternal(request);
	}

	public List<ErrandResponse> getAllGtErrands(int grade) {
		final SearchRequest request = SearchUtil.buildGtErrandsSearchRequest("porudzbine", "grade", grade);
		return searchInternal(request);
	}

	public List<ErrandResponse> getAllLteErrands(int grade) {
		final SearchRequest request = SearchUtil.buildLteErrandsSearchRequest("porudzbine", "grade", grade);
		return searchInternal(request);
	}

	public List<ErrandResponse> getAllLtErrands(int grade) {
		final SearchRequest request = SearchUtil.buildLtErrandsSearchRequest("porudzbine", "grade", grade);
		return searchInternal(request);
	}

	public String makeAnErrand(ErrandRequest errandRequest) {
		Buyer buyer = buyerRepository.findById(errandRequest.getBuyerId()).orElseThrow();
		
		ErrandModel errand = new ErrandModel();
		errand.setBuyer(buyer);
		
		ArticleModel article = new ArticleModel(); 
		article.setName(errandRequest.getArticleName());
		article.setPrice(errandRequest.getPrice());
		
		ItemModel item = new ItemModel();
		item.setQuantity(errandRequest.getQuantity());
		item.setArticle(article);
		item.setErrand(errand);
		
		errand = errandModelRepository.save(errand);
		
		return "Uspjesno napravljena porudzbina";
	}
	
	public List<ErrandResponse> getNonDelivered(boolean isDelivered) {
		
		List<ErrandResponse> response = new ArrayList<>();
		List<ErrandModel> errands = errandModelRepository.findAllByIsDelivered(isDelivered);
		
		for(ErrandModel errand : errands) {
			
				ErrandResponse tmp = new ErrandResponse();
				tmp.setId(errand.getId());
				tmp.setOrderedAtDate(errand.getOrderedAtDate());
				tmp.setAnonymousComment(errand.isAnonymousComment());
				tmp.setArchivedComment(errand.isArchivedComment());
				tmp.setComment(errand.getComment());
				tmp.setGrade(errand.getGrade());
				tmp.setDelivered(errand.isDelivered());
				
				response.add(tmp);
			
		}
		return response;
		
	}

	public String changeDeliveryStatus(Long id) {
		ErrandModel errand = errandModelRepository.getById(id); 
		
		if(errand == null) {
			return null;
		}
		errand.setDelivered(true);
		errandModelRepository.save(errand); 
		
		return "Uspjesno izmijenjen status narudzbe";
	}

	public String deliveryComment(Long id) {
		ErrandModel errand = errandModelRepository.getById(id); 
		
		if(errand == null) {
			return null;
		}
		errand.setDelivered(true);
		errandModelRepository.save(errand); 
		
		return "Uspjesno izmijenjen status narudzbe";
	}


}
