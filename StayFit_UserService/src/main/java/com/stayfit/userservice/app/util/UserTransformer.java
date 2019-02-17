/**
 * 
 */
package com.stayfit.userservice.app.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.stayfit.userservice.app.model.User;

/**
 * @author lorenzo
 *
 */
@Component
public class UserTransformer {
	
	public com.stayfit.userservice.User convert(User user) {
		com.stayfit.userservice.User userWsdl = new com.stayfit.userservice.User();
        BeanUtils.copyProperties(user, userWsdl);
        return userWsdl;
    }
	
}
