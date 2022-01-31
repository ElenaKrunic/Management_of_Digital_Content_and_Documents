package elena.ues.repository;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import elena.ues.model.Article;
import elena.ues.model.ArticleModel;
import elena.ues.model.ArticleResponse;
import elena.ues.model.ArticleResultData;

public interface ArticleRepository extends ElasticsearchRepository<Article, String>{

	List<Article> searchArticle(QueryBuilder query);

	Article indexArticle(Article article);

	List<ArticleResponse> getBySeller(int id);

	Article getById(Long id);

}
