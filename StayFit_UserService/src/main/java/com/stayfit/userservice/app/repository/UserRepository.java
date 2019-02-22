/**
 * 
 */
package com.stayfit.userservice.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stayfit.userservice.app.model.User;

/**
 * 
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
