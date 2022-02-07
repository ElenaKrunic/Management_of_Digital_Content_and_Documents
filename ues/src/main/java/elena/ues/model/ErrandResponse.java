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
	private String orderedAtDate;
	
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
		this.orderedAtDate = errand.getOrderedAtDate().toString(); 
		this.isDelivered = errand.isDelivered();
		this.grade = errand.getGrade();
		this.comment = errand.getComment();
		this.anonymousComment = errand.isAnonymousComment();	
		this.archivedComment = errand.isArchivedComment();
	}

	@Override
	public String toString() {
		return "ErrandResponse [id=" + id + ", orderedAtDate=" + orderedAtDate + ", isDelivered=" + isDelivered
				+ ", grade=" + grade + ", comment=" + comment + ", anonymousComment=" + anonymousComment
				+ ", archivedComment=" + archivedComment + "]";
	}
	
	public ErrandResponse(Long id) {
		this.id = id;
	}
	
	

}
