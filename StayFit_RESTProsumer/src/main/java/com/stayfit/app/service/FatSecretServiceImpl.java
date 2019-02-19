/**
 * 
 */
package com.stayfit.app.service;

import com.stayfit.app.exception.ResourceNotFoundException;
import com.stayfit.fatsecretservice.FatSecretService;
import com.stayfit.fatsecretservice.FatSecretServicePortType;
import com.stayfit.fatsecretservice.GetfoodByIdRequest;
import com.stayfit.fatsecretservice.GetfoodByIdResponse;
import com.stayfit.fatsecretservice.GetfoodByNameRequest;
import com.stayfit.fatsecretservice.GetfoodByNameResponse;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
    
    public com.stayfit.fatsecretservice.Foods search(@RequestBody Map<String, Object> payload) throws ResourceNotFoundException{
    	
    	String name = payload.get("name").toString();
    	Integer kcal = Integer.parseInt(payload.get("kcal").toString());
    	String barcode = payload.get("barcode").toString();
    	
    	FatSecretService Service = new FatSecretService();
    	FatSecretServicePortType fatsecretPort = Service.getFatSecretPort();
    	
    	GetfoodByNameRequest request = new GetfoodByNameRequest();
    	request.setName(name);
    	
    	GetfoodByNameResponse response = fatsecretPort.getFoodByName(request);
    	
    	com.stayfit.fatsecretservice.Foods foods = new com.stayfit.fatsecretservice.Foods();
    	
    	List<com.stayfit.fatsecretservice.Item> items = new ArrayList<>();
    	
    	if (kcal != 0) {
    		response.getFoods().getFood().forEach(listItem-> {
    			Pattern pattern = Pattern.compile("Calories: ([\\d]+)kcal");
    			Matcher matcher = pattern.matcher(listItem.getDescription().toString());
    			if (matcher.find())
    			{	
    				Integer kilocalories =Integer.parseInt(matcher.group(1).toString());
    			    if (kilocalories <= kcal) {
    			    	items.add(listItem);
    			    	
    			    }
    			   
    			}
	        });	
    		
    		foods.getFood().addAll(items);
    		

    	}else{
  
    		foods = response.getFoods();
    	}
    	
    	return foods;

    }
 
}