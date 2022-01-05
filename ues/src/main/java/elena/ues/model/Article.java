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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = "articles", createIndex = false)
@Setting(settingPath="es-config/elastic-setting.json")
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
