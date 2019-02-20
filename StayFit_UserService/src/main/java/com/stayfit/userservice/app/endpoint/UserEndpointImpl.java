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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import com.stayfit.userservice.app.service.RoleService;
import com.stayfit.userservice.app.service.UserService;
import com.stayfit.userservice.app.util.UserDietRequestTransformer;
import com.stayfit.userservice.app.util.UserDietTransformer;
import com.stayfit.userservice.app.util.UserHistoryTransformer;
import com.stayfit.userservice.app.util.UserTransformer;
import com.stayfit.userservice.app.model.Role;
import com.stayfit.userservice.app.configuration.Encoders;
import com.stayfit.userservice.app.exception.ResourceNotFoundException;
import com.stayfit.userservice.GetAllUserDietRequestNotCompletedRequest;
import com.stayfit.userservice.GetAllUserDietRequestNotCompletedResponse;
import com.stayfit.userservice.GetUserByIdRequest;
import com.stayfit.userservice.GetUserByIdResponse;
import com.stayfit.userservice.GetUserDietByUserIdRequest;
import com.stayfit.userservice.GetUserDietByUserIdResponse;
import com.stayfit.userservice.GetUserDietRequestNotCompletedByUserIdRequest;
import com.stayfit.userservice.GetUserDietRequestNotCompletedByUserIdResponse;
import com.stayfit.userservice.GetUserHistoryByDateRequest;
import com.stayfit.userservice.GetUserHistoryByDateResponse;
import com.stayfit.userservice.GetUserHistoryByUserIdRequest;
import com.stayfit.userservice.GetUserHistoryByUserIdResponse;
import com.stayfit.userservice.ObjectFactory;
import com.stayfit.userservice.RegistrationRequest;
import com.stayfit.userservice.RegistrationResponse;
import com.stayfit.userservice.SaveUserDietRequest;
import com.stayfit.userservice.SaveUserDietRequestRequest;
import com.stayfit.userservice.SaveUserDietRequestResponse;
import com.stayfit.userservice.SaveUserDietResponse;
import com.stayfit.userservice.SaveUserHistoryRequest;
import com.stayfit.userservice.SaveUserHistoryResponse;
import com.stayfit.userservice.UpdateUserRequest;
import com.stayfit.userservice.UpdateUserResponse;
import com.stayfit.userservice.User;
import com.stayfit.userservice.UserDiet;
import com.stayfit.userservice.UserDietRequest;
import com.stayfit.userservice.UserHistory;

/**
 * @author lorenzo
 *
 */
