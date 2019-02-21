package com.stayfit.userdietservice.app.service;

import java.util.List;

import com.stayfit.userdietservice.app.exception.ResourceNotFoundException;
import com.stayfit.userdietservice.app.model.UserDiet;
import com.stayfit.userdietservice.app.model.UserDietRequest;

public interface UserDietService {

	/**
	 * 
	 * It returns all of the user's diet requests that are not completed yet.
	 */
	List<UserDietRequest> getAllUserDietRequestNotCompleted() throws ResourceNotFoundException;

	/**
	 * 
	 * It returns the user's diet request that is not completed yet.
	 */
	UserDietRequest getUserDietRequestNotCompletedByUserId(Long userId) throws ResourceNotFoundException;

	/**
	 * 
	 * It saves/updates the user's diet request into the system.
	 */
	UserDietRequest saveUserDietRequest(UserDietRequest userDietRequest);

	/**
	 * 
	 * It returns the user's diet by his user id.
	 */
	UserDiet getUserDietByUserId(Long userId) throws ResourceNotFoundException;

	/**
	 * 
	 * It saves/updates the user's diet into the system.
	 */
	UserDiet saveUserDiet(UserDiet userDiet);

}