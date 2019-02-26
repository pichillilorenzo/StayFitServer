package com.stayfit.app.service;

import com.stayfit.userdietservice.UserDietServicePortType;
import com.stayfit.userhistoryservice.UserHistoryServicePortType;
import com.stayfit.userservice.UserServicePortType;

public interface LoadBalancerService {
	
	/**
	 * Gets the User SOAP Web Service Port.
	 */
	UserServicePortType getUserService() throws Exception;
	
	/**
	 * Gets the User History SOAP Web Service Port.
	 */
	UserHistoryServicePortType getUserHistoryService() throws Exception;
	
	/**
	 * Gets the User Diet SOAP Web Service Port.
	 */
	UserDietServicePortType getUserDietService() throws Exception;

}