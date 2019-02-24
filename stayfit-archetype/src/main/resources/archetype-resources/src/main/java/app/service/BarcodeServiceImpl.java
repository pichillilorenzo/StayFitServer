package ${package}.app.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

public class BarcodeServiceImpl implements BarcodeService {
	
	@Override
	@Transactional(readOnly = true)
	public String getNameByBarcode(String barcode) throws Exception {
		return "Hello";
	}


}
