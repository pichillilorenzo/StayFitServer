/**
 * 
 */
package com.stayfit.app.service;

import com.stayfit.app.exception.ResourceNotFoundException;
import com.stayfit.app.model.Role;
import com.stayfit.app.model.User;
import com.stayfit.fatsecretservice.FatSecretService;
import com.stayfit.fatsecretservice.FatSecretServicePortType;
import com.stayfit.fatsecretservice.GetfoodByIdRequest;
import com.stayfit.fatsecretservice.GetfoodByIdResponse;
import com.stayfit.fatsecretservice.GetfoodByNameRequest;
import com.stayfit.fatsecretservice.GetfoodByNameResponse;

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
 * @author Matteo;
 *
 */

@Service
public class FatSecretServiceImpl  {
	
	
   
    public com.stayfit.fatsecretservice.Food getFoodById(Long id) throws ResourceNotFoundException {
    	
    	FatSecretService Service = new FatSecretService();
    	FatSecretServicePortType fatsecretPort = Service.getFatSecretPort();
    	
    	GetfoodByIdRequest request = new GetfoodByIdRequest();
    	request.setId(id);
    	
    	GetfoodByIdResponse response = fatsecretPort.getFoodById(request);
    	
    	com.stayfit.fatsecretservice.Food food = response.getFood();
    	if (food != null) {
    		return food;
    	}
    	
    	throw new ResourceNotFoundException("Food", "id", id);
    }
    
    
   public com.stayfit.fatsecretservice.Foods getFoodByname(String name) throws ResourceNotFoundException {
    	
    	FatSecretService Service = new FatSecretService();
    	FatSecretServicePortType fatsecretPort = Service.getFatSecretPort();
    	
    	GetfoodByNameRequest request = new GetfoodByNameRequest();
    	request.setName(name);
    	
    	GetfoodByNameResponse response = fatsecretPort.getFoodByName(request);
    	
    	com.stayfit.fatsecretservice.Foods foods = response.getFoods();
    	if (foods != null) {
    		return foods;
    	}
    	
    	throw new ResourceNotFoundException("Foods", "name", name);
    } 
    
}