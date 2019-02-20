/**
 * 
 */
package com.stayfit.app.service;

import com.stayfit.app.exception.ResourceNotFoundException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stayfit.userservice.UserServicePortType;
import com.stayfit.userservice.Breakfast;
import com.stayfit.userservice.Dinner;
import com.stayfit.userservice.Lunch;
import com.stayfit.userservice.Other;
import com.stayfit.userservice.GetUserByIdRequest;
import com.stayfit.userservice.GetUserByIdResponse;
import com.stayfit.userservice.GetUserHistoryByDateRequest;
import com.stayfit.userservice.GetUserHistoryByDateResponse;
import com.stayfit.userservice.RegistrationRequest;
import com.stayfit.userservice.RegistrationResponse;
import com.stayfit.userservice.SaveUserHistoryRequest;
import com.stayfit.userservice.SaveUserHistoryResponse;
import com.stayfit.userservice.UpdateUserRequest;
import com.stayfit.userservice.UpdateUserResponse;
import com.stayfit.userservice.User;
import com.stayfit.userservice.UserHistory;

import javax.xml.ws.soap.SOAPFaultException;

/**
 * @author lorenzo
 *
 */

@Service
public class UserServiceImpl implements UserDetailsService {
    
    public com.stayfit.userservice.User getUserById(Long id) throws ResourceNotFoundException {
    	
		com.stayfit.userservice.UserService userService = new com.stayfit.userservice.UserService();
    	UserServicePortType userPort = userService.getUserPort();
    	
    	GetUserByIdRequest request = new GetUserByIdRequest();
    	request.setId(id);
    	
    	try {
	    	GetUserByIdResponse response = userPort.getUserById(request);
	    	com.stayfit.userservice.User user = response.getUser();
	    	
	    	return user;
	    }
		catch (SOAPFaultException ex) {
    		throw new ResourceNotFoundException("User", "id", id);
		}
    }
	
