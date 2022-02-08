package elena.ues.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.ues.model.ArticleModel;
import elena.ues.model.BuyerRequest;
import elena.ues.model.LoginRequest;
import elena.ues.model.SellerRequest;
import elena.ues.model.StringResponse;
import elena.ues.model.User;
import elena.ues.model.UserResponse;
import elena.ues.repository.UserRepository;
import elena.ues.security.util.JwtUtil;
import elena.ues.service.BuyerService;
import elena.ues.service.SellerService;
import elena.ues.service.UserService;

@RestController
@RequestMapping(value="api/user")
public class UserController {
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private UserService userService; 
	
	@Autowired 
	private BuyerService buyerService; 
	
	@Autowired 
	private SellerService sellerService; 
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest login) throws Exception {
		User user = userService.findByUsernameAndPasswordAndBlocked(login.getUsername(), login.getPassword());
		
		if(user.isBlocked() == true) {
			System.out.println("Nema tokena za blokiranog korisnika");
		} else {
			String jwtToken = jwtUtil.generateToken(user);
			return new ResponseEntity<String>(jwtToken, HttpStatus.OK);	
		}
		
		return null;
	
	}
	
	@PostMapping("/registerSeller")
	public ResponseEntity<?> registerSeller(@RequestBody SellerRequest sellerRequest) {
		try {
			String register = userService.registerSeller(sellerRequest);
			return new ResponseEntity<>(new StringResponse(register), HttpStatus.OK);
		} catch (Exception e ) {
			e.printStackTrace();
            return new ResponseEntity<>(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/registerBuyer")
	public ResponseEntity<?> registerBuyer(@RequestBody BuyerRequest buyerRequest) {
		try {
			String register = userService.registerBuyer(buyerRequest);
			return new ResponseEntity<>(new StringResponse(register), HttpStatus.OK);
		} catch (Exception e ) {
			e.printStackTrace();
            return new ResponseEntity<>(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
    @PutMapping("/changeBuyerPassword")
    public ResponseEntity<StringResponse> changeBuyerPassword(@RequestBody BuyerRequest dto, Principal principal) {
        try {
        	buyerService.changePassword(dto, "selenatutic@gmail.com");
            return new ResponseEntity<>(new StringResponse("Successful!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/changeSellerPassword")
    public ResponseEntity<StringResponse> changeSellerPassword(@RequestBody SellerRequest dto, Principal principal) {
        try {
            sellerService.changePassword(dto, "elenakrunic@gmail.com");
            return new ResponseEntity<>(new StringResponse("Successful!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/myBuyerProfile")
    public ResponseEntity<?> getMyBuyerProfile(Principal principal) {
        try {
            BuyerRequest buyer = buyerService.getMyBuyerProfile("selenatutic@gmail.com");
            return new ResponseEntity<>(buyer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/mySellerProfile") 
    public ResponseEntity<?> getMySellerProfile(Principal principal) {
        try {
            SellerRequest seller = sellerService.getMySellerProfile("elenakrunic@gmail.com");
            return new ResponseEntity<>(seller, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/getUsers")
    public List<UserResponse> getAllUsers() {
    	List<UserResponse> users = userService.getAll(); 
    	return users; 
    }
    
    @GetMapping("getUser/{id}")
	public User get(@PathVariable("id") Long id) {
    	return userRepository.getUserById(id);
	}
    
    @PutMapping("/ban/{id}")
    public ResponseEntity<?> ban(@PathVariable("id") Long id) {
        try {
            String mess = userService.validate(id);
            return new ResponseEntity<>(new StringResponse(mess), HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) throws Exception {
		User user = userRepository.findById(id).orElseThrow();
		
		if(user != null) {
			userRepository.deleteById(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			throw new Exception();
		}
    }
}
