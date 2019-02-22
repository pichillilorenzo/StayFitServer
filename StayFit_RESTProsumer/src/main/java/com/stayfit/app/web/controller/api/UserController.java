/**
 * 
 */
package com.stayfit.app.web.controller.api;

import java.util.List;
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

import com.stayfit.app.service.UserService;
import com.stayfit.userservice.User;
import com.stayfit.userdietservice.UserDiet;
import com.stayfit.userdietservice.UserDietRequest;
import com.stayfit.userhistoryservice.UserHistory;

/**
 * 
 * @RestController annotation is used to create RESTful web services.
 * This Rest Controller manages the users of the system.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * This method maps the HTTP GET requests incoming on the route "/api/v1/users/{id}"
	 * and produces an application/json response.
	 * 
	 * It returns the user by his id.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody User getUserById(@PathVariable("id") Long id) {
		return userService.getUserById(id);
	}
	
	/**
	 * This method maps the HTTP POST requests incoming on the route "/api/v1/users".
	 * It consumes an application/json request and produces an application/json response.
	 * 
	 * It is used to register the user into the system.
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody User registerUser(@RequestBody Map<String, Object> payload) {
		return userService.registerUser(payload);
	}
	
	/**
	 * This method maps the HTTP PUT requests incoming on the route "/api/v1/users/{id}".
	 * It consumes an application/json request and produces an application/json response.
	 * 
	 * It updates the user with his new fields.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody User updateUser(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		return userService.updateUser(id, payload);
	}
	
	/**
	 * This method maps the HTTP GET requests incoming on the route "/api/v1/users/{id}/history/{date}".
	 * It consumes an application/json request and produces an application/json response.
	 * 
	 * It returns the user's history by history date.
	 */
	@RequestMapping(value = "/{id}/history/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserHistory getUserHistoryByDate(@PathVariable("id") Long id,
			@PathVariable("date") String date) {
		return userService.getUserHistoryByDate(id, date);
	}
	
	/**
	 * This method maps the HTTP POST requests incoming on the route "/api/v1/users/{id}/history/{date}".
	 * It consumes an application/json request and produces an application/json response.
	 * 
	 * It saves/updates the user's history into the system.
	 */
	@RequestMapping(value = "/{id}/history/{date}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserHistory saveUserHistory(@PathVariable("id") Long id, @PathVariable("date") String date,
			@RequestBody Map<String, Object> payload) {
		return userService.saveUserHistory(id, date, payload);
	}
	
	/**
	 * This method maps the HTTP GET requests incoming on the route "/api/v1/users/diet-requests".
	 * It consumes an application/json request and produces an application/json response.
	 * 
	 * It returns all of the user's diet requests that are not completed yet.
	 */
	@RequestMapping(value = "/diet-requests", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<UserDietRequest> getAllUserDietRequestNotCompleted() {
		return userService.getAllUserDietRequestNotCompleted();
	}
	
	/**
	 * This method maps the HTTP GET requests incoming on the route "/api/v1/users/{id}/diet-requests".
	 * It consumes an application/json request and produces an application/json response.
	 * 
	 * It returns the user's diet request that is not completed yet.
	 */
	@RequestMapping(value = "/{id}/diet-request", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserDietRequest getUserDietRequestNotCompletedByUserId(@PathVariable("id") Long id) {
		return userService.getUserDietRequestNotCompletedByUserId(id);
	}
	
	/**
	 * This method maps the HTTP POST requests incoming on the route "/api/v1/users/{id}/diet-requests".
	 * It consumes an application/json request and produces an application/json response.
	 * 
	 * It saves/updates the user's diet request into the system.
	 */
	@RequestMapping(value = "/{id}/diet-request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserDietRequest saveUserDietRequest(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		return userService.saveUserDietRequest(id, payload);
	}
	
	/**
	 * This method maps the HTTP GET requests incoming on the route "/api/v1/users/{id}/diet".
	 * It consumes an application/json request and produces an application/json response.
	 * 
	 * It returns the user's diet by his user id.
	 */
	@RequestMapping(value = "/{id}/diet", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserDiet getUserDiet(@PathVariable("id") Long id) {
		return userService.getUserDiet(id);
	}
	
	/**
	 * This method maps the HTTP POST requests incoming on the route "/api/v1/users/{id}/diet".
	 * It consumes an application/json request and produces an application/json response.
	 * 
	 * It saves/updates the user's diet into the system.
	 */
	@RequestMapping(value = "/{id}/diet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserDiet saveUserDiet(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		return userService.saveUserDiet(id, payload);
	}
}
