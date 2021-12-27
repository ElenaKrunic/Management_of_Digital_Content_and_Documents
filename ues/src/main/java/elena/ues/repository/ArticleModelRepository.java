package elena.ues.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import elena.ues.model.ArticleModel;
import elena.ues.service.search.QueryBuilder;

public interface ArticleModelRepository extends CrudRepository<ArticleModel, Long> {
	
	List<ArticleModel> findArticlesBySellerId(Long id);

	List<ArticleModel> searchArticles(org.elasticsearch.index.query.QueryBuilder query);



}
