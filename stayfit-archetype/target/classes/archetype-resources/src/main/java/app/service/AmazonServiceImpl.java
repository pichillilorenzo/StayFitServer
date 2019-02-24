package ${package}.app.service;

import ${package}.app.model.ListProducts;
import ${package}.app.util.AmazonUtils;

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
import org.w3c.dom.Document;

import com.stayfit.app.model.ListProducts;
/**
 * ;
 *
 */

@Service
public class AmazonServiceImpl implements AmazonService {
	
	public ListProducts  getListFood(String name) throws Exception {
		return null;

    }

}