package com.stayfit.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.stayfit.app.exception.ResourceNotFoundException;
import com.stayfit.userdietservice.UserDiet;
import com.stayfit.userdietservice.UserDietRequest;
import com.stayfit.userhistoryservice.UserHistory;

public interface UserService {

	/**
	 * 
	 * It returns the user by his id.
	 */
	com.stayfit.userservice.User getUserById(Long id) throws ResourceNotFoundException;

	/**
	 * 
	 * It returns the user by his username.
	 */
	com.stayfit.userservice.User getUserByUsername(String username) throws ResourceNotFoundException;

	/**
	 * 
	 * It is used to register the user into the system.
	 */
	com.stayfit.userservice.User registerUser(Map<String, Object> payload) throws Exception;

	/**
	 * 
	 * It updates the user with his new fields.
	 */
	com.stayfit.userservice.User updateUser(Long id, Map<String, Object> payload) throws ResourceNotFoundException;

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	/**
	 * 
	 * It returns the user's history by history date.
	 */
	UserHistory getUserHistoryByDate(Long id, String date) throws ResourceNotFoundException;

	/**
	 * 
	 * It saves/updates the user's history into the system.
	 */
	UserHistory saveUserHistory(Long id, String date, Map<String, Object> payload) throws Exception;

	/**
	 * 
	 * It returns all of the user's diet requests that are not completed yet.
	 */
	List<UserDietRequest> getAllUserDietRequestNotCompleted() throws Exception;

	/**
	 * 
	 * It returns the user's diet request that is not completed yet.
	 */
	UserDietRequest getUserDietRequestNotCompletedByUserId(Long id) throws Exception;

	/**
	 * 
	 * It saves/updates the user's diet request into the system.
	 */
	UserDietRequest saveUserDietRequest(Long id, Map<String, Object> payload) throws Exception;

	/**
	 * 
	 * It returns the user's diet by his user id.
	 */
	UserDiet getUserDiet(Long id) throws Exception;

	/**
	 * 
	 * It saves/updates the user's diet into the system.
	 */
	UserDiet saveUserDiet(Long id, Map<String, Object> payload) throws Exception;

}