    public com.stayfit.userservice.User registerUser(Map<String, Object> payload) {

		com.stayfit.userservice.UserService userService = new com.stayfit.userservice.UserService();
    	UserServicePortType userPort = userService.getUserPort();
    	
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	GregorianCalendar gregDate = new GregorianCalendar();
    	
    	try {
    		gregDate.setTime(formatter.parse(payload.get("birthDate").toString()));
		} catch (ParseException e) {
		  e.printStackTrace();
		}
    	
    	RegistrationRequest request = new RegistrationRequest();
    	request.setUsername(payload.get("username").toString());
    	request.setPassword(payload.get("password").toString());
    	request.setEmail(payload.get("email").toString());
    	request.setGender((boolean) payload.get("gender"));
    	request.setNutritionist((boolean) payload.get("nutritionist"));
    	request.setHeight(Integer.parseInt(payload.get("height").toString()));
    	request.setWeight(Float.parseFloat(payload.get("weight").toString()));
    	try {
    		request.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

    	RegistrationResponse response = userPort.register(request);
    	com.stayfit.userservice.User userResponse = response.getUser();
    	
    	return userResponse;
    }
	
    public com.stayfit.userservice.User updateUser(Long id, Map<String, Object> payload) throws ResourceNotFoundException {

		com.stayfit.userservice.UserService userService = new com.stayfit.userservice.UserService();
    	UserServicePortType userPort = userService.getUserPort();
    	
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	GregorianCalendar gregDate = new GregorianCalendar();
    	
    	try {
    		gregDate.setTime(formatter.parse(payload.get("birthDate").toString()));
		} catch (ParseException e) {
		  e.printStackTrace();
		}

    	UpdateUserRequest request = new UpdateUserRequest();
    	request.setId(id);
    	request.setUsername(payload.get("username").toString());
    	request.setPassword(payload.get("password").toString());
    	request.setEmail(payload.get("email").toString());
    	request.setGender((boolean) payload.get("gender"));
    	request.setHeight(Integer.parseInt(payload.get("height").toString()));
    	request.setWeight(Float.parseFloat(payload.get("weight").toString()));
    	try {
    		request.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
    	
    	try {
	    	UpdateUserResponse response = userPort.updateUser(request);
	    	com.stayfit.userservice.User userResponse = response.getUser();
	    	
	    	return userResponse;
    	}
    	catch (SOAPFaultException ex) {
    		throw new ResourceNotFoundException("User", "id", id);
    	}
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public com.stayfit.userservice.UserHistory getUserHistoryByDate(Long id, String date) throws ResourceNotFoundException {
		
		com.stayfit.userservice.UserService userService = new com.stayfit.userservice.UserService();
    	UserServicePortType userPort = userService.getUserPort();
    	
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	GregorianCalendar gregDate = new GregorianCalendar();
    	
    	try {
    		gregDate.setTime(formatter.parse(date));
		} catch (ParseException e) {
		  e.printStackTrace();
		}
        
    	GetUserHistoryByDateRequest request = new GetUserHistoryByDateRequest();
    	request.setUserId(id);
    	try {
    		request.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
    	
    	try {
	    	GetUserHistoryByDateResponse response = userPort.getUserHistoryByDate(request);
	    	com.stayfit.userservice.UserHistory userHistoryResponse = response.getUserHistory();
	    	return userHistoryResponse;
    	}
    	catch (SOAPFaultException ex) {
    		throw new ResourceNotFoundException("UserHistory", "date", date);
    	}
    	
	}
	
	public UserHistory saveUserHistory(Long id, String date, Map<String, Object> payload) {
		
		com.stayfit.userservice.UserService userService = new com.stayfit.userservice.UserService();
    	UserServicePortType userPort = userService.getUserPort();
    	
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	GregorianCalendar gregDate = new GregorianCalendar();
    	
    	try {
    		gregDate.setTime(formatter.parse(date));
		} catch (ParseException e) {
		  e.printStackTrace();
		}
    	
    	UserHistory userHistory = new UserHistory();
    	userHistory.setUserId(id);
        
    	try {
    		userHistory.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
    	
    	List<Breakfast> breakfastList = new ArrayList<Breakfast>();
        List<Lunch> lunchList = new ArrayList<Lunch>();
        List<Dinner> dinnerList = new ArrayList<Dinner>();
        List<Other> otherList = new ArrayList<Other>();
        
        ((ArrayList<Map<String, Object>>) payload.get("breakfast")).forEach(breakfastMap -> {
        	Breakfast breakfast = new Breakfast();
        	breakfast.setFoodId(Long.parseLong(breakfastMap.get("foodId").toString()));
        	breakfast.setAmount(Float.parseFloat(breakfastMap.get("amount").toString()));
        	breakfast.setUnit(breakfastMap.get("unit").toString());
        	breakfastList.add(breakfast);
        });
        userHistory.getBreakfast().clear();
        userHistory.getBreakfast().addAll(breakfastList);
        
        ((ArrayList<Map<String, Object>>) payload.get("lunch")).forEach(lunchMap -> {
        	Lunch lunch = new Lunch();
        	lunch.setFoodId(Long.parseLong(lunchMap.get("foodId").toString()));
        	lunch.setAmount(Float.parseFloat(lunchMap.get("amount").toString()));
        	lunch.setUnit(lunchMap.get("unit").toString());
        	lunchList.add(lunch);
        });
        userHistory.getLunch().clear();
        userHistory.getLunch().addAll(lunchList);
        
        ((ArrayList<Map<String, Object>>) payload.get("dinner")).forEach(dinnerMap -> {
        	Dinner dinner = new Dinner();
        	dinner.setFoodId(Long.parseLong(dinnerMap.get("foodId").toString()));
        	dinner.setAmount(Float.parseFloat(dinnerMap.get("amount").toString()));
        	dinner.setUnit(dinnerMap.get("unit").toString());
        	dinnerList.add(dinner);
        });
        userHistory.getDinner().clear();
        userHistory.getDinner().addAll(dinnerList);
        
        ((ArrayList<Map<String, Object>>) payload.get("other")).forEach(otherMap -> {
        	Other other = new Other();
        	other.setFoodId(Long.parseLong(otherMap.get("foodId").toString()));
        	other.setAmount(Float.parseFloat(otherMap.get("amount").toString()));
        	other.setUnit(otherMap.get("unit").toString());
        	otherList.add(other);
        });
        userHistory.getOther().clear();
        userHistory.getOther().addAll(otherList);
    	
    	SaveUserHistoryRequest request = new SaveUserHistoryRequest();
    	request.setUserHistory(userHistory);
    	
		SaveUserHistoryResponse response = userPort.saveUserHistory(request);
    	com.stayfit.userservice.UserHistory userHistoryResponse = response.getUserHistory();
    	return userHistoryResponse;
    	
	}
	
}