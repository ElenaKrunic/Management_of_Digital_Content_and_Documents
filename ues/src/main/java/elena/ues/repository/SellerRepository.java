package elena.ues.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import elena.ues.model.Seller;

public interface SellerRepository extends CrudRepository<Seller, Long> {

	Seller findByFirstname(String firstname);

	Seller loadByUsernameAndPassword(String username, String password);

}
