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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends User {
		
	@Column(name = "operatesSince", nullable = true)
	private Date operatesSince; 
	
	@Column(name = "email", nullable = true)
	private String email; 
	
	@Column(name = "address", nullable = false)
	private String address; 
	
	@Column(name = "storeName", nullable = true)
	private String storeName; 
	
	private String searchSimilar;
	private String loadByUsername;
 
}
