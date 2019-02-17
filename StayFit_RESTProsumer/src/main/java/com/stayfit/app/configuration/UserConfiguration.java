/**
 * 
 */
package com.stayfit.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * @author lorenzo
 *
 */
@Configuration
public class UserConfiguration {
	
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("com.stayfit.userservice");
		return marshaller;
	}

	@Bean
	public WebServiceTemplate webServiceTemplate() {
		WebServiceTemplate client = new WebServiceTemplate();
		client.setDefaultUri("http://localhost:8082/user/services/user");
		client.setMarshaller(marshaller());
		client.setUnmarshaller(marshaller());
		return client;
	}
	
}
