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

    @Override
	@Transactional(readOnly = true)
    public User getUserByUsername(String username) throws ResourceNotFoundException {
        return userRepository.findByUsername(username)
        		.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    @Override
	@Transactional(readOnly = true)
    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
	@Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
	@Transactional(readOnly = true)
    public List<UserHistory> getUserHistoryByUserId(Long userId) throws ResourceNotFoundException {
        return userHistoryRepository.findByUserId(userId)
        		.orElseThrow(() -> new ResourceNotFoundException("List<UserHistory>", "userId", userId));
    }

    @Override
	@Transactional(readOnly = true)
    public UserHistory getUserHistoryByDate(Long userId, Date date) throws ResourceNotFoundException {
        return userHistoryRepository.findByDateBetween(userId, DateUtil.getStartOfDay(date), DateUtil.getEndOfDay(date))
        		.orElseThrow(() -> new ResourceNotFoundException("UserHistory", "date", date));
    }

    @Override
	@Transactional
    public UserHistory saveUserHistory(UserHistory userHistory) {
        return userHistoryRepository.save(userHistory);
    }

    @Override
	@Transactional(readOnly = true)
    public UserDiet getUserDietByUserId(Long userId) throws ResourceNotFoundException {
        return userDietRepository.findByUserId(userId)
        		.orElseThrow(() -> new ResourceNotFoundException("UserDiet", "userId", userId));
    }

    @Override
	@Transactional
    public UserDiet saveUserDiet(UserDiet userDiet) {
        return userDietRepository.save(userDiet);
    }

    @Override
	@Transactional(readOnly = true)
    public List<UserDietRequest> getAllUserDietRequestNotCompleted() throws ResourceNotFoundException {
        return userDietRequestRepository.findAllNotCompleted()
        		.orElseThrow(() -> new ResourceNotFoundException("List<UserDietRequest>", "", null));
    }
    
    @Override
	@Transactional(readOnly = true)
    public UserDietRequest getUserDietRequestNotCompletedByUserId(Long userId) throws ResourceNotFoundException {
        return userDietRequestRepository.findNotCompletedByUserId(userId)
        		.orElseThrow(() -> new ResourceNotFoundException("UserDietRequest", "userId", userId));
    }

    @Override
	@Transactional
    public UserDietRequest saveUserDietRequest(UserDietRequest userDietRequest) {
        return userDietRequestRepository.save(userDietRequest);
    }
}