@Endpoint
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

	@Autowired
	private UserHistoryTransformer userHistoryTransformer;

	@Autowired
	private UserDietTransformer userDietTransformer;

	@Autowired
	private UserDietRequestTransformer userDietRequestTransformer;
	
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
	
	/**
	 * This method maps the WSDL operation "getUserHistoryByUserId" with a GetUserHistoryByUserIdRequest input message
	 * and a GetUserHistoryByUserIdResponse output message.
	 * 
	 * It returns all the user's histories by his user id.
	 */
	@Override
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
	
	/**
	 * This method maps the WSDL operation "getUserHistoryByDate" with a GetUserHistoryByDateRequest input message
	 * and a GetUserHistoryByDateResponse output message.
	 * 
	 * It returns the user's history by history date.
	 */
	@Override
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
	
	/**
	 * This method maps the WSDL operation "saveUserHistory" with a SaveUserHistoryRequest input message
	 * and a SaveUserHistoryResponse output message.
	 * 
	 * It saves/updates the user's history into the system.
	 */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveUserHistoryRequest")
	@ResponsePayload
	public JAXBElement<SaveUserHistoryResponse> saveUserHistory(
			@RequestPayload JAXBElement<SaveUserHistoryRequest> request) {
		
		ObjectFactory factory = new ObjectFactory();
		SaveUserHistoryResponse response = factory.createSaveUserHistoryResponse();

		com.stayfit.userservice.app.model.UserHistory userHistory;

		UserHistory userHistoryWsdl = request.getValue().getUserHistory();

		try {
			// get the user's history of today if it is already present into the system
			userHistory = userService.getUserHistoryByDate(userHistoryWsdl.getUserId(),
					userHistoryWsdl.getDate().toGregorianCalendar().getTime());
			userHistoryTransformer.convertWsdl(userHistory, userHistoryWsdl);
		} catch (ResourceNotFoundException ex) {
			// otherwise create a new user's history of today
			userHistory = new com.stayfit.userservice.app.model.UserHistory();
			userHistoryTransformer.convertWsdl(userHistory, userHistoryWsdl);
		}
		
		// update/save it into the system
		userService.saveUserHistory(userHistory);

		response.setUserHistory(userHistoryWsdl);

		return factory.createSaveUserHistoryResponse(response);
	}
	
	/**
	 * This method maps the WSDL operation "getAllUserDietRequestNotCompleted" 
	 * with a GetAllUserDietRequestNotCompletedRequest input message
	 * and a GetAllUserDietRequestNotCompletedResponse output message.
	 * 
	 * It returns all of the user's diet requests that are not completed yet.
	 */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllUserDietRequestNotCompletedRequest")
	@ResponsePayload
	public JAXBElement<GetAllUserDietRequestNotCompletedResponse> getAllUserDietRequestNotCompleted(
			@RequestPayload JAXBElement<GetAllUserDietRequestNotCompletedRequest> request) {
		
		ObjectFactory factory = new ObjectFactory();
		GetAllUserDietRequestNotCompletedResponse response = factory
				.createGetAllUserDietRequestNotCompletedResponse();
		
		List<UserDietRequest> userDietRequestListWsdl = new ArrayList<UserDietRequest>();
		userService.getAllUserDietRequestNotCompleted().forEach(userDietRequest -> {
			userDietRequestListWsdl.add(userDietRequestTransformer.convert(userDietRequest));
		});
		
		response.getUserDietRequest().addAll(userDietRequestListWsdl);

		return factory.createGetAllUserDietRequestNotCompletedResponse(response);
	}
	
	/**
	 * This method maps the WSDL operation "getUserDietRequestNotCompletedByUserId" 
	 * with a GetUserDietRequestNotCompletedByUserIdRequest input message
	 * and a GetUserDietRequestNotCompletedByUserIdResponse output message.
	 * 
	 * It returns the user's diet request that is not completed yet.
	 */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserDietRequestNotCompletedByUserIdRequest")
	@ResponsePayload
	public JAXBElement<GetUserDietRequestNotCompletedByUserIdResponse> getUserDietRequestNotCompletedByUserId(
			@RequestPayload JAXBElement<GetUserDietRequestNotCompletedByUserIdRequest> request) {
		
		ObjectFactory factory = new ObjectFactory();
		GetUserDietRequestNotCompletedByUserIdResponse response = factory
				.createGetUserDietRequestNotCompletedByUserIdResponse();

		UserDietRequest userDietRequestWsdl = userDietRequestTransformer
				.convert(userService.getUserDietRequestNotCompletedByUserId(request.getValue().getUserId()));

		response.setUserDietRequest(userDietRequestWsdl);

		return factory.createGetUserDietRequestNotCompletedByUserIdResponse(response);
	}
	
	/**
	 * This method maps the WSDL operation "saveUserDietRequest" with a SaveUserDietRequestRequest input message
	 * and a SaveUserDietRequestResponse output message.
	 * 
	 * It saves/updates the user's diet request into the system.
	 */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveUserDietRequestRequest")
	@ResponsePayload
	public JAXBElement<SaveUserDietRequestResponse> saveUserDietRequest(
			@RequestPayload JAXBElement<SaveUserDietRequestRequest> request) {
		
		ObjectFactory factory = new ObjectFactory();
		SaveUserDietRequestResponse response = factory.createSaveUserDietRequestResponse();

		com.stayfit.userservice.app.model.UserDietRequest userDietRequest;

		UserDietRequest userDietRequestWsdl = request.getValue().getUserDietRequest();

		try {
			// get the user's diet request if it is already present into the system
			userDietRequest = userService.getUserDietRequestNotCompletedByUserId(userDietRequestWsdl.getUserId());
			userDietRequestTransformer.convertWsdl(userDietRequest, userDietRequestWsdl);
		} catch (ResourceNotFoundException ex) {
			// otherwise create a new user's diet request
			userDietRequest = new com.stayfit.userservice.app.model.UserDietRequest();
			userDietRequestTransformer.convertWsdl(userDietRequest, userDietRequestWsdl);
		}
		
		// update/save it into the system
		userService.saveUserDietRequest(userDietRequest);

		response.setUserDietRequest(userDietRequestWsdl);

		return factory.createSaveUserDietRequestResponse(response);
	}
	
	/**
	 * This method maps the WSDL operation "getUserDietByUserId" 
	 * with a GetUserDietByUserIdRequest input message
	 * and a GetUserDietByUserIdResponse output message.
	 * 
	 * It returns the user's diet by his user id.
	 */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserDietByUserIdRequest")
	@ResponsePayload
	public JAXBElement<GetUserDietByUserIdResponse> getUserDietByUserId(
			@RequestPayload JAXBElement<GetUserDietByUserIdRequest> request) {
		
		ObjectFactory factory = new ObjectFactory();
		GetUserDietByUserIdResponse response = factory.createGetUserDietByUserIdResponse();

		UserDiet userDietWsdl = userDietTransformer
				.convert(userService.getUserDietByUserId(request.getValue().getUserId()));
		response.setUserDiet(userDietWsdl);

		return factory.createGetUserDietByUserIdResponse(response);
	}
	
	/**
	 * This method maps the WSDL operation "saveUserDiet" with a SaveUserDietRequest input message
	 * and a SaveUserDietResponse output message.
	 * 
	 * It saves/updates the user's diet into the system.
	 */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveUserDietRequest")
	@ResponsePayload
	public JAXBElement<SaveUserDietResponse> saveUserDiet(@RequestPayload JAXBElement<SaveUserDietRequest> request) {
		
		ObjectFactory factory = new ObjectFactory();
		SaveUserDietResponse response = factory.createSaveUserDietResponse();

		com.stayfit.userservice.app.model.UserDiet userDiet;
		com.stayfit.userservice.app.model.UserDietRequest userDietRequest = null;

		UserDiet userDietWsdl = request.getValue().getUserDiet();
		
		try {
			// check if a user's diet request already exists
			userDietRequest = userService.getUserDietRequestNotCompletedByUserId(userDietWsdl.getUserId());
		} catch (ResourceNotFoundException ex) {}
		
		try {
			// get the user's diet if it is already present into the system
			userDiet = userService.getUserDietByUserId(userDietWsdl.getUserId());
			userDietTransformer.convertWsdl(userDiet, userDietWsdl);
		} catch (ResourceNotFoundException ex) {
			// otherwise create a new user's diet
			userDiet = new com.stayfit.userservice.app.model.UserDiet();
			userDietTransformer.convertWsdl(userDiet, userDietWsdl);
		}
		
		// update/save it into the system
		userService.saveUserDiet(userDiet);
		
		if (userDietRequest != null) {
			// if a user's diet request exists
			// set the user's diet request completed to true and 
			// set the nutritionist id that made his diet
			userDietRequest.setCompleted(true);
			userDietRequest.setNutritionistId(userDietWsdl.getNutritionistId());
			userService.saveUserDietRequest(userDietRequest);
		}

		response.setUserDiet(userDietWsdl);

		return factory.createSaveUserDietResponse(response);
	}
}
