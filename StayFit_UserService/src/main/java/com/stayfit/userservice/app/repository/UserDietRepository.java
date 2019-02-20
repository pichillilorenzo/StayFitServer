/**
 * 
 */
package com.stayfit.userservice.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stayfit.userservice.app.model.UserDiet;

/**
 * @author lorenzo
 *
 */
public interface UserDietRepository extends MongoRepository<UserDiet, Long> {
	
	Optional<UserDiet> findByUserId(long userId);
	
}
