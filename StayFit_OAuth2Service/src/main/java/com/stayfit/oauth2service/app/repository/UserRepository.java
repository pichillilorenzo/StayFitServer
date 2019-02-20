/**
 * 
 */
package com.stayfit.oauth2service.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stayfit.oauth2service.app.model.User;

/**
 * @author lorenzo
 * 
 */
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
