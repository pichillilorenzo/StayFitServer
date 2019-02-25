package${package}.app.service;

import${package}.userdietservice.UserDietServicePortType;
import${package}.userhistoryservice.UserHistoryServicePortType;
import${package}.userservice.UserServicePortType;

public interface LoadBalancerService {

	UserServicePortType getUserService() throws Exception;

	UserHistoryServicePortType getUserHistoryService() throws Exception;

	UserDietServicePortType getUserDietService() throws Exception;

}