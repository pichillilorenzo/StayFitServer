/**
 * 
 */
package com.stayfit.userdietservice.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stayfit.userdietservice.app.model.UserDietRequest;

/**
 * @author lorenzo
 *
 */
public interface UserDietRequestRepository extends JpaRepository<UserDietRequest, Long>  {
	
	@Query("select udr from UserDietRequest udr where udr.userId = :userId and udr.completed = FALSE")
	Optional<UserDietRequest> findNotCompletedByUserId(@Param("userId") Long userId);
	
	@Query("select udr from UserDietRequest udr where udr.completed = FALSE")
	Optional<List<UserDietRequest>> findAllNotCompleted();
	
}
