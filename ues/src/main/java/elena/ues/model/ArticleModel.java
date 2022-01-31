package elena.ues.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Table(name="article")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleModel {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = true)
	private String name; 
    
    @Column(name = "description", nullable = true)
   	private String description;
    
    @Column(name = "price", nullable = true)
   	private double price;
    
    @Column(name = "path", nullable = true)
   	private String path; 
    
    @OneToMany(mappedBy="article")
    private List<ItemModel> items;
   
    @ManyToOne
    private Seller seller;
    
    protected String searchArticles;
    
    public ArticleModel(Long id) {
    	this.id = id; 
    }

}
