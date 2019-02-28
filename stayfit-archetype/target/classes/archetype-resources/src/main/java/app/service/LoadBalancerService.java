package ${package}.app.service;

import java.net.MalformedURLException;

import javax.annotation.PostConstruct;

public interface LoadBalancerService {

	/**
	 * Init all the SOAP Web Service Ports.
	 */
	void init() throws MalformedURLException;

}