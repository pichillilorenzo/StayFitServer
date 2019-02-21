/**
 * 
 */
package com.stayfit.fatsecretservice.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatsecret.platform.services.FatsecretService;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.Food;

import java.util.List;
/**
 * @author Matteo
 *
 */

@Service
public class FatSecretServiceImpl implements FatSecretService {
	
	/* we will need to initialize Fatsecret Service with our Fatsecret Application 
	 * Consumer Key and associated Consumer Secret.
	 */
	String key = "b81048f4a42a486a86e9dd9cf1b920e9";
	String secret = "dccaedac974841ebacfce130397d15f3";
	FatsecretService service = new FatsecretService(key, secret);
	
	/* we use getFood method from FatsecretService in order to get 
	 * detailed information of the food item by its id.
	 */
	@Override
	@Transactional(readOnly = true)
	public Food getFoodById(Long id){
		return service.getFood(id);
	}
	
	
	/*
	 * we  use searchFoods method from FatsecretService in order to get 
	 * a list of food items by the name that the user insert 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CompactFood> getFoodByName(String name){
		Response<CompactFood> response = service.searchFoods(name);
		List<CompactFood> results = response.getResults();
		return results;
	}

}