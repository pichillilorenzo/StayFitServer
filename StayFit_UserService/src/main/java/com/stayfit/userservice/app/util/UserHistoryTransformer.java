/**
 * 
 */
package com.stayfit.userservice.app.util;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.stayfit.userservice.app.model.Breakfast;
import com.stayfit.userservice.app.model.Dinner;
import com.stayfit.userservice.app.model.Lunch;
import com.stayfit.userservice.app.model.Other;
import com.stayfit.userservice.app.model.UserHistory;

/**
 * @author lorenzo
 *
 */
@Component
public class UserHistoryTransformer {
	
	public com.stayfit.userservice.UserHistory convert(UserHistory userHistory) {
		
		com.stayfit.userservice.UserHistory userHistoryWsdl = new com.stayfit.userservice.UserHistory();
        BeanUtils.copyProperties(userHistory, userHistoryWsdl);
        
        GregorianCalendar gregDate = new GregorianCalendar();
        gregDate.setTime(userHistory.getDate());
        try {
			userHistoryWsdl.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
        
        userHistory.getBreakfast().forEach(breakfast -> {
        	com.stayfit.userservice.Breakfast breakfastWsdl = new com.stayfit.userservice.Breakfast();
        	BeanUtils.copyProperties(breakfast, breakfastWsdl);
        	userHistoryWsdl.getBreakfast().add(breakfastWsdl);
        });
        
        userHistory.getLunch().forEach(lunch -> {
        	com.stayfit.userservice.Lunch lunchWsdl = new com.stayfit.userservice.Lunch();
        	BeanUtils.copyProperties(lunch, lunchWsdl);
        	userHistoryWsdl.getLunch().add(lunchWsdl);
        });
        
        userHistory.getDinner().forEach(dinner -> {
        	com.stayfit.userservice.Dinner dinnerWsdl = new com.stayfit.userservice.Dinner();
        	BeanUtils.copyProperties(dinner, dinnerWsdl);
        	userHistoryWsdl.getDinner().add(dinnerWsdl);
        });
        
        userHistory.getOther().forEach(other -> {
        	com.stayfit.userservice.Other otherWsdl = new com.stayfit.userservice.Other();
        	BeanUtils.copyProperties(other, otherWsdl);
        	userHistoryWsdl.getOther().add(otherWsdl);
        });
        
        return userHistoryWsdl;
    }
	
	public UserHistory convertWsdl(UserHistory userHistory, com.stayfit.userservice.UserHistory userHistoryWsdl) {
		
        userHistory.setUserId(userHistoryWsdl.getUserId());
        userHistory.setDate(userHistoryWsdl.getDate().toGregorianCalendar().getTime());
        
        List<Breakfast> breakfastList = new ArrayList<Breakfast>();
        List<Lunch> lunchList = new ArrayList<Lunch>();
        List<Dinner> dinnerList = new ArrayList<Dinner>();
        List<Other> otherList = new ArrayList<Other>();
        
        userHistoryWsdl.getBreakfast().forEach(breakfastWsdl -> {
        	Breakfast breakfast = new Breakfast();
        	BeanUtils.copyProperties(breakfastWsdl, breakfast);
        	breakfastList.add(breakfast);
        });
        userHistory.getBreakfast().clear();
        userHistory.getBreakfast().addAll(breakfastList);
        
        userHistoryWsdl.getLunch().forEach(lunchWsdl -> {
        	Lunch lunch = new Lunch();
        	BeanUtils.copyProperties(lunchWsdl, lunch);
        	lunchList.add(lunch);
        });
        userHistory.getLunch().clear();
        userHistory.getLunch().addAll(lunchList);
        
        userHistoryWsdl.getDinner().forEach(dinnerWsdl -> {
        	Dinner dinner = new Dinner();
        	BeanUtils.copyProperties(dinnerWsdl, dinner);
        	dinnerList.add(dinner);
        });
        userHistory.getDinner().clear();
        userHistory.getDinner().addAll(dinnerList);
        
        userHistoryWsdl.getOther().forEach(otherWsdl -> {
        	Other other = new Other();
        	BeanUtils.copyProperties(otherWsdl, other);
        	otherList.add(other);
        });
        userHistory.getOther().clear();
        userHistory.getOther().addAll(otherList);

        return userHistory;
    }
	
}
