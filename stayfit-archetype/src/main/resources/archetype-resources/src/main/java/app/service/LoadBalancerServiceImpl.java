package ${package}.app.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.xml.ws.BindingProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ${package}.userdietservice.UserDietServicePortType;
import ${package}.userhistoryservice.UserHistoryServicePortType;
import ${package}.userservice.UserServicePortType;

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
			return null;
	}

}

