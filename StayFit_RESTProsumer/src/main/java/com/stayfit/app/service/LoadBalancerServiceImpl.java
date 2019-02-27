package com.stayfit.app.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.xml.ws.BindingProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stayfit.userdietservice.UserDietServicePortType;
import com.stayfit.userhistoryservice.UserHistoryServicePortType;
import com.stayfit.userservice.UserServicePortType;

import lombok.Getter;

/**
 * 
 * This class communicates with a load balancer to decide where to redirect the current user request.
 */
@Service
@Getter
public class LoadBalancerServiceImpl implements LoadBalancerService {

	@Value("${loadbalancer.url}")
	private String loadBalancerUrl;
	
	private UserServicePortType userServicePort;
	private UserHistoryServicePortType userHistoryServicePort;
	private UserDietServicePortType userDietServicePort;
	
	/**
	 * Init all the SOAP Web Service Ports.
	 */
	@Override
	@PostConstruct
	public void init() throws MalformedURLException{
		com.stayfit.userservice.UserService userService = new com.stayfit.userservice.UserService();
		userServicePort = userService.getUserPort();
		
		com.stayfit.userhistoryservice.UserHistoryService userHistoryService = new com.stayfit.userhistoryservice.UserHistoryService();
		userHistoryServicePort = userHistoryService.getUserHistoryPort();
		
		com.stayfit.userdietservice.UserDietService userDietService = new com.stayfit.userdietservice.UserDietService();
		userDietServicePort = userDietService.getUserDietPort();
		
		Object[] servicePorts = {userServicePort, userHistoryServicePort, userDietServicePort};
		
		for(int i = 0; i < servicePorts.length; i++) {
			BindingProvider bp = (BindingProvider) servicePorts[i];
			String currentEndpoint = bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY).toString();
	
			String newEndpoint = loadBalancerUrl + (new URL(currentEndpoint)).getPath();
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newEndpoint);
		}
	}
	
}
