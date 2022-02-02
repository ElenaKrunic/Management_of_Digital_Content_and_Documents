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
import elena.ues.model.ErrandResponse;
import elena.ues.model.ItemModel;
import elena.ues.model.ItemResponse;
import elena.ues.model.OrderDTO;
import elena.ues.model.Seller;
import elena.ues.repository.ArticleModelRepository;
import elena.ues.repository.BuyerRepository;
import elena.ues.repository.ErrandModelRepository;
import elena.ues.repository.ItemModelRepository;
import elena.ues.repository.SellerRepository;
import elena.ues.repository.UserRepository;
import elena.ues.service.search.SearchUtil;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

@Service
public class ArticleService {
	
	@Autowired 
    private RestHighLevelClient client;
		
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
	
    private static final com.fasterxml.jackson.databind.ObjectMapper MAPPER = new ObjectMapper();

	private List<ArticleResponse> searchInternal(final SearchRequest request) {
		if (request == null) {
			return null; 
		}
		
		try {
			final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
			final SearchHit[] searchHits = response.getHits().getHits();
			final List<ArticleResponse> articles = new ArrayList<>(searchHits.length);
			for(SearchHit hit : searchHits) {
				System.out.println(" search hits " + searchHits.length);
				articles.add(MAPPER.readValue(hit.getSourceAsString(), ArticleResponse.class));
			}
			
			return articles;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null; 
	}


	public List<ArticleResponse> getAllGteArticles(double price) {
		final SearchRequest request = SearchUtil.buildGteArticlesSearchRequest("artikli", "price", price);
		return searchInternal(request);
	}

	public List<ArticleResponse> getAllGtArticles(double price) {
		final SearchRequest request = SearchUtil.buildGtArticlesSearchRequest("artikli", "price", price);
		return searchInternal(request);
	}

	public List<ArticleResponse> getAllLteArticles(double price) {
		final SearchRequest request = SearchUtil.buildLteArticlesSearchRequest("artikli", "price", price);
		return searchInternal(request);
	}
	
	public List<ArticleResponse> getAllLtArticles(double price) {
		final SearchRequest request = SearchUtil.buildLtSearchRequest("artikli", "price", price);
		return searchInternal(request);
	}


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


		public String orderOneArticle(OrderDTO orderDTO, String address) {		
		ArticleModel article = articleModelRepository.getById(orderDTO.getArticleFromDB().getId());
		System.out.println(">>> id proizvoda je >>>" + article.getId());
		
		ErrandModel errand = new ErrandModel(); 
		errand.setAnonymousComment(false);
		errand.setArchivedComment(false);
		errand.setBuyer(null);
		errand.setComment(null);
		errand.setDelivered(false);
		errand.setGrade(0);
		errand.setId(null);
		errand.setOrderedAtDate(null);
		
		ItemModel item = new ItemModel(); 
		item.setArticle(article);
		item.setErrand(errand);
		item.setQuantity(orderDTO.getQuantity());
		
		article = articleModelRepository.save(article);
		errand = errandModelRepository.save(errand);
		item = itemModelRepository.save(item);
		return "You successfully ordered an article! ";
				
	}

		public String orderSecondArticle(Long id, Integer quantity, String string) {
			
			ArticleModel article = articleModelRepository.getById(id);
			//System.out.println(">>> id proizvoda je >>>" + article.getId());
			
			ErrandModel errand = new ErrandModel(); 
			errand.setAnonymousComment(false);
			errand.setArchivedComment(false);
			errand.setBuyer(null);
			errand.setComment(null);
			errand.setDelivered(false);
			errand.setGrade(0);
			errand.setId(null);
			errand.setOrderedAtDate(null);
			
			ItemModel item = new ItemModel(); 
			item.setArticle(article);
			item.setErrand(errand);
			item.setQuantity(quantity);
			
			article = articleModelRepository.save(article);
			errand = errandModelRepository.save(errand);
			item = itemModelRepository.save(item);
			return "You successfully ordered an article! ";
		}

		/*
	public String orderOneArticle(ArticleResponse articleResponse, String address) {
		Buyer buyer = buyerRepository.findByAddress(address);
		
		ArticleModel article = new ArticleModel();
		article.setDescription(articleResponse.getDescription());
		article.setName(articleResponse.getName());
		article.setPath(articleResponse.getPath());
		article.setPrice(articleResponse.getPrice());
		return null;
	}
	*/
}
 