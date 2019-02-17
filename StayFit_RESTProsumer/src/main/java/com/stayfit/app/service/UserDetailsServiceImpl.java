/**
 * 
 */
package com.stayfit.app.service;

import com.stayfit.app.exception.ResourceNotFoundException;
import com.stayfit.app.model.Role;
import com.stayfit.app.model.User;
import org.springframework.ws.client.core.WebServiceTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.stayfit.userservice.UserService;
import com.stayfit.userservice.UserServicePortType;
import com.stayfit.userservice.GetUserByIdRequest;
import com.stayfit.userservice.GetUserByIdResponse;

/**
 * @author lorenzo
 *
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private WebServiceTemplate webServiceTemplate;
	
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, ResourceNotFoundException {
//        User user = userRepository.findByUsername(username);
//
//        if (user != null) {
//	        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//	        for (Role role : user.getRoles()){
//	            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//	        }
//	
//	        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
//        }
        
        throw new ResourceNotFoundException("User", "username", username);
    }
    
//    @Transactional(readOnly = true)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public List<User> getAll() {
//        return userRepository.findAll();
//    }
    
    public com.stayfit.userservice.User getUserById(Long id) throws ResourceNotFoundException {
    	
    	UserService userService = new UserService();
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
}