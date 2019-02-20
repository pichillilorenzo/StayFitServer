package com.stayfit.userservice.app.service;

import java.util.Date;
import java.util.List;

import com.stayfit.userservice.app.exception.ResourceNotFoundException;
import com.stayfit.userservice.app.model.User;
import com.stayfit.userservice.app.model.UserDiet;
import com.stayfit.userservice.app.model.UserDietRequest;
import com.stayfit.userservice.app.model.UserHistory;

public interface UserService {

	User getUserByUsername(String username) throws ResourceNotFoundException;

	User getUserById(Long id) throws ResourceNotFoundException;

	User saveUser(User user);

	List<UserHistory> getUserHistoryByUserId(Long userId) throws ResourceNotFoundException;

	UserHistory getUserHistoryByDate(Long userId, Date date) throws ResourceNotFoundException;

	UserHistory saveUserHistory(UserHistory userHistory);

	UserDiet getUserDietByUserId(Long userId) throws ResourceNotFoundException;

	UserDiet saveUserDiet(UserDiet userDiet);

	List<UserDietRequest> getAllUserDietRequestNotCompleted() throws ResourceNotFoundException;

	UserDietRequest getUserDietRequestNotCompletedByUserId(Long userId) throws ResourceNotFoundException;

	UserDietRequest saveUserDietRequest(UserDietRequest userDietRequest);

}