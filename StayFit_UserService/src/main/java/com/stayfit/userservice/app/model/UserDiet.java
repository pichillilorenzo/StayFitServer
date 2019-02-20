/**
 * 
 */
package com.stayfit.userservice.app.model;

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
