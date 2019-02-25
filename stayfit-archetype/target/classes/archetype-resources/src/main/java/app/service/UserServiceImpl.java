package ${package}.app.service;

import ${package}.app.configuration.ServicesConfiguration;
import ${package}.app.exception.ResourceNotFoundException;

import java.math.BigDecimal;
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

import ${package}.userservice.UserServicePortType;
import ${package}.userdietservice.Breakfast;
import ${package}.userdietservice.Dinner;
import ${package}.userdietservice.Lunch;
import ${package}.userdietservice.Other;
import ${package}.userdietservice.DietDay;
import ${package}.userdietservice.GetAllUserDietRequestNotCompletedRequest;
import ${package}.userdietservice.GetAllUserDietRequestNotCompletedResponse;
import ${package}.userservice.GetUserByIdRequest;
import ${package}.userservice.GetUserByIdResponse;
import ${package}.userservice.GetUserByUsernameRequest;
import ${package}.userservice.GetUserByUsernameResponse;
import ${package}.userdietservice.GetUserDietByUserIdRequest;
import ${package}.userdietservice.GetUserDietByUserIdResponse;
import ${package}.userdietservice.GetUserDietRequestNotCompletedByUserIdRequest;
import ${package}.userdietservice.GetUserDietRequestNotCompletedByUserIdResponse;
import ${package}.userhistoryservice.GetUserHistoryByDateRequest;
import ${package}.userhistoryservice.GetUserHistoryByDateResponse;
import ${package}.userdietservice.JobKind;
import ${package}.userservice.RegistrationRequest;
import ${package}.userservice.RegistrationResponse;
import ${package}.userdietservice.SaveUserDietRequest;
import ${package}.userdietservice.SaveUserDietRequestRequest;
import ${package}.userdietservice.SaveUserDietRequestResponse;
import ${package}.userdietservice.SaveUserDietResponse;
import ${package}.userhistoryservice.SaveUserHistoryRequest;
import ${package}.userhistoryservice.SaveUserHistoryResponse;
import ${package}.userservice.UpdateUserRequest;
import ${package}.userservice.UpdateUserResponse;
import ${package}.userdietservice.UserDiet;
import ${package}.userdietservice.UserDietRequest;
import ${package}.userdietservice.UserDietServicePortType;
import ${package}.userhistoryservice.UserHistory;
import ${package}.userhistoryservice.UserHistoryServicePortType;

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
	public ${package}.userservice.User getUserById(Long id) throws ResourceNotFoundException {
		return null;

	}
	
	/**
	 * 
	 * It returns the user by his username.
	 */
	@Override
	public ${package}.userservice.User getUserByUsername(String username) throws ResourceNotFoundException {

		return null;
	}

	/**
	 * 
	 * It is used to register the user into the system.
	 */
	@Override
	public ${package}.userservice.User registerUser(Map<String, Object> payload) {

		return null;
	}

	/**
	 * 
	 * It updates the user with his new fields.
	 */
	@Override
	@PreAuthorize("hasAuthority('USER_UPDATE')")
	public ${package}.userservice.User updateUser(Long id, Map<String, Object> payload)
			throws ResourceNotFoundException {

		return null;
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

		return null;

	}

	/**
	 * 
	 * It saves/updates the user's history into the system.
	 */
	@Override
	@PreAuthorize("hasAuthority('USER_HISTORY_CREATE')")
	public UserHistory saveUserHistory(Long id, String date, Map<String, Object> payload) {

		return null;

	}

	/**
	 * 
	 * It returns all of the user's diet requests that are not completed yet.
	 */
	@Override
	@PreAuthorize("hasAuthority('DIET_REQUEST_READ')")
	public List<UserDietRequest> getAllUserDietRequestNotCompleted() {

		return null;
	}

	/**
	 * 
	 * It returns the user's diet request that is not completed yet.
	 */
	@Override
	@PreAuthorize("hasAuthority('DIET_REQUEST_READ')")
	public UserDietRequest getUserDietRequestNotCompletedByUserId(Long id) {

		return null;

	}

	/**
	 * 
	 * It saves/updates the user's diet request into the system.
	 */
	@Override
	@PreAuthorize("hasAuthority('DIET_REQUEST_CREATE')")
	public UserDietRequest saveUserDietRequest(Long id, Map<String, Object> payload) {

		return null;
	}

	/**
	 * 
	 * It returns the user's diet by his user id.
	 */
	@Override
	@PreAuthorize("hasAuthority('DIET_READ')")
	public UserDiet getUserDiet(Long id) {

		return null;
	}

	/**
	 * 
	 * It saves/updates the user's diet into the system.
	 */
	@Override
	@PreAuthorize("hasAuthority('DIET_CREATE')")
	public UserDiet saveUserDiet(Long id, Map<String, Object> payload) {

		return null;
	}

}