package elena.ues.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BuyerRequest {
	
	//1
	private String id;
	//2
	private String pass;
	//3
	private String address;
	//4
	private String phone;
	//5
	private String firstname;
	//6
	private String lastname;
	//7
	private String email;
	//8
	private String username;
	

}
