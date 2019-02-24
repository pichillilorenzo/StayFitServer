package com.stayfit.app.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

@Service
public class LoadBalancerServiceImpl implements LoadBalancerService {

	@Value("${loadbalancer.host}")
	private String host;

	@Value("${loadbalancer.port}")
	private String port;

	@Override
	public UserServicePortType getUserService() throws Exception {
		String[] hostPort = getHostPort("userservice");

		com.stayfit.userservice.UserService userService = new com.stayfit.userservice.UserService();
		UserServicePortType userPort = userService.getUserPort();

		BindingProvider bp = (BindingProvider) userPort;
		String currentEndpoint = bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY).toString();
		String newUrl = createNewUrl(currentEndpoint, hostPort[0], hostPort[1]);
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newUrl);

		return userPort;
	}

	@Override
	public UserHistoryServicePortType getUserHistoryService() throws Exception {
		String[] hostPort = getHostPort("userhistoryservice");

		com.stayfit.userhistoryservice.UserHistoryService userHistoryService = new com.stayfit.userhistoryservice.UserHistoryService();
		UserHistoryServicePortType userHistoryPort = userHistoryService.getUserHistoryPort();

		BindingProvider bp = (BindingProvider) userHistoryPort;
		String currentEndpoint = bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY).toString();
		String newUrl = createNewUrl(currentEndpoint, hostPort[0], hostPort[1]);
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newUrl);

		return userHistoryPort;
	}

	@Override
	public UserDietServicePortType getUserDietService() throws Exception {
		String[] hostPort = getHostPort("userdietservice");

		com.stayfit.userdietservice.UserDietService userDietService = new com.stayfit.userdietservice.UserDietService();
		UserDietServicePortType userDietPort = userDietService.getUserDietPort();

		BindingProvider bp = (BindingProvider) userDietPort;
		String currentEndpoint = bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY).toString();
		String newUrl = createNewUrl(currentEndpoint, hostPort[0], hostPort[1]);
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newUrl);

		return userDietPort;
	}

	private String[] getHostPort(String serviceName) throws Exception {
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
			String url = "http://" + host + ":" + port + "/" + URLEncoder.encode(serviceName, "UTF-8");

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
				System.out.println(sb);
				return sb.split(":");

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

	private String createNewUrl(String url, String host, String port) {
		String newUrl = "";

		try {

			URL currentUrl = new URL(url);

			newUrl += currentUrl.getProtocol() + "://";
			newUrl += (host != null && !host.isEmpty()) ? host : currentUrl.getHost();
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
