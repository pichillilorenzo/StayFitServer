/**
 * 
 */
package com.stayfit.app.web.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stayfit.app.service.UserServiceImpl;

import com.stayfit.userservice.User;
import com.stayfit.userservice.UserDiet;
import com.stayfit.userservice.UserDietRequest;
import com.stayfit.userservice.UserHistory;

/**
 * @author lorenzo
 *
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody User getUserById(@PathVariable("id") Long id) {
		return userService.getUserById(id);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody User registerUser(@RequestBody Map<String, Object> payload) {
		return userService.registerUser(payload);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody User updateUser(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		return userService.updateUser(id, payload);
	}

	@RequestMapping(value = "/{id}/history/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserHistory getUserHistoryByDate(@PathVariable("id") Long id,
			@PathVariable("date") String date) {
		return userService.getUserHistoryByDate(id, date);
	}

	@RequestMapping(value = "/{id}/history/{date}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserHistory saveUserHistory(@PathVariable("id") Long id, @PathVariable("date") String date,
			@RequestBody Map<String, Object> payload) {
		return userService.saveUserHistory(id, date, payload);
	}
	
	@RequestMapping(value = "/diet-requests", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<UserDietRequest> getAllUserDietRequestNotCompleted() {
		return userService.getAllUserDietRequestNotCompleted();
	}
	
	@RequestMapping(value = "/{id}/diet-request", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserDietRequest getUserDietRequestNotCompletedByUserId(@PathVariable("id") Long id) {
		return userService.getUserDietRequestNotCompletedByUserId(id);
	}
	
	@RequestMapping(value = "/{id}/diet-request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserDietRequest saveUserDietRequest(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		return userService.saveUserDietRequest(id, payload);
	}
	
	@RequestMapping(value = "/{id}/diet", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserDiet getUserDiet(@PathVariable("id") Long id) {
		return userService.getUserDiet(id);
	}

	@RequestMapping(value = "/{id}/diet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody UserDiet saveUserDiet(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		return userService.saveUserDiet(id, payload);
	}
}
