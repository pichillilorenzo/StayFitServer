/**
 * 
 */
package com.stayfit.fatsecretservice.app.util;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.Food;

/**
 * @author Matteo
 *
 */
@Component
public class FatSecretTransformer {
	
	
	public com.stayfit.fatsecretservice.Food convert(Food food) {
		com.stayfit.fatsecretservice.Food foodWsdl = new com.stayfit.fatsecretservice.Food();
        BeanUtils.copyProperties(food, foodWsdl);
        food.getServings().forEach(listItem-> {
        	com.stayfit.fatsecretservice.Serving sergvingWsdl = new com.stayfit.fatsecretservice.Serving();
        	BeanUtils.copyProperties(listItem, sergvingWsdl);
        	foodWsdl.getServings().add(sergvingWsdl);
        });
        return foodWsdl;
    }
	
	
	public com.stayfit.fatsecretservice.Foods convertbyname(List<CompactFood> food) {
		com.stayfit.fatsecretservice.Foods foodsWsdl = new com.stayfit.fatsecretservice.Foods();
 
        food.forEach(listItem-> {
        	com.stayfit.fatsecretservice.Item itemWsdl = new com.stayfit.fatsecretservice.Item();
        	BeanUtils.copyProperties(listItem, itemWsdl);
        	foodsWsdl.getFood().add(itemWsdl);
        });
        return foodsWsdl;
    }
	
}
