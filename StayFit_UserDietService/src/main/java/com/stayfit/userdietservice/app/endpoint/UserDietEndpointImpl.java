/**
 * 
 */
package com.stayfit.userdietservice.app.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.stayfit.userdietservice.GetAllUserDietRequestNotCompletedRequest;
import com.stayfit.userdietservice.GetAllUserDietRequestNotCompletedResponse;
import com.stayfit.userdietservice.GetUserDietByUserIdRequest;
import com.stayfit.userdietservice.GetUserDietByUserIdResponse;
import com.stayfit.userdietservice.GetUserDietRequestNotCompletedByUserIdRequest;
import com.stayfit.userdietservice.GetUserDietRequestNotCompletedByUserIdResponse;
import com.stayfit.userdietservice.ObjectFactory;
import com.stayfit.userdietservice.SaveUserDietRequest;
import com.stayfit.userdietservice.SaveUserDietRequestRequest;
import com.stayfit.userdietservice.SaveUserDietRequestResponse;
import com.stayfit.userdietservice.SaveUserDietResponse;
import com.stayfit.userdietservice.UserDiet;
import com.stayfit.userdietservice.UserDietRequest;
import com.stayfit.userdietservice.app.exception.ResourceNotFoundException;
import com.stayfit.userdietservice.app.service.UserDietService;
import com.stayfit.userdietservice.app.util.UserDietRequestTransformer;
import com.stayfit.userdietservice.app.util.UserDietTransformer;

/**
 * 
 * 
 * This Endpoint provide access to the User Diet Service to manage user's diets of the system.
 * An endpoint interprets the XML request message and uses that input to invoke a method on the business service. 
 * The result of that service invocation is represented as a response message.
 */
@Endpoint
public class UserDietEndpointImpl implements UserDietEndpoint {
	private static final String NAMESPACE_URI = "http://stayfit.com/userdietservice";
	
	@Autowired
	private UserDietService userDietService;
	
	@Autowired
	private UserDietTransformer userDietTransformer;

	@Autowired
	private UserDietRequestTransformer userDietRequestTransformer;
	
	
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
		userDietService.getAllUserDietRequestNotCompleted().forEach(userDietRequest -> {
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
				.convert(userDietService.getUserDietRequestNotCompletedByUserId(request.getValue().getUserId()));

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

		com.stayfit.userdietservice.app.model.UserDietRequest userDietRequest;

		UserDietRequest userDietRequestWsdl = request.getValue().getUserDietRequest();

		try {
			// get the user's diet request if it is already present into the system
			userDietRequest = userDietService.getUserDietRequestNotCompletedByUserId(userDietRequestWsdl.getUserId());
			userDietRequestTransformer.convertWsdl(userDietRequest, userDietRequestWsdl);
		} catch (ResourceNotFoundException ex) {
			// otherwise create a new user's diet request
			userDietRequest = new com.stayfit.userdietservice.app.model.UserDietRequest();
			userDietRequestTransformer.convertWsdl(userDietRequest, userDietRequestWsdl);
		}
		
		// update/save it into the system
		userDietService.saveUserDietRequest(userDietRequest);

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
				.convert(userDietService.getUserDietByUserId(request.getValue().getUserId()));
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

		com.stayfit.userdietservice.app.model.UserDiet userDiet;
		com.stayfit.userdietservice.app.model.UserDietRequest userDietRequest = null;

		UserDiet userDietWsdl = request.getValue().getUserDiet();
		
		try {
			// check if a user's diet request already exists
			userDietRequest = userDietService.getUserDietRequestNotCompletedByUserId(userDietWsdl.getUserId());
		} catch (ResourceNotFoundException ex) {}
		
		try {
			// get the user's diet if it is already present into the system
			userDiet = userDietService.getUserDietByUserId(userDietWsdl.getUserId());
			userDietTransformer.convertWsdl(userDiet, userDietWsdl);
		} catch (ResourceNotFoundException ex) {
			// otherwise create a new user's diet
			userDiet = new com.stayfit.userdietservice.app.model.UserDiet();
			userDietTransformer.convertWsdl(userDiet, userDietWsdl);
		}
		
		// update/save it into the system
		userDietService.saveUserDiet(userDiet);
		
		if (userDietRequest != null) {
			// if a user's diet request exists
			// set the user's diet request completed to true and 
			// set the nutritionist id that made his diet
			userDietRequest.setCompleted(true);
			userDietRequest.setNutritionistId(userDietWsdl.getNutritionistId());
			userDietService.saveUserDietRequest(userDietRequest);
		}

		response.setUserDiet(userDietWsdl);

		return factory.createSaveUserDietResponse(response);
	}
}
