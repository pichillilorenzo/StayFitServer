/**
 * 
 */
package com.stayfit.userhistoryservice.app.service;

import com.stayfit.userhistoryservice.app.exception.ResourceNotFoundException;
import com.stayfit.userhistoryservice.app.model.UserHistory;
import com.stayfit.userhistoryservice.app.repository.UserHistoryRepository;
import com.stayfit.userhistoryservice.app.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 
 *
 */

@Service
public class UserHistoryServiceImpl implements UserHistoryService {
    
    @Autowired
	private UserHistoryRepository userHistoryRepository;
    
    /**
	 * 
	 * It returns all the user's histories by his user id.
	 */
    @Override
	@Transactional(readOnly = true)
    public List<UserHistory> getUserHistoryByUserId(Long userId) throws ResourceNotFoundException {
        return userHistoryRepository.findByUserId(userId)
        		.orElseThrow(() -> new ResourceNotFoundException("List<UserHistory>", "userId", userId));
    }
    
    /**
	 * 
	 * It returns the user's history by history date.
	 */
    @Override
	@Transactional(readOnly = true)
    public UserHistory getUserHistoryByDate(Long userId, Date date) throws ResourceNotFoundException {
        return userHistoryRepository.findByDateBetween(userId, DateUtil.getStartOfDay(date), DateUtil.getEndOfDay(date))
        		.orElseThrow(() -> new ResourceNotFoundException("UserHistory", "date", date));
    }
    
    /**
	 * 
	 * It saves/updates the user's history into the system.
	 */
    @Override
	@Transactional
    public UserHistory saveUserHistory(UserHistory userHistory) {
        return userHistoryRepository.save(userHistory);
    }
    
}