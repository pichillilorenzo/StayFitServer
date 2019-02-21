/**
 * 
 */
package com.stayfit.userhistoryservice.app.util;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.stayfit.userhistoryservice.app.model.Breakfast;
import com.stayfit.userhistoryservice.app.model.Dinner;
import com.stayfit.userhistoryservice.app.model.Lunch;
import com.stayfit.userhistoryservice.app.model.Other;
import com.stayfit.userhistoryservice.app.model.UserHistory;

/**
 * @author lorenzo
 *
 * This utility class is used to covert an user's history model to a WSDL request and vice-versa.
 */
@Component
public class UserHistoryTransformer {
	
	public com.stayfit.userhistoryservice.UserHistory convert(UserHistory userHistory) {
		
		com.stayfit.userhistoryservice.UserHistory userHistoryWsdl = new com.stayfit.userhistoryservice.UserHistory();
        BeanUtils.copyProperties(userHistory, userHistoryWsdl);
        
        GregorianCalendar gregDate = new GregorianCalendar();
        gregDate.setTime(userHistory.getDate());
        try {
			userHistoryWsdl.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
        
        userHistory.getBreakfast().forEach(breakfast -> {
        	com.stayfit.userhistoryservice.Breakfast breakfastWsdl = new com.stayfit.userhistoryservice.Breakfast();
        	BeanUtils.copyProperties(breakfast, breakfastWsdl);
        	userHistoryWsdl.getBreakfast().add(breakfastWsdl);
        });
        
        userHistory.getLunch().forEach(lunch -> {
        	com.stayfit.userhistoryservice.Lunch lunchWsdl = new com.stayfit.userhistoryservice.Lunch();
        	BeanUtils.copyProperties(lunch, lunchWsdl);
        	userHistoryWsdl.getLunch().add(lunchWsdl);
        });
        
        userHistory.getDinner().forEach(dinner -> {
        	com.stayfit.userhistoryservice.Dinner dinnerWsdl = new com.stayfit.userhistoryservice.Dinner();
        	BeanUtils.copyProperties(dinner, dinnerWsdl);
        	userHistoryWsdl.getDinner().add(dinnerWsdl);
        });
        
        userHistory.getOther().forEach(other -> {
        	com.stayfit.userhistoryservice.Other otherWsdl = new com.stayfit.userhistoryservice.Other();
        	BeanUtils.copyProperties(other, otherWsdl);
        	userHistoryWsdl.getOther().add(otherWsdl);
        });
        
        return userHistoryWsdl;
    }
	
	public UserHistory convertWsdl(UserHistory userHistory, com.stayfit.userhistoryservice.UserHistory userHistoryWsdl) {
		
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
