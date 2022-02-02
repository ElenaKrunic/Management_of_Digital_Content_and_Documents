package elena.ues.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import elena.ues.model.Buyer;
import elena.ues.model.ErrandModel;

public interface ErrandModelRepository extends CrudRepository<ErrandModel, Long> {

	List<ErrandModel> findErrandsByBuyerId(Long id);

	List<ErrandModel> findAllByBuyer(Buyer buyer);

	ErrandModel getById(Long id);

	List<ErrandModel> findAllByIsDelivered(boolean isDelivered);

}
