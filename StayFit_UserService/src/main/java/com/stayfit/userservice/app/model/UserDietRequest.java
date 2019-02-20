/**
 * 
 */
package com.stayfit.userservice.app.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stayfit.userservice.app.util.JobKind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lorenzo
 *
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
	
	/*
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@JsonIgnore
    private Collection<User> user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nutritionist_id")
	@JsonIgnore
    private Collection<User> nutritionist;
    */
	
}

