package ${package}.app.service;

import java.util.List;
import java.util.Map;

import ${package}.app.exception.ResourceNotFoundException;
import ${package}.userdietservice.UserDiet;
import ${package}.userdietservice.UserDietRequest;
import ${package}.userhistoryservice.UserHistory;

public interface UserService {
	
	/**
	 * 
	 * It returns the user by his id.
	 */
	${package}.userservice.User getUserById(Long id) throws ResourceNotFoundException;
	
	/**
	 * 
	 * It returns the user by his username.
	 */
	${package}.userservice.User getUserByUsername(String username) throws ResourceNotFoundException;

	/**
	 * 
	 * It is used to register the user into the system.
	 */
	${package}.userservice.User registerUser(Map<String, Object> payload);

	/**
	 * 
	 * It updates the user with his new fields.
	 */
	${package}.userservice.User updateUser(Long id, Map<String, Object> payload) throws ResourceNotFoundException;

	/**
	 * 
	 * It returns the user's history by history date.
	 */
	UserHistory getUserHistoryByDate(Long id, String date) throws ResourceNotFoundException;

	/**
	 * 
	 * It saves/updates the user's history into the system.
	 */
	UserHistory saveUserHistory(Long id, String date, Map<String, Object> payload);

	/**
	 * 
	 * It returns all of the user's diet requests that are not completed yet.
	 */
	List<UserDietRequest> getAllUserDietRequestNotCompleted();

	/**
	 * 
	 * It returns the user's diet request that is not completed yet.
	 */
	UserDietRequest getUserDietRequestNotCompletedByUserId(Long id);

	/**
	 * 
	 * It saves/updates the user's diet request into the system.
	 */
	UserDietRequest saveUserDietRequest(Long id, Map<String, Object> payload);

	/**
	 * 
	 * It returns the user's diet by his user id.
	 */
	UserDiet getUserDiet(Long id);

	/**
	 * 
	 * It saves/updates the user's diet into the system.
	 */
	UserDiet saveUserDiet(Long id, Map<String, Object> payload);

}