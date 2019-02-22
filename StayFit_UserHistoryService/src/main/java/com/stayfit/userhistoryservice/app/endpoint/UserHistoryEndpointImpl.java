/**
 * 
 */
package com.stayfit.userhistoryservice.app.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

import javax.xml.bind.JAXBElement;

import com.stayfit.userhistoryservice.GetUserHistoryByDateRequest;
import com.stayfit.userhistoryservice.GetUserHistoryByDateResponse;
import com.stayfit.userhistoryservice.GetUserHistoryByUserIdRequest;
import com.stayfit.userhistoryservice.GetUserHistoryByUserIdResponse;
import com.stayfit.userhistoryservice.ObjectFactory;
import com.stayfit.userhistoryservice.SaveUserHistoryRequest;
import com.stayfit.userhistoryservice.SaveUserHistoryResponse;
import com.stayfit.userhistoryservice.UserHistory;
import com.stayfit.userhistoryservice.app.exception.ResourceNotFoundException;
import com.stayfit.userhistoryservice.app.service.UserHistoryService;
import com.stayfit.userhistoryservice.app.util.UserHistoryTransformer;

/**
 * 
 * 
 * This Endpoint provide access to the User History Service to manage user's history of the system.
 * An endpoint interprets the XML request message and uses that input to invoke a method on the business service. 
 * The result of that service invocation is represented as a response message.
 */
@Endpoint
public class UserHistoryEndpointImpl implements UserHistoryEndpoint {
	private static final String NAMESPACE_URI = "http://stayfit.com/userhistoryservice";

	@Autowired
	private UserHistoryService userService;

	@Autowired
	private UserHistoryTransformer userHistoryTransformer;
	
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

		List<com.stayfit.userhistoryservice.app.model.UserHistory> userHistories = userService
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

		com.stayfit.userhistoryservice.app.model.UserHistory userHistory;

		UserHistory userHistoryWsdl = request.getValue().getUserHistory();

		try {
			// get the user's history of today if it is already present into the system
			userHistory = userService.getUserHistoryByDate(userHistoryWsdl.getUserId(),
					userHistoryWsdl.getDate().toGregorianCalendar().getTime());
			userHistoryTransformer.convertWsdl(userHistory, userHistoryWsdl);
		} catch (ResourceNotFoundException ex) {
			// otherwise create a new user's history of today
			userHistory = new com.stayfit.userhistoryservice.app.model.UserHistory();
			userHistoryTransformer.convertWsdl(userHistory, userHistoryWsdl);
		}
		
		// update/save it into the system
		userService.saveUserHistory(userHistory);

		response.setUserHistory(userHistoryWsdl);

		return factory.createSaveUserHistoryResponse(response);
	}
	
}
