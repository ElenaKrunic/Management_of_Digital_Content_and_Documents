package elena.ues.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

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
public class Buyer extends User {
	
	@Column(name = "address", nullable = true)
	@Field(type = FieldType.Text)
	private String address; 
	
	@Column(name = "email", nullable = true)
	private String email; 
	
	
}
