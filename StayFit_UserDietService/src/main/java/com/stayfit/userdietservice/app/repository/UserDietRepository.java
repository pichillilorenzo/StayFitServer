/**
 * 
 */
package com.stayfit.userdietservice.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stayfit.userdietservice.app.model.UserDiet;

/**
 * 
 *
 */
public interface UserDietRepository extends MongoRepository<UserDiet, Long> {
	
	Optional<UserDiet> findByUserId(long userId);
	
}
