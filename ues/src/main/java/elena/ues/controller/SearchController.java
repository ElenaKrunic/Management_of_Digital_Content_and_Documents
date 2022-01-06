package elena.ues.controller;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import elena.ues.model.ArticleResponse;
import elena.ues.model.ArticleResultData;
import elena.ues.model.ErrandResponse;
import elena.ues.model.RequiredHighlight;
import elena.ues.model.SearchType;
import elena.ues.model.SimpleQuery;
import elena.ues.service.ArticleService;
import elena.ues.service.search.ResultRetriever;

@RestController
@RequestMapping("api/search")
public class SearchController {
	
	@Autowired 
	private ArticleService articleService; 
	
	@Autowired
	private ResultRetriever resultRetriever; 
	
	@GetMapping("/gteRange/{price}/articles")
	public List<ArticleResponse> getAllGteArticles(@PathVariable("price") double price) {
		return articleService.getAllGteArticles(price);
	}
	
	@GetMapping("/gtRange/{price}/articles")
	public List<ArticleResponse> getAllGtArticles(@PathVariable("price") double price) {
		return articleService.getAllGtArticles(price);
	}
	
	@GetMapping("/lteRange/{price}/articles")
	public List<ArticleResponse> getAllLteArticles(@PathVariable("price") double price) {
		return articleService.getAllLteArticles(price);
	}
	
	@GetMapping("/ltRange/{price}/articles")
	public List<ArticleResponse> getAllLtArticles(@PathVariable("price") double price) {
		return articleService.getAllLtArticles(price);
	}
	
	@PostMapping(value="/term/articles")
	public ResponseEntity <List<ArticleResponse>> searchArticlesTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		System.out.println("polje " + simpleQuery.getField() + " i vrijednost " +  simpleQuery.getValue());
		List<ArticleResponse> result = resultRetriever.getArticleResults(queryBuilder, rh);
		System.out.println("broj pronadjenih  " + result.size());
		return new ResponseEntity<List<ArticleResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/fuzzy/articles")
	public ResponseEntity <List<ArticleResponse>> searchArticlesFuzzyQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ArticleResponse> result = resultRetriever.getArticleResults(queryBuilder, rh);
		return new ResponseEntity<List<ArticleResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/phrase/articles")
	public ResponseEntity <List<ArticleResponse>> searchArticlesPhraseQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ArticleResponse> result = resultRetriever.getArticleResults(queryBuilder, rh);
		return new ResponseEntity<List<ArticleResponse>>(result, HttpStatus.OK);
	}
	
	
	@PostMapping(value="/prefix/articles")
	public ResponseEntity <List<ArticleResponse>> searchArticlesPrefixQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(SearchType.prefix, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ArticleResponse> result = resultRetriever.getArticleResults(queryBuilder, rh);
		return new ResponseEntity<List<ArticleResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/range/articles")
	public ResponseEntity <List<ArticleResponse>> searchArticlesRangeQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(SearchType.range, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ArticleResponse> result = resultRetriever.getArticleResults(queryBuilder, rh);
		return new ResponseEntity<List<ArticleResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/term/errands")
	public ResponseEntity <List<ErrandResponse>> searchErrandsTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		System.out.println("polje " + simpleQuery.getField() + " i vrijednost " +  simpleQuery.getValue());
		List<ErrandResponse> result = resultRetriever.getErrandResults(queryBuilder, rh);
		System.out.println("broj pronadjenih  " + result.size());
		return new ResponseEntity<List<ErrandResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/fuzzy/errands")
	public ResponseEntity <List<ErrandResponse>> searchErrandsFuzzyQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ErrandResponse> result = resultRetriever.getErrandResults(queryBuilder, rh);
		return new ResponseEntity<List<ErrandResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/phrase/errands")
	public ResponseEntity <List<ErrandResponse>> searchErrandsPhraseQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ErrandResponse> result = resultRetriever.getErrandResults(queryBuilder, rh);
		return new ResponseEntity<List<ErrandResponse>>(result, HttpStatus.OK);
	}
	
	
	@PostMapping(value="/prefix/errands")
	public ResponseEntity <List<ErrandResponse>> searchErrandsPrefixQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(SearchType.prefix, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ErrandResponse> result = resultRetriever.getErrandResults(queryBuilder, rh);
		return new ResponseEntity<List<ErrandResponse>>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/range/errands")
	public ResponseEntity <List<ErrandResponse>> searchErrandsRangeQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
		QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(SearchType.range, simpleQuery.getField(), simpleQuery.getValue());
		List<RequiredHighlight> rh = new ArrayList<>();
		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
		List<ErrandResponse> result = resultRetriever.getErrandResults(queryBuilder, rh);
		return new ResponseEntity<List<ErrandResponse>>(result, HttpStatus.OK);
	}
	
	/*
	@PostMapping("/advanced")
	public List<ArticleResultData> advancedArticlesSearch(@RequestParam String name, @RequestParam String description, @RequestParam String operation, @RequestParam SearchType searchType) {
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>(); 
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		
		  if (!name.equals("".trim())) {
	            org.elasticsearch.index.query.QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(searchType, "name", name.toLowerCase());
	            if (operation.equals("AND")) {
	                builder.must(queryBuilder);
	                System.out.println(">>>>>>>>>>> AND >>>>>>>>>> ");
	            } else {
	                builder.should(queryBuilder);
	                System.out.println(">>>>>>>>>>> OR >>>>>>>>>> ");
	            }
	            rh.add(new RequiredHighlight("name", name));
	        }
		  
		  if (!description.equals("".trim())) {
	            org.elasticsearch.index.query.QueryBuilder queryBuilder = elena.ues.service.search.QueryBuilder.buildQuery(searchType, "description", description.toLowerCase());
	            if (operation.equals("AND")) {
	                builder.must(queryBuilder);
	                System.out.println(">>>>>>>>>>> AND2 >>>>>>>>>> ");
	            } else {
	                builder.should(queryBuilder);
	                System.out.println(">>>>>>>>>>> OR2 >>>>>>>>>> ");

	            }
	            rh.add(new RequiredHighlight("description", description));
	        }
		  
		    List<ArticleResultData> results = resultRetriever.getResults(builder, rh);
		    System.out.println(" >>>> rezultat je >>>>" + results.toString());

	        return results;
	}
	*/
}
