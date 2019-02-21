/**
 * 
 */
package com.stayfit.userdietservice.app.model;

import java.util.ArrayList;
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
 * This class represents an user's diet.
 * - nutritionistId is the id of the nutritionist that made this diet
 * - week represents the list of the different days of the week containing the diet foods
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="user_diet")
public class UserDiet {
	
	@Id
	String id;
	
	@Field("user_id")
	private Long userId;
	@Field("nutritionist_id")
	private Long nutritionistId;
	
	private List<DietDay> week = new ArrayList<DietDay>(7);
	
}
