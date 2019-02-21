package com.stayfit.userservice.app.service;

import com.stayfit.userservice.app.exception.ResourceNotFoundException;
import com.stayfit.userservice.app.model.User;

public interface UserService {

	/**
	 * 
	 * It returns the user by his username.
	 */
	User getUserByUsername(String username) throws ResourceNotFoundException;

	/**
	 * 
	 * It returns the user by his id.
	 */
	User getUserById(Long id) throws ResourceNotFoundException;

	/**
	 * 
	 * It saves/updates the user with his new fields.
	 */
	User saveUser(User user);

}