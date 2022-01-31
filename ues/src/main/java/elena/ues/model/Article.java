package elena.ues.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Document(indexName = "artikli", createIndex = false)
@Setting(settingPath = "esConfig/serbianAnalyzer.json")
@NoArgsConstructor
public class Article {
	
	@Field(type = FieldType.Keyword)
	private String id;
	
	@Field(type = FieldType.Text, analyzer = "serbian_analyzer")
	private String name; 
	
	@Field(type = FieldType.Text, analyzer = "serbian_analyzer")
	private String description; 
	
	@Field(type = FieldType.Double)
	private double price; 
	
	@ManyToOne
	private Seller seller;
	
	private String searchArticle; 
	private String indexArticle;
	private String searchSimilar; 
}
