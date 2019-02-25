package ${package}.app.service;

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

import ${package}.userdietservice.UserDietServicePortType;
import ${package}.userhistoryservice.UserHistoryServicePortType;
import ${package}.userservice.UserServicePortType;

@Service
public class LoadBalancerServiceImpl implements LoadBalancerService {

	@Value("${loadbalancer.host}")
	private String host;

	@Value("${loadbalancer.port}")
	private String port;

	@Override
	public UserServicePortType getUserService() throws Exception {
		return null;
	}

	@Override
	public UserHistoryServicePortType getUserHistoryService() throws Exception {
		return null;
	}

	@Override
	public UserDietServicePortType getUserDietService() throws Exception {
		return null;
	}

	private String[] getHostPort(String serviceName) throws Exception {
		return null;
	}

	private String createNewUrl(String url, String host, String port) {
		return null;
	}
}
