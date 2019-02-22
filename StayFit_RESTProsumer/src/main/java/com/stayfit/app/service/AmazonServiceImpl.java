/**
 * 
 */
package com.stayfit.app.service;

import com.stayfit.app.model.ListProducts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.stayfit.app.Utils.AmazonUtils;
import org.w3c.dom.Document;
/**
 * ;
 *
 */

@Service
public class AmazonServiceImpl implements AmazonService {
	
	/* (non-Javadoc)
	 * @see com.stayfit.app.service.AmazonService#getListFood(java.lang.String)
	 */
	@Override
	public ListProducts getListFood(String name) throws Exception {
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
			String url = "http://localhost:3000/onca/xml?Service=AWSECommerceService&Operation=ItemSearch&AWSAccessKeyId=ExampleID&AssociateTag=ExampleTag&Keywords="
					+ URLEncoder.encode(name, "UTF-8")
					+ "&ResponseGroup=Images%2CItemAttributes%2COffers&SearchIndex=Books&Timestamp=2015-08-11T17%3A51%3A56.000Z&Signature=oC%2Bv7Q33hROJDi2X79dYn%2BMzm9bRxDqYXk9qHTx3yEo%3D";

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
				Document doc = AmazonUtils.convertStringToDocument(sb);
				ListProducts response_product = new ListProducts();
				response_product = AmazonUtils.convert(doc);
			
				return response_product;

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

}