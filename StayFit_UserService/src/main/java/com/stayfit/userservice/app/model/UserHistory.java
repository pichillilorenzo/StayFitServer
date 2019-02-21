package com.stayfit.userservice.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lorenzo
 * 
 * @Document declares the class as a collection.
 * @Id declares the identifier property of this collection.
 * 
 * This class represents a day of a user, listing all the foods that he has eaten.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="user_history")
public class UserHistory {

	@Id
	String id;
	
	@Field("user_id")
	private Long userId;
	private Date date = new Date();
	
	private List<Breakfast> breakfast = new ArrayList<Breakfast>();
	private List<Lunch> lunch = new ArrayList<Lunch>();
	private List<Dinner> dinner = new ArrayList<Dinner>();
	private List<Other> other = new ArrayList<Other>();
	
}