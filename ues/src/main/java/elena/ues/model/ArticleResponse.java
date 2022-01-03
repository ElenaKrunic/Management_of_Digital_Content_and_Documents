package elena.ues.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import elena.ues.repository.PhotoRepository;
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
	
	@Autowired
	private PhotoRepository photoRepository;
	
	public ArticleResponse(ArticleModel article) {
		this.id = article.getId(); 
		this.name = article.getName();
		this.description = article.getDescription(); 
		this.price = article.getPrice();
		this.path = article.getPath();
	}
	
	public String getPhotoPath(String path) {
		this.path = photoRepository.findById(path);
		System.out.println(" >>> putanja do slike >>> " + path);
		return path;
	}


	public ArticleResponse(String name, String description) {
		this.name = name; 
		this.description = description;
	}
	
}
