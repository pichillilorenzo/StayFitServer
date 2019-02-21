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
 * @RestController annotation is used to create RESTful web services.
 * This Rest Controller manages the users of the system.
 */

@RestController
@RequestMapping("/api/v1/fatsecret")
public class FatSecretController {
	
	@Autowired
    private FatSecretServiceImpl fatsecret;
	
	/**
	  * This method maps the HTTP GET requests incoming on the route "/api/v1/fatsecret/getfoodbyid/{id}"
	  * and produces an application/json response.
	  * 
	  * It returns the product by its id.
	  */
	@RequestMapping(value = "/getfoodbyid/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Food getFoodById(@PathVariable("id") Long id) {
		System.out.println(id);
        return fatsecret.getFoodById(id);
    }
	
	/**
	  * This method maps the HTTP POST requests incoming on the route "/api/v1/fatsecret/search".
	  * It consumes an application/json request and produces an application/json response.
	  * 
	  * It is used to find the products with barcode or name or Kcal and barcode or name and Kcal .
	  */
	@RequestMapping(value= "/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public com.stayfit.fatsecretservice.Foods search(@RequestBody Map<String, Object> payload) {
        return fatsecret.search(payload);
    }
	
}
