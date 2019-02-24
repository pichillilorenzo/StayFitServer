package com.stayfit.app.service;

import com.stayfit.userdietservice.UserDietServicePortType;
import com.stayfit.userhistoryservice.UserHistoryServicePortType;
import com.stayfit.userservice.UserServicePortType;

public interface LoadBalancerService {

	UserServicePortType getUserService() throws Exception;

	UserHistoryServicePortType getUserHistoryService() throws Exception;

	UserDietServicePortType getUserDietService() throws Exception;

}