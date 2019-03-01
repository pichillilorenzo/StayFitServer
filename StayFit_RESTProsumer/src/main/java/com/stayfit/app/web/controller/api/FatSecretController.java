/**
 * 
 */
package com.stayfit.app.web.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fatsecret.platform.model.CompactFood;
import com.stayfit.app.service.FatSecretService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


/**
 * 
 * 
 * @RestController annotation is used to create RESTful web services.
 * This Rest Controller manages the users of the system.
 */

@RestController
@RequestMapping("/api/v1/fatsecret")
public class FatSecretController {
	
	@Autowired
    private FatSecretService fatsecret;
	
	/**
	  * This method maps the HTTP GET requests incoming on the route "/api/v1/fatsecret/getfoodbyid/{id}"
	  * and produces an application/json response.
	  * 
	  * It returns the product by its id.
	  */
	@RequestMapping(value = "/getfoodbyid/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    com.fatsecret.platform.model.Food getFoodById(@PathVariable("id") Long id) {
        return fatsecret.getFoodById(id);
    }
	
	/**
	  * This method maps the HTTP POST requests incoming on the route "/api/v1/fatsecret/search".
	  * It consumes an application/json request and produces an application/json response.
	  * 
	  * It is used to find the products by barcode or name and, in case, also filtered by calories (kcal).
	 * @throws Exception 
	  */
	@RequestMapping(value= "/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<CompactFood> search(@RequestBody Map<String, Object> payload) throws Exception {
        return fatsecret.search(payload);
    }
	
}
