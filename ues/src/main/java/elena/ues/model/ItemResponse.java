package elena.ues.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
	
	private Long id; 
	private int quantity;
	
	@Override
	public String toString() {
		return "ItemResponse [id=" + id + ", quantity=" + quantity + "]";
	} 
	
	
	

}
