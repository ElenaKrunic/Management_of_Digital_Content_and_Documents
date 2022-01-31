package elena.ues.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.indices.Analyze;

import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.elasticsearch.index.IndexSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.stereotype.Service;

import elena.ues.handler.Handler;
import elena.ues.indexing.analysers.SerbianAnalyzer;
import elena.ues.model.Article;
import elena.ues.model.ArticleResponse;
import elena.ues.model.ErrandResponse;
import elena.ues.repository.ArticleRepository;

@Service
public class Indexer {
	
	private JestClient jestClient;
	private static Indexer indexer = new Indexer(); 
	
	 private Indexer() {
	        JestClientFactory factory = new JestClientFactory();
	      
	        factory.setHttpClientConfig(
	                new HttpClientConfig.Builder("http://localhost:9200")
	                        .multiThreaded(true)
	                        .defaultMaxTotalConnectionPerRoute(2)
	                        .maxTotalConnection(10)
	                        .build());
	        jestClient = factory.getObject();
	    }

	@Autowired
	private ArticleRepository articleRepository; 
	
	  public boolean delete(String filename) {
	        if (articleRepository.findById("C:\\Users\\lenovo\\Desktop\\UES_Project\\projekat\\ues\\src\\main\\resources\\files\\" + filename).isPresent()) {
	        	articleRepository.delete(articleRepository.findById("C:\\Users\\lenovo\\Desktop\\UES_Project\\projekat\\ues\\src\\main\\resources\\files\\" + filename).get());
	            File file = new File("src\\main\\resources\\files\\" + filename);
	            File file2 = new File("target\\classes\\files\\" + filename);
	            if (file.exists()) {
	                file.delete();
	            }
	            if (file2.exists()) {
	                file2.delete();
	            }
	            return true;
	        } else {
	            File file = new File("src\\main\\resources\\files\\" + filename);
	            File file2 = new File("target\\classes\\files\\" + filename);
	            if (file.exists()) {
	                file.delete();
	            }
	            if (file2.exists()) {
	                file2.delete();
	            }
	            return false;
	        }
	    }

	  
	  public boolean update(Article article) {
		  article = articleRepository.save(article); 
		  
		  if(article != null) {
			  return true; 
		  } else {
			  return false; 
		  }
	  }
	
	  public boolean add(Article article) {
		  article = articleRepository.indexArticle(article);
		  
		  if(article != null) {
			  articleRepository.save(article);
			  return true;
		  } else {
			  return false;
		  }
	  }
	 

	  public int index(File file) {
	        Handler handler = new Handler();
	        int retVal = 0;
	        try {
	            File[] files;
	            if (file.isDirectory()) {
	                files = file.listFiles();
	            } else {
	                files = new File[1];
	                files[0] = file;
	            }
	            for (File newFile : files) {
	                if (newFile.isFile()) {
	                	Article article = handler.getArticle(newFile);
	                	article.setName(handler.getArticle(newFile).getName());
	                	article.setDescription(handler.getArticle(newFile).getDescription());
	                    if (add(article)) {
	                        retVal++;
	                    }
	                } else if (newFile.isDirectory()) {
	                    retVal += index(newFile);
	                }
	            }
	            System.out.println("indexing done");
	        } catch (Exception e) {
	            System.out.println("indexing NOT done");
	        }
	        return retVal;
	    }
	       


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	  
	  public int indexArticle(List<ArticleResponse> articles) throws IOException {
		  JestResult result = null;
		  int retVal = 0; 
		  
		  for(ArticleResponse article : articles) {
	            Index index = new Index.Builder(article).index("artikli").type("artikal").id(article.getId().toString()).build();
	            result = jestClient.execute(index);
	            
		  }
		  
		  if(result.isSucceeded()) {
			  return retVal += articles.size();
		  } else {
			  return -1; 
		  }
	  }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexErrand(List<ErrandResponse> errands) throws IOException {
		JestResult result = null; 
		int retVal = 0; 
		
		for(ErrandResponse errand : errands) {
			Index index = new Index.Builder(errand).index("porudzbine").type("porudzbina").id(errand.getId().toString()).build();
			result = jestClient.execute(index);
			
		}
		
		  if(result.isSucceeded()) {
			  return retVal += errands.size();
		  } else {
			  return -1; 
		  }
	}
	
	
}
