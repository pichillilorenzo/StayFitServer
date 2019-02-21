/**
 * 
 */
package com.stayfit.userdietservice.app.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lorenzo
 * 
 * This class represents a whole day of a user, where
 * there are listed all of the foods that he ate.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietDay {
	
	private List<Breakfast> breakfast = new ArrayList<Breakfast>();
	private List<Lunch> lunch = new ArrayList<Lunch>();
	private List<Dinner> dinner = new ArrayList<Dinner>();
	private List<Other> other = new ArrayList<Other>();
	
}
