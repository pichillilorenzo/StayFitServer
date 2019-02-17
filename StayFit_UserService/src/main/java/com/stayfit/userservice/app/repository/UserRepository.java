/**
 * 
 */
package com.stayfit.userservice.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stayfit.userservice.app.model.User;

/**
 * @author lorenzo
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
