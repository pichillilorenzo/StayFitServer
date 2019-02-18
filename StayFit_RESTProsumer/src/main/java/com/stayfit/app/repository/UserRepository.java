/**
 * 
 */
package com.stayfit.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stayfit.app.model.User;

/**
 * @author lorenzo
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
