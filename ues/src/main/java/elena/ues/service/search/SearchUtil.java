package elena.ues.service.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class SearchUtil {
	
	public static SearchRequest buildGteArticlesSearchRequest(final String indexName, final String field, final double price) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getGteArticlesQueryBuilder(field,price));
			
			final SearchRequest request = new SearchRequest(indexName); 
			request.source(builder);
			
			return request; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static SearchRequest buildGtArticlesSearchRequest(String in, String f, double price) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getGtArticlesQueryBuilder(f, price));
			final SearchRequest request = new SearchRequest(in);
			request.source(builder);
			
			return request; 
		} catch(Exception e ) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static SearchRequest buildLteArticlesSearchRequest(String ind, String fie, double price) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getLteArticlesQueryBuilder(fie, price));
			final SearchRequest request = new SearchRequest(ind);
			request.source(builder);
			
			return request; 
		} catch(Exception e ) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static SearchRequest buildLtSearchRequest(String string, String string2, double price) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getLtArticlesQueryBuilder(string2, price));
			final SearchRequest request = new SearchRequest(string);
			request.source(builder);
			
			return request; 
		} catch(Exception e ) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static org.elasticsearch.index.query.QueryBuilder getGteArticlesQueryBuilder(final String field, final double price) {
		return QueryBuilders.rangeQuery(field).gte(price);
	}
	
	private static org.elasticsearch.index.query.QueryBuilder getGtArticlesQueryBuilder(final String field, final double price) {
		return QueryBuilders.rangeQuery(field).gt(price);
	}
	
	private static org.elasticsearch.index.query.QueryBuilder getLteArticlesQueryBuilder(final String field, final double price) {
		return QueryBuilders.rangeQuery(field).lte(price);
	}
	
	private static org.elasticsearch.index.query.QueryBuilder getLtArticlesQueryBuilder(final String field, final double price) {
		return QueryBuilders.rangeQuery(field).lt(price);
	}

	
}
