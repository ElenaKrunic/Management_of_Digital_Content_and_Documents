package elena.ues.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import elena.ues.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Document(indexName="artikli")
//@Setting(settingPath = "esConfig/serbianAnalyzer.json")
public class ArticleResponse {

	@Id
	@Field(type = FieldType.Keyword)
	private String id;
	
    @Field(type = FieldType.Keyword)
	private String name; 
    
    @Field(type = FieldType.Text)
	private String description; 
    
    @Field(type = FieldType.Double)
	private double price;
    
    @Field(type = FieldType.Keyword)
	private String path;
    
	private Long sellerID; 
	
	@Autowired
	private PhotoRepository photoRepository;
	
	public ArticleResponse(ArticleModel article) {
		this.id = article.getId().toString(); 
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
	
	public ArticleResponse(String id) {
		 this.id = id;
	}


	public ArticleResponse(String name, String description) {
		this.name = name; 
		this.description = description;
	}

	@Override
	public String toString() {
		return "ArticleResponse [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", path=" + path + ", sellerID=" + sellerID + ", photoRepository=" + photoRepository + "]";
	}
	
	
}
