package elena.ues.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elena.ues.model.ArticleModel;
import elena.ues.model.ArticleResponse;
import elena.ues.model.ErrandModel;
import elena.ues.model.ErrandRequest;
import elena.ues.model.ErrandResponse;
import elena.ues.model.ItemResponse;
import elena.ues.model.StringResponse;
import elena.ues.repository.ErrandModelRepository;
import elena.ues.service.ErrandService;

@RestController
@RequestMapping(value="/api/errands")
@CrossOrigin
public class ErrandController {

	@Autowired
	private ErrandModelRepository errandModelRepository; 
	
	@Autowired 
	private ErrandService errandService; 
	
	@GetMapping(value="/errand/{id}")
	public ResponseEntity<ErrandResponse> getOneErrand(@PathVariable("id") Long id) {
		ErrandModel errand = errandModelRepository.findById(id).orElse(null);
		if(errand == null) { 
			return null;
		}
		
		return new ResponseEntity<ErrandResponse>(new ErrandResponse(errand), HttpStatus.OK);
		
	}
	
	@GetMapping("/myErrands")
	public ResponseEntity<?> getMyErrands(Principal principal) {
		try {
			List<ErrandResponse> myErrands = errandService.myErrands("selenatutic@gmail.com");
			return new ResponseEntity<>(myErrands, HttpStatus.OK);
		} catch (Exception e ) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
	}
	
	
	@PostMapping("/makeAnErrand")
	public ResponseEntity<?> order(@RequestBody ErrandRequest errandRequest) {
		try {
			String message = errandService.makeAnErrand(errandRequest);
			return new ResponseEntity<> (new StringResponse(message), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<> (new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	//korisnik pregleda sve nedostavljene porudzbine 
	@GetMapping("/nonDelivered/{true}")
	public ResponseEntity<?> getNonDelivered(boolean isDelivered) {
		try {
			List<ErrandResponse> myErr = errandService.getNonDelivered(isDelivered);
			
			return new ResponseEntity<>(myErr, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//korisnik mijenja status porudzbine u dostavljenu 
	@PutMapping("/changeStatus/{id}")
	public ResponseEntity<?> changeDeliveryStatus(@PathVariable("id") Long id) {
		try {
			String mess = errandService.changeDeliveryStatus(id);
			return new ResponseEntity<>(new StringResponse(mess), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//korisnik mora da da komentar i ocijeni porudzbinu 
	@PutMapping("/deliveryComment/{id}")
	public ResponseEntity<?> deliveryComment(@PathVariable("id") Long id) {
		try {
			String mess = errandService.deliveryComment(id); 
			return new ResponseEntity<>(new StringResponse(mess), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}
	
	@PutMapping("/commentAndGrade/{id}")
	public ResponseEntity<ErrandResponse> commentAndGrade(@RequestBody ErrandResponse res, @PathVariable("id") Long id) {
		ErrandModel errand = errandModelRepository.getById(id); 
		errand.setAnonymousComment(res.isAnonymousComment());
		errand.setArchivedComment(res.isArchivedComment());
		errand.setComment(res.getComment());
		errand.setGrade(res.getGrade());
		
		errand = errandModelRepository.save(errand);
		
		return new ResponseEntity<ErrandResponse>(new ErrandResponse(errand), HttpStatus.OK);
		
	}
}
