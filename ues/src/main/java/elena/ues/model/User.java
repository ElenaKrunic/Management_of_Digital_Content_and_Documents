package elena.ues.model;

import java.util.List;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable=false, unique=true)
	private Long id; 
	
	@Column(name="firstname", nullable = false)
	private String firstname; 
	
	@Column(name="lastname", nullable = false)
	private String lastname; 
	
	@Column(name="username", nullable = false)
	private String username; 
	
	@Column(name="password")
	private String password;
	
	@Column(name="blocked", nullable = false)
	private boolean blocked; 
	
	@JsonIgnore
	@OneToMany(mappedBy="seller")
	private List<ArticleModel> articles; 
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="role_has_user"
            , joinColumns={
            @JoinColumn(name="user_id")
    }
            , inverseJoinColumns={
            @JoinColumn(name="role_id")
    }
    )
    private List<Role> roles;
	
	public User(String username, String password) {
		this.username = username; 
		this.password = password; 
	}
}
