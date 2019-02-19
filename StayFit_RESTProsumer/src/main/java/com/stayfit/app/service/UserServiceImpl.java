/**
 * 
 */
package com.stayfit.app.service;

import com.stayfit.app.exception.ResourceNotFoundException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.stayfit.userservice.GetUserByIdRequest;
import com.stayfit.userservice.GetUserByIdResponse;
import com.stayfit.userservice.GetUserHistoryByDateRequest;
import com.stayfit.userservice.GetUserHistoryByDateResponse;
import com.stayfit.userservice.RegistrationRequest;
import com.stayfit.userservice.RegistrationResponse;
import com.stayfit.userservice.UpdateUserRequest;
import com.stayfit.userservice.UpdateUserResponse;
import com.stayfit.userservice.User;

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

    	RegistrationRequest request = new RegistrationRequest();
    	request.setUsername(payload.get("username").toString());
    	request.setPassword(payload.get("password").toString());
    	request.setEmail(payload.get("email").toString());
    	request.setGender((boolean) payload.get("gender"));
    	request.setNutritionist((boolean) payload.get("nutritionist"));
    	request.setHeight(Integer.parseInt(payload.get("height").toString()));
    	request.setWeight(Float.parseFloat(payload.get("weight").toString()));

    	RegistrationResponse response = userPort.register(request);
    	com.stayfit.userservice.User userResponse = response.getUser();
    	
    	return userResponse;
    }
	
    public com.stayfit.userservice.User updateUser(Long id, Map<String, Object> payload) throws ResourceNotFoundException {

		com.stayfit.userservice.UserService userService = new com.stayfit.userservice.UserService();
    	UserServicePortType userPort = userService.getUserPort();

    	UpdateUserRequest request = new UpdateUserRequest();
    	request.setId(id);
    	request.setUsername(payload.get("username").toString());
    	request.setPassword(payload.get("password").toString());
    	request.setEmail(payload.get("email").toString());
    	request.setGender((boolean) payload.get("gender"));
    	request.setHeight(Integer.parseInt(payload.get("height").toString()));
    	request.setWeight(Float.parseFloat(payload.get("weight").toString()));
    	
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
	
	public com.stayfit.userservice.UserHistory saveUserHistory(Long id, Map<String, Object> payload) {
		// TODO Auto-generated method stub
		return null;
	}
	
}