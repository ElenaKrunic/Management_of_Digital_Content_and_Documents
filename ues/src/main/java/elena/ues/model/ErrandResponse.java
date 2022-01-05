package elena.ues.model;

import java.util.Date;

import javax.persistence.Column;

import elena.ues.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrandResponse {
	
	private Long id;
	private Date orderedAtDate; 
	private boolean isDelivered; 
	private int grade; 
	private String comment; 
	private boolean anonymousComment;
	private boolean archivedComment; 
	
	public ErrandResponse(ErrandModel errand) {
		this.id = errand.getId();
		this.orderedAtDate = errand.getOrderedAtDate(); 
		this.isDelivered = errand.isDelivered();
		this.grade = errand.getGrade();
		this.comment = errand.getComment();
		this.anonymousComment = errand.isAnonymousComment();	
		this.archivedComment = errand.isArchivedComment();
	}
	

}
