package elena.ues.service.search;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elena.ues.indexing.analysers.SerbianAnalyzer;
import elena.ues.model.Article;
import elena.ues.model.ArticleModel;
import elena.ues.model.ArticleResponse;
import elena.ues.model.ArticleResultData;
import elena.ues.model.ErrandResponse;
import elena.ues.model.RequiredHighlight;
import elena.ues.repository.ArticleModelRepository;
import elena.ues.repository.ArticleRepository;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

@Service
public class ResultRetriever {

	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired 
	ArticleModelRepository articleModelRepository;
	
	public ResultRetriever() {}
	
	private static int maxHits = 10;

	private static JestClient client;

	 static {
	        JestClientFactory factory = new JestClientFactory();
	        factory.setHttpClientConfig(new HttpClientConfig
	                .Builder("http://localhost:9200")
	                .multiThreaded(true)
	                .build());
	        ResultRetriever.client = factory.getObject();
	    }

	    public static void setMaxHits(int maxHits) {
	        ResultRetriever.maxHits = maxHits;
	    }

	    public static int getMaxHits() {
	        return ResultRetriever.maxHits;
	    }

	
	// u ovoj metodi pretrazujem indeks article, meni treba pretraga entiteta jer njega i indeksiram 
	/*
	public List<ArticleResponse> getResults(org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}
			
		List<ArticleResponse> results = new ArrayList<ArticleResponse>();
		List<Article> articles = articleRepository.searchArticle(query);
       
        for (Article article : articles) {
        	results.add(new ArticleResponse(article.getName(), article.getDescription()));
		}
        
		return results;
	}
	*/
	
	
	/*
	public List<ArticleResponse> getResults(org.elasticsearch.index.query.QueryBuilder query, List<RequiredHighlight> rh) {
		
		//System.out.println(">>> query builder je : >>> " + query.getName());
		//System.out.println(">>> rh je: >>> " + rh.size());
		
		List<ArticleResponse> results = new ArrayList<ArticleResponse>();
		
		List<ArticleModel> articles = articleModelRepository.searchArticles(query);
		
		//System.out.println(">>> repozitorijum artikli su >>> " + articles.size());
		
		for(ArticleModel article : articles) {
			results.add(new ArticleResponse(article.getName(), article.getDescription()));
		}
		
		return results; 	
	 }
	 */
	
	public List<ArticleResponse> getArticleResults(org.elasticsearch.index.query.QueryBuilder query, List<RequiredHighlight> highlights) throws Exception {
		
		if(query == null) {
			throw new Exception();
		}
		
		List<ArticleResponse> results = new ArrayList<>();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(query);
		
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("name"); 
		highlightBuilder.field("description");
		highlightBuilder.field("price");
		highlightBuilder.field("id");
		highlightBuilder.preTags("<spam style='color:red'>").postTags("</spam>");
        highlightBuilder.fragmentSize(200);
        searchSourceBuilder.highlighter(highlightBuilder);
                
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("artikli").addType("artikal").build();       
        SearchResult result; 
   
        try {
        	result = client.execute(search); 
        	 List<SearchResult.Hit<ArticleResponse, Void>> hits = result.getHits(ArticleResponse.class);        	  
        	 ArticleResponse rd = new ArticleResponse();

              for (SearchResult.Hit<ArticleResponse, Void> hit : hits) {
                  for (String hf : hit.highlight.keySet() ) {
                      for (RequiredHighlight rh : highlights) {
                          if(hf.equals(rh.getFieldName())){
                              String highlight = "";
                              highlight += hit.highlight.get(hf).toString();
                              //System.out.println(">>> highlight je >>> " + highlight.toLowerCase());
                          }
                      }
                  }
                  rd.setName(hit.source.getName());
                  rd.setDescription(hit.source.getDescription());
                  rd.setPrice(hit.source.getPrice());
                  rd.setId(hit.source.getId());
                  results.add(rd);
              }
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return results;
	}
	
	public List<ErrandResponse> getErrandResults(org.elasticsearch.index.query.QueryBuilder query, List<RequiredHighlight> highlights) throws Exception {
		
		if(query == null) {
			throw new Exception();
		}
		
		List<ErrandResponse> results = new ArrayList<>();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(query);
		
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("orderedAtDate"); 
		highlightBuilder.field("isDelivered");
		highlightBuilder.field("grade");
		highlightBuilder.field("comment");
		highlightBuilder.preTags("<spam style='color:red'>").postTags("</spam>");
        highlightBuilder.fragmentSize(200);
        searchSourceBuilder.highlighter(highlightBuilder);
                
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("artikli").addType("artikal").build();       
        SearchResult result; 
   
        try {
        	result = client.execute(search); 
        	 List<SearchResult.Hit<ErrandResponse, Void>> hits = result.getHits(ErrandResponse.class);        	  
        	 ErrandResponse rd = new ErrandResponse();

              for (SearchResult.Hit<ErrandResponse, Void> hit : hits) {
                  for (String hf : hit.highlight.keySet() ) {
                      for (RequiredHighlight rh : highlights) {
                          if(hf.equals(rh.getFieldName())){
                              String highlight = "";
                              highlight += hit.highlight.get(hf).toString();
                              //System.out.println(">>> highlight je >>> " + highlight.toLowerCase());
                          }
                      }
                  }
                  
                  rd.setOrderedAtDate(hit.source.getOrderedAtDate());
                  rd.setDelivered(hit.source.isDelivered());
                  rd.setGrade(hit.source.getGrade());
                  rd.setComment(hit.source.getComment());
                  results.add(rd);
              }
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return results;
	}
	
}
