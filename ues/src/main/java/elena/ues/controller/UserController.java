package elena.ues.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.ues.model.LoginRequest;
import elena.ues.model.User;
import elena.ues.repository.UserRepository;
import elena.ues.security.util.JwtUtil;
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
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest login) {
		User user = userRepository.findByUsernameAndPassword(login.getUsername(), login.getPassword()); 
		String jwtToken = jwtUtil.generateToken(user);
		return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
	}
	
	@GetMapping("getUser/{id}")
	public Optional<User> get(@PathVariable("id") Long id) {
		return userRepository.findById(id);
	}
	
	
	@GetMapping(value = "/all")
	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}
	
}
