/**
 * 
 */
package com.stayfit.userservice.app.service;

import com.stayfit.userservice.app.exception.ResourceNotFoundException;
import com.stayfit.userservice.app.model.Role;
import com.stayfit.userservice.app.model.User;
import com.stayfit.userservice.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lorenzo
 *
 */

@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws ResourceNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null) {
	        return user;
        }
        
        throw new ResourceNotFoundException("User", "username", username);
    }
    
    @Transactional(readOnly = true)
    public User loadUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
    
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }
}