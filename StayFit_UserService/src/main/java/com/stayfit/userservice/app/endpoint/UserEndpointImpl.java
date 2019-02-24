/**
 * 
 */
package com.stayfit.userservice.app.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import com.stayfit.userservice.app.service.RoleService;
import com.stayfit.userservice.app.service.UserService;
import com.stayfit.userservice.app.util.UserTransformer;
import com.stayfit.userservice.app.model.Role;
import com.stayfit.userservice.app.configuration.Encoders;
import com.stayfit.userservice.GetUserByIdRequest;
import com.stayfit.userservice.GetUserByIdResponse;
import com.stayfit.userservice.GetUserByUsernameRequest;
import com.stayfit.userservice.GetUserByUsernameResponse;
import com.stayfit.userservice.ObjectFactory;
import com.stayfit.userservice.RegistrationRequest;
import com.stayfit.userservice.RegistrationResponse;
import com.stayfit.userservice.UpdateUserRequest;
import com.stayfit.userservice.UpdateUserResponse;
import com.stayfit.userservice.User;

/**
 * 
 * This Endpoint provide access to the User Service to manage users of the system.
 * An endpoint interprets the XML request message and uses that input to invoke a method on the business service. 
 * The result of that service invocation is represented as a response message.
 */
@Endpoint
@Transactional
@Import(Encoders.class)
public class UserEndpointImpl implements UserEndpoint {
	private static final String NAMESPACE_URI = "http://stayfit.com/userservice";

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordEncoder userPasswordEncoder;

	@Autowired
	private UserTransformer userTransformer;
	
	/**
	 * This method maps the WSDL operation "getUserById" with a GetUserByIdRequest input message
	 * and a GetUserByIdResponse output message.
	 * 
	 * It returns the user by his id.
	 */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserByIdRequest")
	@ResponsePayload
	public JAXBElement<GetUserByIdResponse> getUserById(@RequestPayload JAXBElement<GetUserByIdRequest> request) {
		
		ObjectFactory factory = new ObjectFactory();
		GetUserByIdResponse response = factory.createGetUserByIdResponse();
		
		User userWsdl = userTransformer.convert(userService.getUserById(request.getValue().getId()));
		
		response.setUser(userWsdl);

		return factory.createGetUserByIdResponse(response);
	}
	
	/**
	 * This method maps the WSDL operation "getUserByUsername" with a GetUserByUsernameRequest input message
	 * and a GetUserByUsernameResponse output message.
	 * 
	 * It returns the user by his username.
	 */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserByUsernameRequest")
	@ResponsePayload
	public JAXBElement<GetUserByUsernameResponse> getUserByUsername(@RequestPayload JAXBElement<GetUserByUsernameRequest> request) {
		
		ObjectFactory factory = new ObjectFactory();
		GetUserByUsernameResponse response = factory.createGetUserByUsernameResponse();
		
		User userWsdl = userTransformer.convert(userService.getUserByUsername(request.getValue().getUsername()));
		
		response.setUser(userWsdl);

		return factory.createGetUserByUsernameResponse(response);
	}
	
	/**
	 * This method maps the WSDL operation "register" with a RegistrationRequest input message
	 * and a RegistrationResponse output message.
	 * 
	 * It is used to register the user into the system.
	 */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegistrationRequest")
	@ResponsePayload
	public JAXBElement<RegistrationResponse> register(@RequestPayload JAXBElement<RegistrationRequest> request) {
		
		ObjectFactory factory = new ObjectFactory();
		RegistrationResponse response = factory.createRegistrationResponse();
		RegistrationRequest registrationRequest = request.getValue();

		com.stayfit.userservice.app.model.User userModel = new com.stayfit.userservice.app.model.User();
		userModel.setUsername(registrationRequest.getUsername().trim());
		userModel.setPassword(userPasswordEncoder.encode(registrationRequest.getPassword().trim()));
		userModel.setEmail(registrationRequest.getEmail().trim());
		userModel.setEnabled(true);
		userModel.setGender(registrationRequest.isGender());
		userModel.setHeight(registrationRequest.getHeight());
		userModel.setWeight(registrationRequest.getWeight());
		userModel.setBirthDate(registrationRequest.getBirthDate().toGregorianCalendar().getTime());
		Role userRole = roleService
				.getRoleByName((registrationRequest.isNutritionist()) ? "ROLE_NUTRITIONIST" : "ROLE_USER");
		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		userModel.setRoles(roles);

		User userWsdl = userTransformer.convert(userService.saveUser(userModel));
		response.setUser(userWsdl);
		return factory.createRegistrationResponse(response);
	}
	
	/**
	 * This method maps the WSDL operation "updateUser" with a UpdateUserRequest input message
	 * and a UpdateUserResponse output message.
	 * 
	 * It updates the user with his new fields.
	 */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateUserRequest")
	@ResponsePayload
	public JAXBElement<UpdateUserResponse> updateUser(@RequestPayload JAXBElement<UpdateUserRequest> request) {
		
		ObjectFactory factory = new ObjectFactory();
		UpdateUserResponse response = factory.createUpdateUserResponse();
		UpdateUserRequest updateUserRequest = request.getValue();

		com.stayfit.userservice.app.model.User userModel = userService.getUserById(updateUserRequest.getId());

		userModel.setUsername(updateUserRequest.getUsername().trim());
		// if the password is empty, do not update it
		if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().trim().isEmpty())
			userModel.setPassword(userPasswordEncoder.encode(updateUserRequest.getPassword().trim()));
		userModel.setEmail(updateUserRequest.getEmail().trim());
		userModel.setGender(updateUserRequest.isGender());
		userModel.setHeight(updateUserRequest.getHeight());
		userModel.setWeight(updateUserRequest.getWeight());
		userModel.setBirthDate(updateUserRequest.getBirthDate().toGregorianCalendar().getTime());

		User userWsdl = userTransformer.convert(userService.saveUser(userModel));
		response.setUser(userWsdl);

		return factory.createUpdateUserResponse(response);
	}
	
}
