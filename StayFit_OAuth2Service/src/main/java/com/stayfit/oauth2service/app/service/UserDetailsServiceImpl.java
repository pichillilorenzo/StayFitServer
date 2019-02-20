/**
 * 
 */
package com.stayfit.oauth2service.app.service;

import com.stayfit.oauth2service.app.exception.ResourceNotFoundException;
import com.stayfit.oauth2service.app.model.User;
import com.stayfit.oauth2service.app.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lorenzo
 * 
 * This @Service is used by the OAuth2Config to get the user's information.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Find the user by username.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, ResourceNotFoundException {
        User user = userRepository.findByUsername(username);
        
        if (user != null) {
	        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
	    }
        
        throw new ResourceNotFoundException("User", "username", username);
    }
    
}