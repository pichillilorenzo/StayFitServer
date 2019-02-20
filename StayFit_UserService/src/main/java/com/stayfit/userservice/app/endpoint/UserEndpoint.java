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

	@Autowired
	private UserDietTransformer userDietTransformer;

	@Autowired
	private UserDietRequestTransformer userDietRequestTransformer;

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
		userModel.setBirthDate(updateUserRequest.getBirthDate().toGregorianCalendar().getTime());

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

		userService.saveUserHistory(userHistory);

		response.setUserHistory(userHistoryWsdl);

		return factory.createSaveUserHistoryResponse(response);
	}

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

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveUserDietRequestRequest")
	@ResponsePayload
	public JAXBElement<SaveUserDietRequestResponse> saveUserDietRequest(
			@RequestPayload JAXBElement<SaveUserDietRequestRequest> request) {
		ObjectFactory factory = new ObjectFactory();
		SaveUserDietRequestResponse response = factory.createSaveUserDietRequestResponse();

		com.stayfit.userservice.app.model.UserDietRequest userDietRequest;

		UserDietRequest userDietRequestWsdl = request.getValue().getUserDietRequest();

		try {
			userDietRequest = userService.getUserDietRequestNotCompletedByUserId(userDietRequestWsdl.getUserId());
			userDietRequestTransformer.convertWsdl(userDietRequest, userDietRequestWsdl);
		} catch (ResourceNotFoundException ex) {
			userDietRequest = new com.stayfit.userservice.app.model.UserDietRequest();
			userDietRequestTransformer.convertWsdl(userDietRequest, userDietRequestWsdl);
		}

		userService.saveUserDietRequest(userDietRequest);

		response.setUserDietRequest(userDietRequestWsdl);

		return factory.createSaveUserDietRequestResponse(response);
	}

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

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveUserDietRequest")
	@ResponsePayload
	public JAXBElement<SaveUserDietResponse> saveUserDiet(@RequestPayload JAXBElement<SaveUserDietRequest> request) {
		ObjectFactory factory = new ObjectFactory();
		SaveUserDietResponse response = factory.createSaveUserDietResponse();

		com.stayfit.userservice.app.model.UserDiet userDiet;
		com.stayfit.userservice.app.model.UserDietRequest userDietRequest = null;

		UserDiet userDietWsdl = request.getValue().getUserDiet();
		
		try {
			// check if a user's diet request exists
			userDietRequest = userService.getUserDietRequestNotCompletedByUserId(userDietWsdl.getUserId());
		} catch (ResourceNotFoundException ex) {}
		
		try {
			userDiet = userService.getUserDietByUserId(userDietWsdl.getUserId());
			userDietTransformer.convertWsdl(userDiet, userDietWsdl);
		} catch (ResourceNotFoundException ex) {
			userDiet = new com.stayfit.userservice.app.model.UserDiet();
			userDietTransformer.convertWsdl(userDiet, userDietWsdl);
		}

		userService.saveUserDiet(userDiet);
		
		if (userDietRequest != null) {
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
