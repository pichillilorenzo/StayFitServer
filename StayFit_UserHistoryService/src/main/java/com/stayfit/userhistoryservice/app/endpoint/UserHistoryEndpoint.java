package com.stayfit.userhistoryservice.app.endpoint;

import javax.xml.bind.JAXBElement;

import com.stayfit.userhistoryservice.GetUserHistoryByDateRequest;
import com.stayfit.userhistoryservice.GetUserHistoryByDateResponse;
import com.stayfit.userhistoryservice.GetUserHistoryByUserIdRequest;
import com.stayfit.userhistoryservice.GetUserHistoryByUserIdResponse;
import com.stayfit.userhistoryservice.SaveUserHistoryRequest;
import com.stayfit.userhistoryservice.SaveUserHistoryResponse;

public interface UserHistoryEndpoint {

	/**
	 * This method maps the WSDL operation "getUserHistoryByUserId" with a GetUserHistoryByUserIdRequest input message
	 * and a GetUserHistoryByUserIdResponse output message.
	 * 
	 * It returns all the user's histories by his user id.
	 */
	JAXBElement<GetUserHistoryByUserIdResponse> getUserHistoryByUserId(
			JAXBElement<GetUserHistoryByUserIdRequest> request);

	/**
	 * This method maps the WSDL operation "getUserHistoryByDate" with a GetUserHistoryByDateRequest input message
	 * and a GetUserHistoryByDateResponse output message.
	 * 
	 * It returns the user's history by history date.
	 */
	JAXBElement<GetUserHistoryByDateResponse> getUserHistoryByDate(JAXBElement<GetUserHistoryByDateRequest> request);

	/**
	 * This method maps the WSDL operation "saveUserHistory" with a SaveUserHistoryRequest input message
	 * and a SaveUserHistoryResponse output message.
	 * 
	 * It saves/updates the user's history into the system.
	 */
	JAXBElement<SaveUserHistoryResponse> saveUserHistory(JAXBElement<SaveUserHistoryRequest> request);

}