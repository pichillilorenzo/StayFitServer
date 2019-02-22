package com.stayfit.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stayfit.app.configuration.ServicesConfiguration;

@SpringBootApplication
public class StayFitApplication {

	public static void main(String[] args) {
		
		if (args.length > 1) {
			// initialize web services endpoints using command line arguments.
			try {
				System.out.println(args[1]);
				System.out.println(args[2]);
				System.out.println(args[3]);
				System.out.println(args[4]);
				
				String[] USER_SERVICE_ENDPOINT = args[1].split(":");
				ServicesConfiguration.USER_SERVICE_ENDPOINT_IP = USER_SERVICE_ENDPOINT[0];
				ServicesConfiguration.USER_SERVICE_ENDPOINT_PORT = USER_SERVICE_ENDPOINT[1];
				
				String[] USER_HISTORY_SERVICE_ENDPOINT = args[2].split(":");
				ServicesConfiguration.USER_HISTORY_SERVICE_ENDPOINT_IP = USER_HISTORY_SERVICE_ENDPOINT[0];
				ServicesConfiguration.USER_HISTORY_SERVICE_ENDPOINT_PORT = USER_HISTORY_SERVICE_ENDPOINT[1];
				
				String[] USER_DIET_SERVICE_ENDPOINT = args[3].split(":");
				ServicesConfiguration.USER_DIET_SERVICE_ENDPOINT_IP = USER_DIET_SERVICE_ENDPOINT[0];
				ServicesConfiguration.USER_DIET_SERVICE_ENDPOINT_PORT = USER_DIET_SERVICE_ENDPOINT[1];
				
				String[] OAUTH2_SERVICE_ENDPOINT = args[4].split(":");
				ServicesConfiguration.OAUTH2_SERVICE_ENDPOINT_IP = OAUTH2_SERVICE_ENDPOINT[0];
				ServicesConfiguration.OAUTH2_SERVICE_ENDPOINT_PORT = OAUTH2_SERVICE_ENDPOINT[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		SpringApplication.run(StayFitApplication.class, args);
	}

}

