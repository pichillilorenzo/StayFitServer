/**
 * 
 */
package com.stayfit.app.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stayfit.app.service.UserDetailsServiceImpl;

import com.stayfit.userservice.User;

/**
 * @author lorenzo
 *
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
    private UserDetailsServiceImpl userDetailsService;
	
//	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.OK)
//    public @ResponseBody
//    List<User> getAll() {
//        return userDetailsService.getAll();
//    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    User getUserById(@PathVariable("id") Long id) {
        return userDetailsService.getUserById(id);
    }
	
}
