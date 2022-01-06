package elena.ues.service.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class SearchUtil {
	
	public static SearchRequest buildGteArticlesSearchRequest(final String aindexName, final String afield, final double aprice) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getGteArticlesQueryBuilder(afield,aprice));
			
			final SearchRequest request = new SearchRequest(aindexName); 
			request.source(builder);
			
			return request; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static SearchRequest buildGtArticlesSearchRequest(String ain, String af, double aprice) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getGtArticlesQueryBuilder(af, aprice));
			final SearchRequest request = new SearchRequest(ain);
			request.source(builder);
			
			return request; 
		} catch(Exception e ) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static SearchRequest buildLteArticlesSearchRequest(String aind, String afie, double aprice) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getLteArticlesQueryBuilder(afie, aprice));
			final SearchRequest request = new SearchRequest(aind);
			request.source(builder);
			
			return request; 
		} catch(Exception e ) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static SearchRequest buildLtSearchRequest(String astring, String astring2, double aprice) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getLtArticlesQueryBuilder(astring2, aprice));
			final SearchRequest request = new SearchRequest(astring);
			request.source(builder);
			
			return request; 
		} catch(Exception e ) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static org.elasticsearch.index.query.QueryBuilder getGteArticlesQueryBuilder(final String afield, final double aprice) {
		return QueryBuilders.rangeQuery(afield).gte(aprice);
	}
	
	private static org.elasticsearch.index.query.QueryBuilder getGtArticlesQueryBuilder(final String afield, final double aprice) {
		return QueryBuilders.rangeQuery(afield).gt(aprice);
	}
	
	private static org.elasticsearch.index.query.QueryBuilder getLteArticlesQueryBuilder(final String afield, final double aprice) {
		return QueryBuilders.rangeQuery(afield).lte(aprice);
	}
	
	private static org.elasticsearch.index.query.QueryBuilder getLtArticlesQueryBuilder(final String afield, final double aprice) {
		return QueryBuilders.rangeQuery(afield).lt(aprice);
	}

	public static SearchRequest buildGteErrandsSearchRequest(String estring, String estring2, int egrade) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getGteErrandsQueryBuilder(estring2,egrade));
			
			final SearchRequest request = new SearchRequest(estring); 
			request.source(builder);
			
			return request; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static SearchRequest buildGtErrandsSearchRequest(String erstring, String erstring2, int ergrade) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getGtErrandsQueryBuilder(erstring2,ergrade));
			
			final SearchRequest request = new SearchRequest(erstring); 
			request.source(builder);
			
			return request; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static SearchRequest buildLteErrandsSearchRequest(String errstring, String errstring2, int errgrade) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getLteErrandsQueryBuilder(errstring2,errgrade));
			
			final SearchRequest request = new SearchRequest(errstring); 
			request.source(builder);
			
			return request; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static SearchRequest buildLtErrandsSearchRequest(String errastring, String errastring2, int erragrade) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getLtErrandsQueryBuilder(errastring2,erragrade));
			
			final SearchRequest request = new SearchRequest(errastring); 
			request.source(builder);
			
			return request; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static org.elasticsearch.index.query.QueryBuilder getGteErrandsQueryBuilder(final String efield, final int grade) {
		return QueryBuilders.rangeQuery(efield).gte(grade);
	}
	
	private static org.elasticsearch.index.query.QueryBuilder getGtErrandsQueryBuilder(final String efield, final int grade) {
		return QueryBuilders.rangeQuery(efield).gt(grade);
	}
	
	private static org.elasticsearch.index.query.QueryBuilder getLteErrandsQueryBuilder(final String efield, final int grade) {
		return QueryBuilders.rangeQuery(efield).lte(grade);
	}
	
	private static org.elasticsearch.index.query.QueryBuilder getLtErrandsQueryBuilder(final String efield, final int grade) {
		return QueryBuilders.rangeQuery(efield).lt(grade);
	}
}
