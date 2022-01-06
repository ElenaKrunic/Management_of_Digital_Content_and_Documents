package elena.ues.model;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import elena.ues.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrandResponse {
	
	@JsonProperty
	private Long id;
	
	@JsonProperty
	private Date orderedAtDate;
	
	@JsonProperty
	private boolean isDelivered; 
	
	@JsonProperty
	private int grade;
	
	@JsonProperty
	private String comment; 
	
	@JsonProperty
	private boolean anonymousComment;
	
	@JsonProperty
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
