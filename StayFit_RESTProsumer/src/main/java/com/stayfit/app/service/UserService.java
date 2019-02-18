/**
 * 
 */
package com.stayfit.app.service;

import com.stayfit.app.exception.ResourceNotFoundException;

import java.util.Map;

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
import com.stayfit.userservice.RegistrationRequest;
import com.stayfit.userservice.RegistrationResponse;

/**
 * @author lorenzo
 *
 */

@Service
public class UserService implements UserDetailsService {
    
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public com.stayfit.userservice.User getUserById(Long id) throws ResourceNotFoundException {
    	
		com.stayfit.userservice.UserService userService = new com.stayfit.userservice.UserService();
    	UserServicePortType userPort = userService.getUserPort();
    	
    	GetUserByIdRequest request = new GetUserByIdRequest();
    	request.setId(id);
    	
    	GetUserByIdResponse response = userPort.getUserById(request);
    	
    	com.stayfit.userservice.User user = response.getUser();
    	if (user != null) {
    		return user;
    	}
    	
    	throw new ResourceNotFoundException("User", "id", id);
    }
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public com.stayfit.userservice.User registerUser(Map<String, Object> payload) throws ResourceNotFoundException {

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
    	
    	if (userResponse != null) {
    		return userResponse;
    	}
    	
    	throw new ResourceNotFoundException("User", "id", null);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
}