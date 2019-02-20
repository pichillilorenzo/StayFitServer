/**
 * 
 */
package com.stayfit.Barcode.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.InputStreamReader;

import java.net.URL;
import org.json.JSONObject;

/**
 * @author Matteo
 *
 */

@Service
public class BarcodeServiceImpl implements BarcodeService {

	/* (non-Javadoc)
	 * @see com.stayfit.Barcode.app.service.BarcodeService#getNameByBarcode(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public String getNameByBarcode(String barcode) throws Exception {

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
			String url = "https://www.datakick.org/api/items/" + barcode;

			// connect to url
			HttpsURLConnection c = null;
			URL u = new URL(url);
			c = (HttpsURLConnection) u.openConnection();
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
				JSONObject obj = new JSONObject(sb);
				String name = obj.getString("name");
				return name;

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
