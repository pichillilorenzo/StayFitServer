/**
 * 
 */
package com.stayfit.oauth2service.app.service;

import com.stayfit.oauth2service.app.exception.ResourceNotFoundException;
import com.stayfit.oauth2service.app.model.User;
import com.stayfit.oauth2service.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lorenzo
 *
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null) {
	        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }
        
        throw new ResourceNotFoundException("User", "username", username);
    }
    
}