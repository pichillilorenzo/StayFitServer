/**
 * 
 */
package com.stayfit.userservice.app.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.stayfit.userservice.app.model.UserDietRequest;

/**
 * @author lorenzo
 *
 */
@Component
public class UserDietRequestTransformer {
	
	public com.stayfit.userservice.UserDietRequest convert(UserDietRequest userDietRequest) {
		com.stayfit.userservice.UserDietRequest userDietRequestWsdl = new com.stayfit.userservice.UserDietRequest();
        BeanUtils.copyProperties(userDietRequest, userDietRequestWsdl);
        
        com.stayfit.userservice.JobKind jobKind = com.stayfit.userservice.JobKind.fromValue(userDietRequest.getJobKind().name());
        userDietRequestWsdl.setJobKind(jobKind);
        return userDietRequestWsdl;
    }
	
	public UserDietRequest convertWsdl(UserDietRequest userDietRequest, com.stayfit.userservice.UserDietRequest userDietRequestWsdl) {
        BeanUtils.copyProperties(userDietRequestWsdl, userDietRequest);
        
        JobKind jobKind = JobKind.fromValue(userDietRequestWsdl.getJobKind().name());
        userDietRequest.setJobKind(jobKind);
        
        return userDietRequest;
    }
	
}
