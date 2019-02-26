package com.stayfit.app.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.ws.BindingProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stayfit.userdietservice.UserDietServicePortType;
import com.stayfit.userhistoryservice.UserHistoryServicePortType;
import com.stayfit.userservice.UserServicePortType;

/**
 * 
 * This class communicates with a load balancer to decide where to redirect the current user request.
 */
@Service
public class LoadBalancerServiceImpl implements LoadBalancerService {

	@Value("${loadbalancer.url}")
	private String loadBalancerUrl;
	
	private Map<String, UserServicePortType> cachedUserServicePort = new HashMap<>();
	private Map<String, UserHistoryServicePortType> cachedUserHistoryServicePort = new HashMap<>();
	private Map<String, UserDietServicePortType> cachedUserDietServicePort = new HashMap<>();
	
	/**
	 * Gets the User SOAP Web Service Port.
	 */
	@Override
	public UserServicePortType getUserService() throws Exception {
		String url = getServiceUrl("userservice");
		
		if (cachedUserServicePort.containsKey(url)) {
			return cachedUserServicePort.get(url);
		}
		
		com.stayfit.userservice.UserService userService = new com.stayfit.userservice.UserService();
		UserServicePortType userPort = userService.getUserPort();

		BindingProvider bp = (BindingProvider) userPort;
		String currentEndpoint = bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY).toString();
		String newUrl = createNewUrl(currentEndpoint, url);
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newUrl);
		
		cachedUserServicePort.put(url, userPort);
		
		return userPort;
	}
	
	/**
	 * Gets the User History SOAP Web Service Port.
	 */
	@Override
	public UserHistoryServicePortType getUserHistoryService() throws Exception {
		String url = getServiceUrl("userhistoryservice");
		
		if (cachedUserHistoryServicePort.containsKey(url)) {
			return cachedUserHistoryServicePort.get(url);
		}

		com.stayfit.userhistoryservice.UserHistoryService userHistoryService = new com.stayfit.userhistoryservice.UserHistoryService();
		UserHistoryServicePortType userHistoryPort = userHistoryService.getUserHistoryPort();

		BindingProvider bp = (BindingProvider) userHistoryPort;
		String currentEndpoint = bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY).toString();
		String newUrl = createNewUrl(currentEndpoint, url);
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newUrl);
		
		cachedUserHistoryServicePort.put(url, userHistoryPort);
		
		return userHistoryPort;
	}
	
	/**
	 * Gets the User Diet SOAP Web Service Port.
	 */
	@Override
	public UserDietServicePortType getUserDietService() throws Exception {
		String url = getServiceUrl("userdietservice");
		
		if (cachedUserDietServicePort.containsKey(url)) {
			return cachedUserDietServicePort.get(url);
		}
		
		com.stayfit.userdietservice.UserDietService userDietService = new com.stayfit.userdietservice.UserDietService();
		UserDietServicePortType userDietPort = userDietService.getUserDietPort();

		BindingProvider bp = (BindingProvider) userDietPort;
		String currentEndpoint = bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY).toString();
		String newUrl = createNewUrl(currentEndpoint, url);
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newUrl);
		
		cachedUserDietServicePort.put(url, userDietPort);
		
		return userDietPort;
	}
	
	/**
	 * Returns the host and port of the web service from the load balancer
	 */
	private String getServiceUrl(String serviceName) throws Exception {
		// In this portion of code we fix the SSL error for MacOS
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				// No need to implement.
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				// No need to implement.
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception(e);
		}

		try {

			// build the url
			String url = loadBalancerUrl + "/" + URLEncoder.encode(serviceName, "UTF-8");

			// connect to url
			HttpURLConnection c = null;

			URL u = new URL(url);
			c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.connect();
			int status = c.getResponseCode();

			switch (status) {
			case 201:
			case 200:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
				String sb = "";
				String line;
				while ((line = br.readLine()) != null) {
					sb += line;
				}
				br.close();

				if (c != null) {
					c.disconnect();
				}
				
				System.out.println("Load Balancer redirects to: " + sb);
				return sb;

			default:
				if (c != null) {
					c.disconnect();
				}
				System.out.println("Http Error: " + status);
				throw new Exception("Http Error: " + status);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	/**
	 * Utility to create a valid url with new host and port.
	 */
	private String createNewUrl(String oldUrl, String url) {
		String newUrl = url;

		try {
			URL currentUrl = new URL(oldUrl);

			newUrl += (currentUrl.getPath() != null) ? currentUrl.getPath() : "";
			newUrl += (currentUrl.getQuery() != null) ? currentUrl.getQuery() : "";

		} catch (Exception e) {
			e.printStackTrace();
			return url.toString();
		}

		return newUrl;
	}
}
