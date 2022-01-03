package elena.ues.repository;

import org.springframework.data.repository.CrudRepository;

import elena.ues.model.Photo;

public interface PhotoRepository extends CrudRepository<String, Photo> {

	String findById(String string);

}
