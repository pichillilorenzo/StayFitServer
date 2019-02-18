/**
 * 
 */
package com.stayfit.userservice.app.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import com.stayfit.userservice.app.service.RoleService;
import com.stayfit.userservice.app.service.UserService;
import com.stayfit.userservice.app.util.UserHistoryTransformer;
import com.stayfit.userservice.app.util.UserTransformer;
import com.stayfit.userservice.app.model.Role;
import com.stayfit.userservice.app.configuration.Encoders;
import com.stayfit.userservice.app.exception.ResourceNotFoundException;
import com.stayfit.userservice.GetUserByIdRequest;
import com.stayfit.userservice.GetUserByIdResponse;
import com.stayfit.userservice.GetUserHistoryByDateRequest;
import com.stayfit.userservice.GetUserHistoryByDateResponse;
import com.stayfit.userservice.GetUserHistoryByUserIdRequest;
import com.stayfit.userservice.GetUserHistoryByUserIdResponse;
import com.stayfit.userservice.ObjectFactory;
import com.stayfit.userservice.RegistrationRequest;
import com.stayfit.userservice.RegistrationResponse;
import com.stayfit.userservice.SaveUserHistoryRequest;
import com.stayfit.userservice.SaveUserHistoryResponse;
import com.stayfit.userservice.UpdateUserRequest;
import com.stayfit.userservice.UpdateUserResponse;
import com.stayfit.userservice.User;
import com.stayfit.userservice.UserHistory;

/**
 * @author lorenzo
 *
 */
@Endpoint
@Import(Encoders.class)
public class UserEndpoint {
	private static final String NAMESPACE_URI = "http://stayfit.com/userservice";

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordEncoder userPasswordEncoder;

	@Autowired
	private UserTransformer userTransformer;

	@Autowired
	private UserHistoryTransformer userHistoryTransformer;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserByIdRequest")
	@ResponsePayload
	public JAXBElement<GetUserByIdResponse> getUserById(@RequestPayload JAXBElement<GetUserByIdRequest> request) {
		ObjectFactory factory = new ObjectFactory();
		GetUserByIdResponse response = factory.createGetUserByIdResponse();

		User userWsdl = userTransformer.convert(userService.getUserById(request.getValue().getId()));
		response.setUser(userWsdl);

		return factory.createGetUserByIdResponse(response);
	}

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
		Role userRole = roleService.getRoleByName( (registrationRequest.isNutritionist()) ? "ROLE_NUTRITIONIST" : "ROLE_USER" ); 
		Set<Role> roles = new HashSet<>();
		roles.add(userRole);
		userModel.setRoles(roles);

		User userWsdl = userTransformer.convert(userService.saveUser(userModel));
		response.setUser(userWsdl);
		return factory.createRegistrationResponse(response);
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateUserRequest")
	@ResponsePayload
	public JAXBElement<UpdateUserResponse> updateUser(@RequestPayload JAXBElement<UpdateUserRequest> request) {
		ObjectFactory factory = new ObjectFactory();
		UpdateUserResponse response = factory.createUpdateUserResponse();
		UpdateUserRequest updateUserRequest = request.getValue();
		
		com.stayfit.userservice.app.model.User userModel = userService.getUserById(updateUserRequest.getId());
		
		userModel.setUsername(updateUserRequest.getUsername().trim());
		if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().trim().isEmpty())
			userModel.setPassword(userPasswordEncoder.encode(updateUserRequest.getPassword().trim()));
		userModel.setEmail(updateUserRequest.getEmail().trim());
		userModel.setGender(updateUserRequest.isGender());
		userModel.setHeight(updateUserRequest.getHeight());
		userModel.setWeight(updateUserRequest.getWeight());
		
		User userWsdl = userTransformer.convert(userService.saveUser(userModel));
		response.setUser(userWsdl);

		return factory.createUpdateUserResponse(response);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserHistoryByUserIdRequest")
	@ResponsePayload
	public JAXBElement<GetUserHistoryByUserIdResponse> getUserHistoryByUserId(
			@RequestPayload JAXBElement<GetUserHistoryByUserIdRequest> request) {
		ObjectFactory factory = new ObjectFactory();
		GetUserHistoryByUserIdResponse response = factory.createGetUserHistoryByUserIdResponse();

		List<com.stayfit.userservice.app.model.UserHistory> userHistories = userService
				.getUserHistoryByUserId(request.getValue().getUserId());

		userHistories.forEach(userHistory -> {
			response.getUserHistories().add(userHistoryTransformer.convert(userHistory));
		});

		return factory.createGetUserHistoryByUserIdResponse(response);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserHistoryByDateRequest")
	@ResponsePayload
	public JAXBElement<GetUserHistoryByDateResponse> getUserHistoryByDate(
			@RequestPayload JAXBElement<GetUserHistoryByDateRequest> request) {
		ObjectFactory factory = new ObjectFactory();
		GetUserHistoryByDateResponse response = factory.createGetUserHistoryByDateResponse();

		UserHistory userHistoryWsdl = userHistoryTransformer.convert(userService.getUserHistoryByDate(
				request.getValue().getUserId(), request.getValue().getDate().toGregorianCalendar().getTime()));
		response.setUserHistory(userHistoryWsdl);

		return factory.createGetUserHistoryByDateResponse(response);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveUserHistoryRequest")
	@ResponsePayload
	public JAXBElement<SaveUserHistoryResponse> saveUserHistory(
			@RequestPayload JAXBElement<SaveUserHistoryRequest> request) {
		ObjectFactory factory = new ObjectFactory();
		SaveUserHistoryResponse response = factory.createSaveUserHistoryResponse();

		com.stayfit.userservice.app.model.UserHistory userHistory;

		UserHistory userHistoryWsdl = request.getValue().getUserHistory();

		try {
			userHistory = userService.getUserHistoryByDate(userHistoryWsdl.getUserId(),
					userHistoryWsdl.getDate().toGregorianCalendar().getTime());
			userHistoryTransformer.convertWsdl(userHistory, userHistoryWsdl);
		} catch (ResourceNotFoundException ex) {
			userHistory = new com.stayfit.userservice.app.model.UserHistory();
			userHistoryTransformer.convertWsdl(userHistory, userHistoryWsdl);
		}
		
		userService.saveHistory(userHistory);

		response.setUserHistory(userHistoryWsdl);

		return factory.createSaveUserHistoryResponse(response);
	}
}
