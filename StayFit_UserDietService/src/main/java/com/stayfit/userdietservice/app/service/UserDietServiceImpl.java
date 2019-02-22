/**
 * 
 */
package com.stayfit.userdietservice.app.service;

import com.stayfit.userdietservice.app.exception.ResourceNotFoundException;
import com.stayfit.userdietservice.app.model.UserDiet;
import com.stayfit.userdietservice.app.model.UserDietRequest;
import com.stayfit.userdietservice.app.repository.UserDietRepository;
import com.stayfit.userdietservice.app.repository.UserDietRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 *
 */

@Service
public class UserDietServiceImpl implements UserDietService {
    
    @Autowired
	private UserDietRepository userDietRepository;
    
    @Autowired
	private UserDietRequestRepository userDietRequestRepository;
    
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