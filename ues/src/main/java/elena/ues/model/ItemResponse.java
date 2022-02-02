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
	private ErrandModel errand;
	private ArticleModel article; 
	
	@Override
	public String toString() {
		return "ItemResponse [id=" + id + ", quantity=" + quantity + "]";
	} 
	
	public ItemResponse(ItemModel item) {
		this.id = item.getId();
		this.quantity = item.getQuantity();
		this.errand = item.getErrand();
		this.article = item.getArticle();
	}
}
