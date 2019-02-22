/**
 * 
 */
package com.stayfit.userservice.app.service;

import com.stayfit.userservice.app.exception.ResourceNotFoundException;
import com.stayfit.userservice.app.model.User;
import com.stayfit.userservice.app.repository.UserRepository;

import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 *
 */

@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserRepository userRepository;
    
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
    
}