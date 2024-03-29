/**
 * 
 */
package com.stayfit.userhistoryservice.app.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.stayfit.userhistoryservice.app.model.UserHistory;
/**
 * 
 *
 */
public interface UserHistoryRepository extends MongoRepository<UserHistory, Long> {
	
	Optional<List<UserHistory>> findByUserId(long userId);
	
	@Query("{ $and: [ { 'user_id' : ?0 }, { 'date' : { '$gte' : ?1, '$lte' : ?2 } } ] }")
	Optional<UserHistory> findByDateBetween(long userId, Date from, Date to);
	
}
