/**
 * 
 */
package com.stayfit.app.web.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stayfit.app.service.FatSecretServiceImpl;
import com.stayfit.fatsecretservice.Food;

/**
 * @author Matteo
 *
 */
@RestController
@RequestMapping("/api/v1/fatsecret")
public class FatSecretController {
	
	@Autowired
    private FatSecretServiceImpl fatsecret;
	
	
	@RequestMapping(value = "/getfoodbyid/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Food getFoodById(@PathVariable("id") Long id) {
		System.out.println(id);
        return fatsecret.getFoodById(id);
    }
	
	
	
	@RequestMapping(value= "/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public com.stayfit.fatsecretservice.Foods search(@RequestBody Map<String, Object> payload) {
        return fatsecret.search(payload);
    }
	
}
