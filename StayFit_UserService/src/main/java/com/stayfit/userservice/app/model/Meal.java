/**
 * 
 */
package com.stayfit.userservice.app.model;

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
public class Meal {
	
	@Field("food_id")
	private long foodId;
	private float amount;
	String unit;
}
