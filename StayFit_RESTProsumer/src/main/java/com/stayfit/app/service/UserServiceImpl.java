/**
 * 
 */
package com.stayfit.app.service;

import com.stayfit.app.configuration.ServicesConfiguration;
import com.stayfit.app.exception.ResourceNotFoundException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stayfit.userservice.UserServicePortType;
import com.stayfit.userdietservice.Breakfast;
import com.stayfit.userdietservice.Dinner;
import com.stayfit.userdietservice.Lunch;
import com.stayfit.userdietservice.Other;
import com.stayfit.userdietservice.DietDay;
import com.stayfit.userdietservice.GetAllUserDietRequestNotCompletedRequest;
import com.stayfit.userdietservice.GetAllUserDietRequestNotCompletedResponse;
import com.stayfit.userservice.GetUserByIdRequest;
import com.stayfit.userservice.GetUserByIdResponse;
import com.stayfit.userdietservice.GetUserDietByUserIdRequest;
import com.stayfit.userdietservice.GetUserDietByUserIdResponse;
import com.stayfit.userdietservice.GetUserDietRequestNotCompletedByUserIdRequest;
import com.stayfit.userdietservice.GetUserDietRequestNotCompletedByUserIdResponse;
import com.stayfit.userhistoryservice.GetUserHistoryByDateRequest;
import com.stayfit.userhistoryservice.GetUserHistoryByDateResponse;
import com.stayfit.userdietservice.JobKind;
import com.stayfit.userservice.RegistrationRequest;
import com.stayfit.userservice.RegistrationResponse;
import com.stayfit.userdietservice.SaveUserDietRequest;
import com.stayfit.userdietservice.SaveUserDietRequestRequest;
import com.stayfit.userdietservice.SaveUserDietRequestResponse;
import com.stayfit.userdietservice.SaveUserDietResponse;
import com.stayfit.userhistoryservice.SaveUserHistoryRequest;
import com.stayfit.userhistoryservice.SaveUserHistoryResponse;
import com.stayfit.userservice.UpdateUserRequest;
import com.stayfit.userservice.UpdateUserResponse;
import com.stayfit.userdietservice.UserDiet;
import com.stayfit.userdietservice.UserDietRequest;
import com.stayfit.userdietservice.UserDietServicePortType;
import com.stayfit.userhistoryservice.UserHistory;
import com.stayfit.userhistoryservice.UserHistoryServicePortType;

import javax.xml.ws.soap.SOAPFaultException;

