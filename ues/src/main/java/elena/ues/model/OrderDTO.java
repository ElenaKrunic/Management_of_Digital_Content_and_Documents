package elena.ues.model;

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
public class OrderDTO {
	
	//private ItemResponse item;
	//private ErrandResponse errand; 
	//private ArticleResponse article;
	
	private ArticleModel articleFromDB; 
	private ErrandModel errandFromDB; 
	//private ItemModel itemFromDB; 
	private Integer quantity;
	
	//price
	//quantity -> item
	//article id
	

}
