package elena.ues.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="seller")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends User {
	
	//@Id
	//@Field(type =  FieldType.Keyword)
	//private String id; 
	
	@Column(name = "operatesSince", nullable = true)
	@Field(type = FieldType.Text)
	private Date operatesSince; 
	
	@Column(name = "email", nullable = true)
	@Field(type = FieldType.Text)
	private String email; 
	
	@Column(name = "address", nullable = false)
	@Field(type = FieldType.Text)
	private String address; 
	
	@Column(name = "storeName", nullable = true)
	@Field(type = FieldType.Text)
	private String storeName; 
	
	private String searchSimilar;
	private String loadByUsername; 
}
