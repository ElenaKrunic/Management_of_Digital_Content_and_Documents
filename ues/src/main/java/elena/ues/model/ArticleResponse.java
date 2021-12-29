package elena.ues.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {

	private Long id; 
	private String name; 
	private String description; 	
	private double price;
	private String path;
	private Long sellerID; 
	
	public ArticleResponse(ArticleModel article) {
		this.id = article.getId(); 
		this.name = article.getName();
		this.description = article.getDescription(); 
		this.price = article.getPrice();
		this.path = article.getPath();
	}

	public ArticleResponse(String name, String description) {
		this.name = name; 
		this.description = description;
	}
	
}
