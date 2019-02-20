/**
 * 
 */
package com.stayfit.Barcode.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;


/**
 * @author Matteo
 *
 */

@Service
public class BarcodeService {

	@Transactional(readOnly = true)
	public String getNameByBarcode(String barcode) throws Exception {
		try {

			// build the url
			String url = "https://www.datakick.org/api/items/" + barcode;

			// connect to url
			HttpURLConnection c = null;
			URL u = new URL(url);
			c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			// c.setRequestProperty("Content-length", "0");
			c.setRequestProperty("Access-Control-Request-Method", "POST, GET, OPTIONS, HEAD");
			c.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
			c.setRequestProperty("Upgrade-Insecure-Requests", "1");
			// c.setUseCaches(false);
			// c.setAllowUserInteraction(false);
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
				throw new Exception("Http Error: " + status);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
