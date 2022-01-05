package elena.ues.model;

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
@Table(name="item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemModel {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "quantity", nullable = false)
	private int quantity; 
     
    @ManyToOne
    private ErrandModel errand; 
     
    @ManyToOne
    private ArticleModel article; 
}
