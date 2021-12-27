package elena.ues.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResultData {

	private String name; 
	private String description; 	
	private double price;
	private String location;
	private String highlight;
	
	public ArticleResultData(String name, String description) {
		this.name = name; 
		this.description = description;
	}
}
