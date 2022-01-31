package elena.ues.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
	
	private Long id; 
	private String firstname; 
	private String lastname; 
	private String username; 
	private String password; 
	private boolean blocked;
	private List<String> roles;

}
