package elena.ues.service;

import java.util.HashSet;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import elena.ues.model.CustomPrincipal;
import elena.ues.model.User;
import elena.ues.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	/*
	public UserDetails loadByUsernameAndPassword(String username, String password) {
		Optional<User> user = userRepository.findByUsernameAndPassword(username, password); 
		if(user == null) throw new UsernameNotFoundException(String.format("User with username: %s not found", username));
        User u = user.get();
        
        return new CustomPrincipal(u.getId(), u.getUsername(), u.getFirstname(), u.getLastname(), u.getPassword(), new HashSet<>());
	}
	*/

	public User loadByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username); 
		
		User u = user.get();
		return u; 
		} 
	
	public User getUserById(Long id) {
		//User user = userRepository.findById(id).orElseThrow(); 
		return userRepository.findById(id).orElseThrow(); 
	}
}
