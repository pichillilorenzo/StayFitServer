/**
 * 
 */
package com.stayfit.userservice.app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.stayfit.userservice.app.model.Breakfast;
import com.stayfit.userservice.app.model.Dinner;
import com.stayfit.userservice.app.model.Lunch;
import com.stayfit.userservice.app.model.Other;
import com.stayfit.userservice.app.model.UserDiet;
import com.stayfit.userservice.app.model.DietDay;

/**
 * @author lorenzo
 *
 * This utility class is used to covert an user's diet model to a WSDL request and vice-versa.
 */
@Component
public class UserDietTransformer {
	public com.stayfit.userservice.UserDiet convert(UserDiet userDiet) {
		
		com.stayfit.userservice.UserDiet userDietWsdl = new com.stayfit.userservice.UserDiet();
        BeanUtils.copyProperties(userDiet, userDietWsdl);
        
        List<com.stayfit.userservice.DietDay> weekWsdl = new ArrayList<com.stayfit.userservice.DietDay>(7);
        
        userDiet.getWeek().forEach(dietDay -> {
        	com.stayfit.userservice.DietDay dietDayWsdl = new com.stayfit.userservice.DietDay();
        	
        	dietDay.getBreakfast().forEach(breakfast -> {
	        	com.stayfit.userservice.Breakfast breakfastWsdl = new com.stayfit.userservice.Breakfast();
	        	BeanUtils.copyProperties(breakfast, breakfastWsdl);
	        	dietDayWsdl.getBreakfast().add(breakfastWsdl);
	        });
	        
        	dietDay.getLunch().forEach(lunch -> {
	        	com.stayfit.userservice.Lunch lunchWsdl = new com.stayfit.userservice.Lunch();
	        	BeanUtils.copyProperties(lunch, lunchWsdl);
	        	dietDayWsdl.getLunch().add(lunchWsdl);
	        });
	        
        	dietDay.getDinner().forEach(dinner -> {
	        	com.stayfit.userservice.Dinner dinnerWsdl = new com.stayfit.userservice.Dinner();
	        	BeanUtils.copyProperties(dinner, dinnerWsdl);
	        	dietDayWsdl.getDinner().add(dinnerWsdl);
	        });
	        
        	dietDay.getOther().forEach(other -> {
	        	com.stayfit.userservice.Other otherWsdl = new com.stayfit.userservice.Other();
	        	BeanUtils.copyProperties(other, otherWsdl);
	        	dietDayWsdl.getOther().add(otherWsdl);
	        });
        	
        	weekWsdl.add(dietDayWsdl);
        });
	        
        userDietWsdl.getWeek().addAll(weekWsdl);
        
        return userDietWsdl;
    }
	
	public UserDiet convertWsdl(UserDiet userDiet, com.stayfit.userservice.UserDiet userDietWsdl) {

		userDiet.setUserId(userDietWsdl.getUserId());
		userDiet.setNutritionistId(userDietWsdl.getNutritionistId());

        List<DietDay> week = new ArrayList<DietDay>();
        
        userDietWsdl.getWeek().forEach(dietDayWsdl -> {
        	
        	List<Breakfast> breakfastList = new ArrayList<Breakfast>();
            List<Lunch> lunchList = new ArrayList<Lunch>();
            List<Dinner> dinnerList = new ArrayList<Dinner>();
            List<Other> otherList = new ArrayList<Other>();
            
        	DietDay dietDay = new DietDay();
        	
        	dietDayWsdl.getBreakfast().forEach(breakfastWsdl -> {
            	Breakfast breakfast = new Breakfast();
            	BeanUtils.copyProperties(breakfastWsdl, breakfast);
            	breakfastList.add(breakfast);
            });
        	dietDay.getBreakfast().addAll(breakfastList);
	        
        	dietDayWsdl.getLunch().forEach(lunchWsdl -> {
            	Lunch lunch = new Lunch();
            	BeanUtils.copyProperties(lunchWsdl, lunch);
            	lunchList.add(lunch);
            });
        	dietDay.getLunch().addAll(lunchList);
	        
        	dietDayWsdl.getDinner().forEach(dinnerWsdl -> {
            	Dinner dinner = new Dinner();
            	BeanUtils.copyProperties(dinnerWsdl, dinner);
            	dinnerList.add(dinner);
            });
        	dietDay.getDinner().addAll(dinnerList);
	        
        	dietDayWsdl.getOther().forEach(otherWsdl -> {
            	Other other = new Other();
            	BeanUtils.copyProperties(otherWsdl, other);
            	otherList.add(other);
            });
        	dietDay.getOther().addAll(otherList);
        	
        	week.add(dietDay);
        });
        
    	userDiet.getWeek().clear();
    	userDiet.getWeek().addAll(week);
    	
        return userDiet;
    }
	
}
