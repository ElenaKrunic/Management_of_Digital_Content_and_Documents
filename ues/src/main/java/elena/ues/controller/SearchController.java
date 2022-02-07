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

import elena.ues.model.AdvancedQuery;
import elena.ues.model.ArticleResponse;
import elena.ues.model.ArticleResultData;
import elena.ues.model.ErrandResponse;
import elena.ues.model.RequiredHighlight;
import elena.ues.model.SearchType;
import elena.ues.model.SimpleQuery;
import elena.ues.service.ArticleService;
import elena.ues.service.ErrandService;
import elena.ues.service.search.ResultRetriever;

@RestController
@RequestMapping("api/search")
public class SearchController {
	
	@Autowired 
	private ArticleService articleService; 
	
	@Autowired
	private ResultRetriever resultRetriever;
	
	@Autowired 
	private ErrandService errandService; 
	
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
	
	
	@PostMapping(value="/boolean/articles", consumes="application/json")
	public ResponseEntity<List<ArticleResponse>> searchArticlesBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception {
        QueryBuilder query1 = elena.ues.model.QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField1(), advancedQuery.getValue1());
        QueryBuilder query2 = elena.ues.model.QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField2(), advancedQuery.getValue2());

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if(advancedQuery.getOperation().equalsIgnoreCase("AND")) {
        	builder.must(query1);
        	builder.must(query2);
        	System.out.println(">>> and builder >>>" +  builder.getName());
        } else if(advancedQuery.getOperation().equalsIgnoreCase("OR")) {
        	builder.should(query1); 
        	builder.should(query2);
        	System.out.println(">>> or builder >>> " + builder.getName());
        } else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")) {
        	builder.must(query1); 
        	builder.mustNot(query2);
        	System.out.println(">>> not builder >>> " + builder.getName());
        }
        
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
        rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
        
        System.out.println(" >>> rh >>> " + rh.toString());
        List<ArticleResponse> result = resultRetriever.getArticleResults(builder, rh);
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
	
	@GetMapping("/gteRange/{grade}/errands")
	public List<ErrandResponse> getAllGteErrands(@PathVariable("grade") int grade) {
		return errandService.getAllGteErrands(grade);
	}
	
	@GetMapping("/gtRange/{grade}/errands")
	public List<ErrandResponse> getAllGtErrands(@PathVariable("grade") int grade) {
		return errandService.getAllGtErrands(grade);
	}
	
	@GetMapping("/lteRange/{grade}/errands")
	public List<ErrandResponse> getAllLteErrands(@PathVariable("grade") int grade) {
		return errandService.getAllLteErrands(grade);
	}
	
	@GetMapping("/ltRange/{grade}/errands")
	public List<ErrandResponse> getAllLtErrands(@PathVariable("grade") int grade) {
		return errandService.getAllLtErrands(grade);
	}
	
	
	@PostMapping(value="/boolean/errands", consumes="application/json")
	public ResponseEntity<List<ErrandResponse>> searchErrandsBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception {
        QueryBuilder query1 = elena.ues.model.QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField1(), advancedQuery.getValue1());
        QueryBuilder query2 = elena.ues.model.QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField2(), advancedQuery.getValue2());

        System.out.println(">>> query1.toString >>> " + query1.toString());
        System.out.println(">>> query2.toString >>> " + query2.toString());

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if(advancedQuery.getOperation().equalsIgnoreCase("AND")) {
        	builder.must(query1);
        	builder.must(query2);
        	System.out.println(">>> and builder >>>" +  builder.getName());
        } else if(advancedQuery.getOperation().equalsIgnoreCase("OR")) {
        	builder.should(query1); 
        	builder.should(query2);
        	System.out.println(">>> or builder >>> " + builder.getName());
        } else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")) {
        	builder.must(query1); 
        	builder.mustNot(query2);
        	System.out.println(">>> not builder >>> " + builder.getName());
        }
        
        List<RequiredHighlight> rh = new ArrayList<>();
        rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
        rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
        
        System.out.println(" >>> rh >>> " + rh.toString());
        List<ErrandResponse> result = resultRetriever.getErrandResults(builder, rh);
		return new ResponseEntity<List<ErrandResponse>>(result, HttpStatus.OK);
	}

	
}
