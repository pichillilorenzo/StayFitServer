package com.stayfit.userhistoryservice.app.service;

import java.util.Date;
import java.util.List;

import com.stayfit.userhistoryservice.app.exception.ResourceNotFoundException;
import com.stayfit.userhistoryservice.app.model.UserHistory;

public interface UserHistoryService {

	/**
	 * 
	 * It returns all the user's histories by his user id.
	 */
	List<UserHistory> getUserHistoryByUserId(Long userId) throws ResourceNotFoundException;

	/**
	 * 
	 * It returns the user's history by history date.
	 */
	UserHistory getUserHistoryByDate(Long userId, Date date) throws ResourceNotFoundException;

	/**
	 * 
	 * It saves/updates the user's history into the system.
	 */
	UserHistory saveUserHistory(UserHistory userHistory);

}