/**
 * 
 * This Spring Service will use the SOAP User Web Service to exchange data.
 */

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

	private UserServicePortType userPort = (new AnnotationConfigApplicationContext(ServicesConfiguration.class))
			.getBean(UserServicePortType.class);
	
	private UserDietServicePortType userDietPort = (new AnnotationConfigApplicationContext(ServicesConfiguration.class))
			.getBean(UserDietServicePortType.class);
	
	private UserHistoryServicePortType userHistoryPort = (new AnnotationConfigApplicationContext(ServicesConfiguration.class))
			.getBean(UserHistoryServicePortType.class);

	/**
	 * 
	 * It returns the user by his id.
	 */
	@Override
	@PreAuthorize("hasAuthority('USER_READ')")
	public com.stayfit.userservice.User getUserById(Long id) throws ResourceNotFoundException {

		GetUserByIdRequest request = new GetUserByIdRequest();
		request.setId(id);

		try {
			GetUserByIdResponse response = userPort.getUserById(request);
			com.stayfit.userservice.User user = response.getUser();

			return user;
		} catch (SOAPFaultException ex) {
			throw new ResourceNotFoundException("User", "id", id);
		}
	}

	/**
	 * 
	 * It is used to register the user into the system.
	 */
	@Override
	public com.stayfit.userservice.User registerUser(Map<String, Object> payload) {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gregDate = new GregorianCalendar();

		try {
			gregDate.setTime(formatter.parse(payload.get("birthDate").toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		RegistrationRequest request = new RegistrationRequest();
		request.setUsername(payload.get("username").toString());
		request.setPassword(payload.get("password").toString());
		request.setEmail(payload.get("email").toString());
		request.setGender((boolean) payload.get("gender"));
		request.setNutritionist((boolean) payload.get("nutritionist"));
		request.setHeight(Integer.parseInt(payload.get("height").toString()));
		request.setWeight(Float.parseFloat(payload.get("weight").toString()));
		try {
			request.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		RegistrationResponse response = userPort.register(request);
		com.stayfit.userservice.User userResponse = response.getUser();

		return userResponse;
	}

	/**
	 * 
	 * It updates the user with his new fields.
	 */
	@Override
	@PreAuthorize("hasAuthority('USER_UPDATE')")
	public com.stayfit.userservice.User updateUser(Long id, Map<String, Object> payload)
			throws ResourceNotFoundException {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gregDate = new GregorianCalendar();

		try {
			gregDate.setTime(formatter.parse(payload.get("birthDate").toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		UpdateUserRequest request = new UpdateUserRequest();
		request.setId(id);
		request.setUsername(payload.get("username").toString());
		request.setPassword(payload.get("password").toString());
		request.setEmail(payload.get("email").toString());
		request.setGender((boolean) payload.get("gender"));
		request.setHeight(Integer.parseInt(payload.get("height").toString()));
		request.setWeight(Float.parseFloat(payload.get("weight").toString()));
		try {
			request.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		try {
			UpdateUserResponse response = userPort.updateUser(request);
			com.stayfit.userservice.User userResponse = response.getUser();

			return userResponse;
		} catch (SOAPFaultException ex) {
			throw new ResourceNotFoundException("User", "id", id);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * It returns the user's history by history date.
	 */
	@Override
	@PreAuthorize("hasAuthority('USER_HISTORY_READ')")
	public UserHistory getUserHistoryByDate(Long id, String date) throws ResourceNotFoundException {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gregDate = new GregorianCalendar();

		try {
			gregDate.setTime(formatter.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		GetUserHistoryByDateRequest request = new GetUserHistoryByDateRequest();
		request.setUserId(id);
		try {
			request.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		try {
			GetUserHistoryByDateResponse response = userHistoryPort.getUserHistoryByDate(request);
			com.stayfit.userhistoryservice.UserHistory userHistoryResponse = response.getUserHistory();
			return userHistoryResponse;
		} catch (SOAPFaultException ex) {
			throw new ResourceNotFoundException("UserHistory", "date", date);
		}

	}

	/**
	 * 
	 * It saves/updates the user's history into the system.
	 */
	@Override
	@PreAuthorize("hasAuthority('USER_HISTORY_CREATE')")
	public UserHistory saveUserHistory(Long id, String date, Map<String, Object> payload) {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gregDate = new GregorianCalendar();

		try {
			gregDate.setTime(formatter.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		UserHistory userHistory = new UserHistory();
		userHistory.setUserId(id);

		try {
			userHistory.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregDate));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		List<com.stayfit.userhistoryservice.Breakfast> breakfastList = new ArrayList<com.stayfit.userhistoryservice.Breakfast>();
		List<com.stayfit.userhistoryservice.Lunch> lunchList = new ArrayList<com.stayfit.userhistoryservice.Lunch>();
		List<com.stayfit.userhistoryservice.Dinner> dinnerList = new ArrayList<com.stayfit.userhistoryservice.Dinner>();
		List<com.stayfit.userhistoryservice.Other> otherList = new ArrayList<com.stayfit.userhistoryservice.Other>();

		((ArrayList<Map<String, Object>>) payload.get("breakfast")).forEach(breakfastMap -> {
			com.stayfit.userhistoryservice.Breakfast breakfast = new com.stayfit.userhistoryservice.Breakfast();
			breakfast.setFoodId(Long.parseLong(breakfastMap.get("foodId").toString()));
			breakfast.setAmount(Float.parseFloat(breakfastMap.get("amount").toString()));
			breakfast.setUnit(breakfastMap.get("unit").toString());
			breakfastList.add(breakfast);
		});
		userHistory.getBreakfast().clear();
		userHistory.getBreakfast().addAll(breakfastList);

		((ArrayList<Map<String, Object>>) payload.get("lunch")).forEach(lunchMap -> {
			com.stayfit.userhistoryservice.Lunch lunch = new com.stayfit.userhistoryservice.Lunch();
			lunch.setFoodId(Long.parseLong(lunchMap.get("foodId").toString()));
			lunch.setAmount(Float.parseFloat(lunchMap.get("amount").toString()));
			lunch.setUnit(lunchMap.get("unit").toString());
			lunchList.add(lunch);
		});
		userHistory.getLunch().clear();
		userHistory.getLunch().addAll(lunchList);

		((ArrayList<Map<String, Object>>) payload.get("dinner")).forEach(dinnerMap -> {
			com.stayfit.userhistoryservice.Dinner dinner = new com.stayfit.userhistoryservice.Dinner();
			dinner.setFoodId(Long.parseLong(dinnerMap.get("foodId").toString()));
			dinner.setAmount(Float.parseFloat(dinnerMap.get("amount").toString()));
			dinner.setUnit(dinnerMap.get("unit").toString());
			dinnerList.add(dinner);
		});
		userHistory.getDinner().clear();
		userHistory.getDinner().addAll(dinnerList);

		((ArrayList<Map<String, Object>>) payload.get("other")).forEach(otherMap -> {
			com.stayfit.userhistoryservice.Other other = new com.stayfit.userhistoryservice.Other();
			other.setFoodId(Long.parseLong(otherMap.get("foodId").toString()));
			other.setAmount(Float.parseFloat(otherMap.get("amount").toString()));
			other.setUnit(otherMap.get("unit").toString());
			otherList.add(other);
		});
		userHistory.getOther().clear();
		userHistory.getOther().addAll(otherList);

		SaveUserHistoryRequest request = new SaveUserHistoryRequest();
		request.setUserHistory(userHistory);

		SaveUserHistoryResponse response = userHistoryPort.saveUserHistory(request);
		return response.getUserHistory();

	}

	/**
	 * 
	 * It returns all of the user's diet requests that are not completed yet.
	 */
	@Override
	@PreAuthorize("hasAuthority('DIET_REQUEST_READ')")
	public List<UserDietRequest> getAllUserDietRequestNotCompleted() {

		GetAllUserDietRequestNotCompletedRequest request = new GetAllUserDietRequestNotCompletedRequest();
		GetAllUserDietRequestNotCompletedResponse response = userDietPort.getAllUserDietRequestNotCompleted(request);

		return response.getUserDietRequest();
	}

	/**
	 * 
	 * It returns the user's diet request that is not completed yet.
	 */
	@Override
	@PreAuthorize("hasAuthority('DIET_REQUEST_READ')")
	public UserDietRequest getUserDietRequestNotCompletedByUserId(Long id) {

		GetUserDietRequestNotCompletedByUserIdRequest request = new GetUserDietRequestNotCompletedByUserIdRequest();

		request.setUserId(id);

		GetUserDietRequestNotCompletedByUserIdResponse response = userDietPort
				.getUserDietRequestNotCompletedByUserId(request);

		return response.getUserDietRequest();

	}

	/**
	 * 
	 * It saves/updates the user's diet request into the system.
	 */
	@Override
	@PreAuthorize("hasAuthority('DIET_REQUEST_CREATE')")
	public UserDietRequest saveUserDietRequest(Long id, Map<String, Object> payload) {

		SaveUserDietRequestRequest request = new SaveUserDietRequestRequest();

		UserDietRequest userDietRequest = new UserDietRequest();
		userDietRequest.setUserId(id);
		userDietRequest.setBodyFatPerc(Float.parseFloat(payload.get("bodyFatPerc").toString()));
		userDietRequest.setTargetWeight(Float.parseFloat(payload.get("targetWeight").toString()));
		userDietRequest.setNumTrainingDays(Integer.parseInt(payload.get("numTrainingDays").toString()));
		userDietRequest.setJobKind(JobKind.fromValue(payload.get("jobKind").toString()));
		userDietRequest.setNote(payload.getOrDefault("note", "").toString());
		userDietRequest.setCompleted(false);
		request.setUserDietRequest(userDietRequest);

		SaveUserDietRequestResponse response = userDietPort.saveUserDietRequest(request);

		return response.getUserDietRequest();
	}

	/**
	 * 
	 * It returns the user's diet by his user id.
	 */
	@Override
	@PreAuthorize("hasAuthority('DIET_READ')")
	public UserDiet getUserDiet(Long id) {

		GetUserDietByUserIdRequest request = new GetUserDietByUserIdRequest();

		request.setUserId(id);

		GetUserDietByUserIdResponse response = userDietPort.getUserDietByUserId(request);

		return response.getUserDiet();
	}

	/**
	 * 
	 * It saves/updates the user's diet into the system.
	 */
	@Override
	@PreAuthorize("hasAuthority('DIET_CREATE')")
	public UserDiet saveUserDiet(Long id, Map<String, Object> payload) {

		SaveUserDietRequest request = new SaveUserDietRequest();

		UserDiet userDiet = new UserDiet();
		userDiet.setNutritionistId(Long.parseLong(payload.get("nutritionistId").toString()));
		userDiet.setUserId(id);

		List<DietDay> week = new ArrayList<DietDay>(7);

		((ArrayList<Map<String, Object>>) payload.get("week")).forEach(dietDayMap -> {
			List<Breakfast> breakfastList = new ArrayList<Breakfast>();
			List<Lunch> lunchList = new ArrayList<Lunch>();
			List<Dinner> dinnerList = new ArrayList<Dinner>();
			List<Other> otherList = new ArrayList<Other>();

			DietDay dietDay = new DietDay();

			((ArrayList<Map<String, Object>>) dietDayMap.get("breakfast")).forEach(breakfastMap -> {
				Breakfast breakfast = new Breakfast();
				breakfast.setFoodId(Long.parseLong(breakfastMap.get("foodId").toString()));
				breakfast.setAmount(Float.parseFloat(breakfastMap.get("amount").toString()));
				breakfast.setUnit(breakfastMap.get("unit").toString());
				breakfastList.add(breakfast);
			});
			dietDay.getBreakfast().addAll(breakfastList);

			((ArrayList<Map<String, Object>>) dietDayMap.get("lunch")).forEach(lunchMap -> {
				Lunch lunch = new Lunch();
				lunch.setFoodId(Long.parseLong(lunchMap.get("foodId").toString()));
				lunch.setAmount(Float.parseFloat(lunchMap.get("amount").toString()));
				lunch.setUnit(lunchMap.get("unit").toString());
				lunchList.add(lunch);
			});
			dietDay.getLunch().addAll(lunchList);

			((ArrayList<Map<String, Object>>) dietDayMap.get("dinner")).forEach(dinnerMap -> {
				Dinner dinner = new Dinner();
				dinner.setFoodId(Long.parseLong(dinnerMap.get("foodId").toString()));
				dinner.setAmount(Float.parseFloat(dinnerMap.get("amount").toString()));
				dinner.setUnit(dinnerMap.get("unit").toString());
				dinnerList.add(dinner);
			});
			dietDay.getDinner().addAll(dinnerList);

			((ArrayList<Map<String, Object>>) dietDayMap.get("other")).forEach(otherMap -> {
				Other other = new Other();
				other.setFoodId(Long.parseLong(otherMap.get("foodId").toString()));
				other.setAmount(Float.parseFloat(otherMap.get("amount").toString()));
				other.setUnit(otherMap.get("unit").toString());
				otherList.add(other);
			});
			dietDay.getOther().addAll(otherList);

			week.add(dietDay);
		});

		userDiet.getWeek().clear();
		userDiet.getWeek().addAll(week);

		request.setUserDiet(userDiet);

		SaveUserDietResponse response = userDietPort.saveUserDiet(request);

		return response.getUserDiet();
	}

}