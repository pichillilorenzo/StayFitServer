package com.stayfit.userdietservice.app.endpoint;

import javax.xml.bind.JAXBElement;

import com.stayfit.userdietservice.GetAllUserDietRequestNotCompletedRequest;
import com.stayfit.userdietservice.GetAllUserDietRequestNotCompletedResponse;
import com.stayfit.userdietservice.GetUserDietByUserIdRequest;
import com.stayfit.userdietservice.GetUserDietByUserIdResponse;
import com.stayfit.userdietservice.GetUserDietRequestNotCompletedByUserIdRequest;
import com.stayfit.userdietservice.GetUserDietRequestNotCompletedByUserIdResponse;
import com.stayfit.userdietservice.SaveUserDietRequest;
import com.stayfit.userdietservice.SaveUserDietRequestRequest;
import com.stayfit.userdietservice.SaveUserDietRequestResponse;
import com.stayfit.userdietservice.SaveUserDietResponse;

public interface UserDietEndpoint {

	/**
	 * This method maps the WSDL operation "getAllUserDietRequestNotCompleted" 
	 * with a GetAllUserDietRequestNotCompletedRequest input message
	 * and a GetAllUserDietRequestNotCompletedResponse output message.
	 * 
	 * It returns all of the user's diet requests that are not completed yet.
	 */
	JAXBElement<GetAllUserDietRequestNotCompletedResponse> getAllUserDietRequestNotCompleted(
			JAXBElement<GetAllUserDietRequestNotCompletedRequest> request);

	/**
	 * This method maps the WSDL operation "getUserDietRequestNotCompletedByUserId" 
	 * with a GetUserDietRequestNotCompletedByUserIdRequest input message
	 * and a GetUserDietRequestNotCompletedByUserIdResponse output message.
	 * 
	 * It returns the user's diet request that is not completed yet.
	 */
	JAXBElement<GetUserDietRequestNotCompletedByUserIdResponse> getUserDietRequestNotCompletedByUserId(
			JAXBElement<GetUserDietRequestNotCompletedByUserIdRequest> request);

	/**
	 * This method maps the WSDL operation "saveUserDietRequest" with a SaveUserDietRequestRequest input message
	 * and a SaveUserDietRequestResponse output message.
	 * 
	 * It saves/updates the user's diet request into the system.
	 */
	JAXBElement<SaveUserDietRequestResponse> saveUserDietRequest(JAXBElement<SaveUserDietRequestRequest> request);

	/**
	 * This method maps the WSDL operation "getUserDietByUserId" 
	 * with a GetUserDietByUserIdRequest input message
	 * and a GetUserDietByUserIdResponse output message.
	 * 
	 * It returns the user's diet by his user id.
	 */
	JAXBElement<GetUserDietByUserIdResponse> getUserDietByUserId(JAXBElement<GetUserDietByUserIdRequest> request);

	/**
	 * This method maps the WSDL operation "saveUserDiet" with a SaveUserDietRequest input message
	 * and a SaveUserDietResponse output message.
	 * 
	 * It saves/updates the user's diet into the system.
	 */
	JAXBElement<SaveUserDietResponse> saveUserDiet(JAXBElement<SaveUserDietRequest> request);

}