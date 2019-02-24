package ${package}.app.configuration;

import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ${package}.userdietservice.UserDietServicePortType;
import ${package}.userhistoryservice.UserHistoryServicePortType;
import ${package}.userservice.UserServicePortType;

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
		return null;
	}

	@Bean
	public UserHistoryServicePortType getUserHistoryService() {
		return null;
	}

	@Bean
	public UserDietServicePortType getUserDietService() {
		return null;
	}

	public String createNewUrl(String url, String ip, String port) {
		return null;
	}
}
