package elena.ues.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "name", nullable = false)
	private String name; 
    
    @Column(name = "description", nullable = false)
   	private String description;
    
    @Column(name = "price", nullable = false)
   	private double price;
    
    @Column(name = "path", nullable = false)
   	private String path; 
   
    @ManyToOne
    private Seller seller;
    
    protected String searchArticles;

}
