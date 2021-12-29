package elena.ues.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import elena.ues.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	List<Role> findByName(String name);

}
