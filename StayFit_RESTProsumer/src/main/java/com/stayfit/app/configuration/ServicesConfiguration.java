package com.stayfit.app.configuration;

import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stayfit.userdietservice.UserDietServicePortType;
import com.stayfit.userhistoryservice.UserHistoryServicePortType;
import com.stayfit.userservice.UserServicePortType;

/**
 * This class is used to set the host and port of SOAP web services.
 */
@Configuration
public class ServicesConfiguration {

	public static String USER_SERVICE_ENDPOINT_IP;
	public static String USER_HISTORY_SERVICE_ENDPOINT_IP;
	public static String USER_DIET_SERVICE_ENDPOINT_IP;
	public static String OAUTH2_SERVICE_ENDPOINT_IP;

	public static String USER_SERVICE_ENDPOINT_PORT;
	public static String USER_HISTORY_SERVICE_ENDPOINT_PORT;
	public static String USER_DIET_SERVICE_ENDPOINT_PORT;
	public static String OAUTH2_SERVICE_ENDPOINT_PORT;

	@Bean
	public UserServicePortType getUserService() {
		com.stayfit.userservice.UserService userService = new com.stayfit.userservice.UserService();
		UserServicePortType userPort = userService.getUserPort();

		BindingProvider bp = (BindingProvider) userPort;
		String currentEndpoint = bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY).toString();
		String newUrl = createNewUrl(currentEndpoint, USER_SERVICE_ENDPOINT_IP, USER_SERVICE_ENDPOINT_PORT);
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newUrl);

		return userPort;
	}

	@Bean
	public UserHistoryServicePortType getUserHistoryService() {
		com.stayfit.userhistoryservice.UserHistoryService userHistoryService = new com.stayfit.userhistoryservice.UserHistoryService();
		UserHistoryServicePortType userHistoryPort = userHistoryService.getUserHistoryPort();

		BindingProvider bp = (BindingProvider) userHistoryPort;
		String currentEndpoint = bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY).toString();
		String newUrl = createNewUrl(currentEndpoint, USER_HISTORY_SERVICE_ENDPOINT_IP, USER_HISTORY_SERVICE_ENDPOINT_PORT);
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newUrl);

		return userHistoryPort;
	}

	@Bean
	public UserDietServicePortType getUserDietService() {
		com.stayfit.userdietservice.UserDietService userDietService = new com.stayfit.userdietservice.UserDietService();
		UserDietServicePortType userDietPort = userDietService.getUserDietPort();

		BindingProvider bp = (BindingProvider) userDietPort;
		String currentEndpoint = bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY).toString();
		String newUrl = createNewUrl(currentEndpoint, USER_DIET_SERVICE_ENDPOINT_IP, USER_DIET_SERVICE_ENDPOINT_PORT);
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newUrl);

		return userDietPort;
	}

	public String createNewUrl(String url, String ip, String port) {
		String newUrl = "";

		try {

			URL currentUrl = new URL(url);

			newUrl += currentUrl.getProtocol() + "://";
			newUrl += (ip != null && !ip.isEmpty()) ? ip : currentUrl.getHost();
			newUrl += ":" + ((port != null && !port.isEmpty()) ? port
					: ((currentUrl.getPort() != -1) ? currentUrl.getPort() : currentUrl.getDefaultPort()));
			newUrl += (currentUrl.getPath() != null) ? currentUrl.getPath() : "";
			newUrl += (currentUrl.getQuery() != null) ? currentUrl.getQuery() : "";

		} catch (Exception e) {
			e.printStackTrace();
			return url.toString();
		}

		return newUrl;
	}
}
