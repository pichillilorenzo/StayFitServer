/**
 * 
 */
package com.stayfit.userdietservice.app.model;

import java.math.BigDecimal;

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
	@Field("food_name")
	private String name;
	private BigDecimal calories;
	private float amount;
	String unit;
}
