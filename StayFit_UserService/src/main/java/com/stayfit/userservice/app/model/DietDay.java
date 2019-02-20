/**
 * 
 */
package com.stayfit.userservice.app.model;

import java.util.ArrayList;
import java.util.List;

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
public class DietDay {
	
	private List<Breakfast> breakfast = new ArrayList<Breakfast>();
	private List<Lunch> lunch = new ArrayList<Lunch>();
	private List<Dinner> dinner = new ArrayList<Dinner>();
	private List<Other> other = new ArrayList<Other>();
	
}
