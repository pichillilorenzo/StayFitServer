/**
 * 
 */
package com.stayfit.app.web.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stayfit.app.model.ListProducts;
import com.stayfit.app.service.AmazonService;

/**
 * 
 * 
 * @RestController annotation is used to create RESTful web services.
 * This Rest Controller manages the users of the system.
 */

@RestController
@RequestMapping("/api/v1/amazon")
public class AmazonController {
	
	@Autowired
    private AmazonService amazon;
	
	
	/**
	  * This method maps the HTTP GET requests incoming on the route "/api/v1/amazon/{name}"
	  * and produces an application/json response.
	  * 
	  * It returns the list of products filtered by product name
	 * @throws Exception 
	  */
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    ListProducts getListFood(@PathVariable("name") String name) throws Exception {
        return amazon.getListFood(name);
    }
	
}
