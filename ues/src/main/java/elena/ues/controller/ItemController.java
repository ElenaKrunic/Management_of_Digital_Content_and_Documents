package elena.ues.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.ues.model.ErrandModel;
import elena.ues.model.ErrandResponse;
import elena.ues.model.ItemModel;
import elena.ues.model.ItemResponse;
import elena.ues.repository.ItemModelRepository;

@RestController
@RequestMapping(value="/api/items")
@CrossOrigin
public class ItemController {

	@Autowired 
	private ItemModelRepository itemModelRepository; 
	
	@GetMapping(value="/item/{id}")
	public ResponseEntity<ItemResponse> getOneItem(@PathVariable("id") Long id) {
		ItemModel item = itemModelRepository.getById(id);
		if(item == null) { 
			return null;
		}
		return new ResponseEntity<ItemResponse>(new ItemResponse(item), HttpStatus.OK);
	}
	
	
	
}
