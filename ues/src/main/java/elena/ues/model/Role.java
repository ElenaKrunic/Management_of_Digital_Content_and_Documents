package elena.ues.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role implements GrantedAuthority {

	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  @Column(name = "id", nullable = false, unique = true)
	  private int id;

	  @Column(name = "_name", nullable = false)
	  private String name;

	  @ManyToMany(mappedBy="roles", fetch = FetchType.EAGER)
	  private List<User> users;

	  @Override
	  public String getAuthority() {
		return name;
	}
	    
}
