package elena.ues.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

import elena.ues.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsernameAndPassword(String username, String password);
	Optional<User> findByUsername(String username);


}
