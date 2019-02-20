/**
 * 
 */
package com.stayfit.userservice.app.util;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

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
        
        GregorianCalendar gregDate = new GregorianCalendar();
        gregDate.setTime(user.getBirthDate());
        try {
        	userWsdl.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
        
        return userWsdl;
    }
	
}
