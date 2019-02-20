package com.stayfit.userservice.app.endpoint;

import javax.xml.bind.JAXBElement;

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

public interface UserEndpoint {

	JAXBElement<GetUserByIdResponse> getUserById(JAXBElement<GetUserByIdRequest> request);

	JAXBElement<RegistrationResponse> register(JAXBElement<RegistrationRequest> request);

	JAXBElement<UpdateUserResponse> updateUser(JAXBElement<UpdateUserRequest> request);

	JAXBElement<GetUserHistoryByUserIdResponse> getUserHistoryByUserId(
			JAXBElement<GetUserHistoryByUserIdRequest> request);

	JAXBElement<GetUserHistoryByDateResponse> getUserHistoryByDate(JAXBElement<GetUserHistoryByDateRequest> request);

	JAXBElement<SaveUserHistoryResponse> saveUserHistory(JAXBElement<SaveUserHistoryRequest> request);

	JAXBElement<GetAllUserDietRequestNotCompletedResponse> getAllUserDietRequestNotCompleted(
			JAXBElement<GetAllUserDietRequestNotCompletedRequest> request);

	JAXBElement<GetUserDietRequestNotCompletedByUserIdResponse> getUserDietRequestNotCompletedByUserId(
			JAXBElement<GetUserDietRequestNotCompletedByUserIdRequest> request);

	JAXBElement<SaveUserDietRequestResponse> saveUserDietRequest(JAXBElement<SaveUserDietRequestRequest> request);

	JAXBElement<GetUserDietByUserIdResponse> getUserDietByUserId(JAXBElement<GetUserDietByUserIdRequest> request);

	JAXBElement<SaveUserDietResponse> saveUserDiet(JAXBElement<SaveUserDietRequest> request);

}