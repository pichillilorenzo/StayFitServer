package com.stayfit.userservice.app.endpoint;

import javax.xml.bind.JAXBElement;

import com.stayfit.userservice.GetUserByIdRequest;
import com.stayfit.userservice.GetUserByIdResponse;
import com.stayfit.userservice.RegistrationRequest;
import com.stayfit.userservice.RegistrationResponse;
import com.stayfit.userservice.UpdateUserRequest;
import com.stayfit.userservice.UpdateUserResponse;

public interface UserEndpoint {

	/**
	 * This method maps the WSDL operation "getUserById" with a GetUserByIdRequest input message
	 * and a GetUserByIdResponse output message.
	 * 
	 * It returns the user by his id.
	 */
	JAXBElement<GetUserByIdResponse> getUserById(JAXBElement<GetUserByIdRequest> request);

	/**
	 * This method maps the WSDL operation "register" with a RegistrationRequest input message
	 * and a RegistrationResponse output message.
	 * 
	 * It is used to register the user into the system.
	 */
	JAXBElement<RegistrationResponse> register(JAXBElement<RegistrationRequest> request);

	/**
	 * This method maps the WSDL operation "updateUser" with a UpdateUserRequest input message
	 * and a UpdateUserResponse output message.
	 * 
	 * It updates the user with his new fields.
	 */
	JAXBElement<UpdateUserResponse> updateUser(JAXBElement<UpdateUserRequest> request);

}