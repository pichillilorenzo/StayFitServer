package com.stayfit.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.stayfit.app.exception.ResourceNotFoundException;
import com.stayfit.userservice.UserDiet;
import com.stayfit.userservice.UserDietRequest;
import com.stayfit.userservice.UserHistory;

public interface UserService {

	com.stayfit.userservice.User getUserById(Long id) throws ResourceNotFoundException;

	com.stayfit.userservice.User registerUser(Map<String, Object> payload);

	com.stayfit.userservice.User updateUser(Long id, Map<String, Object> payload) throws ResourceNotFoundException;

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	com.stayfit.userservice.UserHistory getUserHistoryByDate(Long id, String date) throws ResourceNotFoundException;

	UserHistory saveUserHistory(Long id, String date, Map<String, Object> payload);

	List<UserDietRequest> getAllUserDietRequestNotCompleted();

	UserDietRequest getUserDietRequestNotCompletedByUserId(Long id);

	UserDietRequest saveUserDietRequest(Long id, Map<String, Object> payload);

	UserDiet getUserDiet(Long id);

	UserDiet saveUserDiet(Long id, Map<String, Object> payload);

}