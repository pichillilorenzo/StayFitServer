/**
 * 
 */
package com.stayfit.userservice.app.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;

import com.stayfit.userservice.app.service.UserService;
import com.stayfit.userservice.app.util.UserTransformer;

import com.stayfit.userservice.GetUserByIdRequest;
import com.stayfit.userservice.GetUserByIdResponse;
import com.stayfit.userservice.ObjectFactory;
import com.stayfit.userservice.User;

/**
 * @author lorenzo
 *
 */
@Endpoint
public class UserEndpoint {
	private static final String NAMESPACE_URI = "http://stayfit.com/userservice";
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private UserTransformer userTransformer;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserByIdRequest")
	@ResponsePayload
	public JAXBElement<GetUserByIdResponse> getUserById(@RequestPayload JAXBElement<GetUserByIdRequest> request) {
		ObjectFactory factory = new ObjectFactory();
		GetUserByIdResponse response = factory.createGetUserByIdResponse();
		User userWsdl = userTransformer.convert(userService.loadUserById(request.getValue().getId()));
		response.setUser(userWsdl);
		return factory.createGetUserByIdResponse(response);
	}
}
