/**
 * 
 */
package com.stayfit.userdietservice.app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.stayfit.userdietservice.app.model.Breakfast;
import com.stayfit.userdietservice.app.model.DietDay;
import com.stayfit.userdietservice.app.model.Dinner;
import com.stayfit.userdietservice.app.model.Lunch;
import com.stayfit.userdietservice.app.model.Other;
import com.stayfit.userdietservice.app.model.UserDiet;

/**
 * @author lorenzo
 *
 * This utility class is used to covert an user's diet model to a WSDL request and vice-versa.
 */
@Component
public class UserDietTransformer {
	public com.stayfit.userdietservice.UserDiet convert(UserDiet userDiet) {
		
		com.stayfit.userdietservice.UserDiet userDietWsdl = new com.stayfit.userdietservice.UserDiet();
        BeanUtils.copyProperties(userDiet, userDietWsdl);
        
        List<com.stayfit.userdietservice.DietDay> weekWsdl = new ArrayList<com.stayfit.userdietservice.DietDay>(7);
        
        userDiet.getWeek().forEach(dietDay -> {
        	com.stayfit.userdietservice.DietDay dietDayWsdl = new com.stayfit.userdietservice.DietDay();
        	
        	dietDay.getBreakfast().forEach(breakfast -> {
	        	com.stayfit.userdietservice.Breakfast breakfastWsdl = new com.stayfit.userdietservice.Breakfast();
	        	BeanUtils.copyProperties(breakfast, breakfastWsdl);
	        	dietDayWsdl.getBreakfast().add(breakfastWsdl);
	        });
	        
        	dietDay.getLunch().forEach(lunch -> {
	        	com.stayfit.userdietservice.Lunch lunchWsdl = new com.stayfit.userdietservice.Lunch();
	        	BeanUtils.copyProperties(lunch, lunchWsdl);
	        	dietDayWsdl.getLunch().add(lunchWsdl);
	        });
	        
        	dietDay.getDinner().forEach(dinner -> {
	        	com.stayfit.userdietservice.Dinner dinnerWsdl = new com.stayfit.userdietservice.Dinner();
	        	BeanUtils.copyProperties(dinner, dinnerWsdl);
	        	dietDayWsdl.getDinner().add(dinnerWsdl);
	        });
	        
        	dietDay.getOther().forEach(other -> {
	        	com.stayfit.userdietservice.Other otherWsdl = new com.stayfit.userdietservice.Other();
	        	BeanUtils.copyProperties(other, otherWsdl);
	        	dietDayWsdl.getOther().add(otherWsdl);
	        });
        	
        	weekWsdl.add(dietDayWsdl);
        });
	        
        userDietWsdl.getWeek().addAll(weekWsdl);
        
        return userDietWsdl;
    }
	
	public UserDiet convertWsdl(UserDiet userDiet, com.stayfit.userdietservice.UserDiet userDietWsdl) {

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
