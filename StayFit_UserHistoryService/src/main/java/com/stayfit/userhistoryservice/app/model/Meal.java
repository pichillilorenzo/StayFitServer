/**
 * 
 */
package com.stayfit.userhistoryservice.app.model;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * This class represents a Meal of a user.
 * - foodId is the id of the food.
 * - amount is the amount of the food eaten by the user.
 * - unit is the unit of measure.
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
