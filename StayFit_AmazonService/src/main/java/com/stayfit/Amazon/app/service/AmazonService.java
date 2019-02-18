/**
 * 
 */
package com.stayfit.Amazon.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


import org.w3c.dom.Document;

import com.stayfit.Amazon.app.util.AmazonUtils;

/**
 * @author Matteo
 *
 */

@Service
public class AmazonService {
	@Autowired
	
	private AmazonUtils Utils;
	
	@Transactional(readOnly = true)
	public Document  getFoodByName(String name) throws Exception {
		try {

			// build the url
			String url = "http://localhost:3000/onca/xml?Service=AWSECommerceService&Operation=ItemSearch&AWSAccessKeyId=ExampleID&AssociateTag=ExampleTag&Keywords="+URLEncoder.encode(name, "UTF-8")+"&ResponseGroup=Images%2CItemAttributes%2COffers&SearchIndex=Books&Timestamp=2015-08-11T17%3A51%3A56.000Z&Signature=oC%2Bv7Q33hROJDi2X79dYn%2BMzm9bRxDqYXk9qHTx3yEo%3D";
			
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

				return Utils.convertStringToDocument(sb);

			default:
				if (c != null) {
					c.disconnect();
				}
				throw new Exception("Http Error: " + status);
			}
		} catch (Exception  e) {
			throw new Exception(e);
		}
	}
	
	

	

}
	
	
