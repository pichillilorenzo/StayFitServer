/**
 * 
 */
package com.stayfit.userservice.app.service;

import com.stayfit.userservice.app.exception.ResourceNotFoundException;
import com.stayfit.userservice.app.model.User;
import com.stayfit.userservice.app.model.UserDiet;
import com.stayfit.userservice.app.model.UserHistory;
import com.stayfit.userservice.app.model.UserDietRequest;
import com.stayfit.userservice.app.repository.UserDietRepository;
import com.stayfit.userservice.app.repository.UserDietRequestRepository;
import com.stayfit.userservice.app.repository.UserHistoryRepository;
import com.stayfit.userservice.app.repository.UserRepository;
import com.stayfit.userservice.app.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lorenzo
 *
 */

@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	private UserHistoryRepository userHistoryRepository;
    
    @Autowired
	private UserDietRepository userDietRepository;
    
    @Autowired
	private UserDietRequestRepository userDietRequestRepository;
    
    /**
     * 
	 * It returns the user by his username.
	 */
    @Override
	@Transactional(readOnly = true)
    public User getUserByUsername(String username) throws ResourceNotFoundException {
        return userRepository.findByUsername(username)
        		.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }
    
    /**
	 * 
	 * It returns the user by his id.
	 */
    @Override
	@Transactional(readOnly = true)
    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
    
    /**
	 * 
	 * It saves/updates the user with his new fields.
	 */
    @Override
	@Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
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
    
    /**
	 * 
	 * It returns all of the user's diet requests that are not completed yet.
	 */
    @Override
	@Transactional(readOnly = true)
    public List<UserDietRequest> getAllUserDietRequestNotCompleted() throws ResourceNotFoundException {
        return userDietRequestRepository.findAllNotCompleted()
        		.orElseThrow(() -> new ResourceNotFoundException("List<UserDietRequest>", "", null));
    }
    
    /**
	 * 
	 * It returns the user's diet request that is not completed yet.
	 */
    @Override
	@Transactional(readOnly = true)
    public UserDietRequest getUserDietRequestNotCompletedByUserId(Long userId) throws ResourceNotFoundException {
        return userDietRequestRepository.findNotCompletedByUserId(userId)
        		.orElseThrow(() -> new ResourceNotFoundException("UserDietRequest", "userId", userId));
    }
    
    /**
	 * 
	 * It saves/updates the user's diet request into the system.
	 */
    @Override
	@Transactional
    public UserDietRequest saveUserDietRequest(UserDietRequest userDietRequest) {
        return userDietRequestRepository.save(userDietRequest);
    }
    
    /**
	 * 
	 * It returns the user's diet by his user id.
	 */
    @Override
	@Transactional(readOnly = true)
    public UserDiet getUserDietByUserId(Long userId) throws ResourceNotFoundException {
        return userDietRepository.findByUserId(userId)
        		.orElseThrow(() -> new ResourceNotFoundException("UserDiet", "userId", userId));
    }
    
    /**
	 * 
	 * It saves/updates the user's diet into the system.
	 */
    @Override
	@Transactional
    public UserDiet saveUserDiet(UserDiet userDiet) {
        return userDietRepository.save(userDiet);
    }
}