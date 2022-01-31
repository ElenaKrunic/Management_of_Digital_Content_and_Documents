package elena.ues.model;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(indexName="test", createIndex = true)
@Setting(settingPath = "/es-config/serbian-analyzer.json")
public class Test {
	
	@Id
	private String id; 
	
	@Field(type = FieldType.Keyword)
	private String name; 

}
