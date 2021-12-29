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
public class SellerRequest {
	
	private String id;
	private String pass;
	private String address;
	private String phone;
	private String firstname;
	private String lastname;
	private String email;
	private Date operatesSince; 
	private String storeName; 
	private String username;

}
