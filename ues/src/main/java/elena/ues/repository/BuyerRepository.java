package elena.ues.repository;

import org.springframework.data.repository.CrudRepository;

import elena.ues.model.Buyer;

public interface BuyerRepository extends CrudRepository<Buyer, Long> {

	Buyer findByEmail(String email);

}
