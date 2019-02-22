/**
 * 
 */
package com.stayfit.userdietservice.app.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Field;

import com.stayfit.userdietservice.app.util.JobKind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * @Entity declares the class as an entity.
 * @Id declares the identifier property of this entity.
 * @Table defines the table for this entity.
 * 
 * This class represents an user's diet request.
 * Nutritionists will take care of these requests.
 */
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "user_diet_requests")
public class UserDietRequest {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Field("body_fat_perc")
	private Float bodyFatPerc;
	@Field("targer_weight")
	private Float targetWeight;
	@Field("num_training_days")
	private Integer numTrainingDays;
	@Field("job_kind")
	@Enumerated(EnumType.STRING)
	private JobKind jobKind;
	private String note;
	private Boolean completed;
	@Field("user_id")
	private Long userId;
	@Field("nutritionist_id")
	private Long nutritionistId;
	
}


