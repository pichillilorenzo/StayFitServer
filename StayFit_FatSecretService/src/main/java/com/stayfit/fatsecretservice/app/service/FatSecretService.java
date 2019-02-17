/**
 * 
 */
package com.stayfit.fatsecretservice.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatsecret.platform.services.FatsecretService;
import com.fatsecret.platform.services.Response;
import com.stayfit.fatsecretservice.app.exception.ResourceNotFoundException;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.Food;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Matteo
 *
 */

@Service
public class FatSecretService {
	
	String key = "b81048f4a42a486a86e9dd9cf1b920e9";
	String secret = "dccaedac974841ebacfce130397d15f3";
	FatsecretService service = new FatsecretService(key, secret);
	
	@Transactional(readOnly = true)
	public Food getFoodById(Long id){
		return service.getFood(id);
	}
	
	
	@Transactional(readOnly = true)
	public List<CompactFood> getFoodByName(String name){
		Response<CompactFood> response = service.searchFoods(name);
		List<CompactFood> results = response.getResults();
		return results;
	}

}