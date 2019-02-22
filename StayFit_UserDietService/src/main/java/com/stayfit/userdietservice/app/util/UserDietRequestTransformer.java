/**
 * 
 */
package com.stayfit.userdietservice.app.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.stayfit.userdietservice.app.model.UserDietRequest;

/**
 * 
 * 
 * This utility class is used to covert an user's diet request model to a WSDL request and vice-versa.
 */
@Component
public class UserDietRequestTransformer {
	
	public com.stayfit.userdietservice.UserDietRequest convert(UserDietRequest userDietRequest) {
		com.stayfit.userdietservice.UserDietRequest userDietRequestWsdl = new com.stayfit.userdietservice.UserDietRequest();
        BeanUtils.copyProperties(userDietRequest, userDietRequestWsdl);
        
        com.stayfit.userdietservice.JobKind jobKind = com.stayfit.userdietservice.JobKind.fromValue(userDietRequest.getJobKind().name());
        userDietRequestWsdl.setJobKind(jobKind);
        return userDietRequestWsdl;
    }
	
	public UserDietRequest convertWsdl(UserDietRequest userDietRequest, com.stayfit.userdietservice.UserDietRequest userDietRequestWsdl) {
        BeanUtils.copyProperties(userDietRequestWsdl, userDietRequest);
        
        JobKind jobKind = JobKind.fromValue(userDietRequestWsdl.getJobKind().name());
        userDietRequest.setJobKind(jobKind);
        
        return userDietRequest;
    }
	
}
