package elena.ues.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrandRequest {

	@JsonProperty
	private Long id;
	
	@JsonProperty
	private String articleName; 
	
	@JsonProperty
	private double price;
	
	@JsonProperty
	private int quantity;
	
	@JsonProperty
	private Long buyerId;
}
