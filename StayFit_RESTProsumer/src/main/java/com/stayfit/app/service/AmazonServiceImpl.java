/**
 * 
 */
package com.stayfit.app.service;

import com.stayfit.amazonservice.AmazonService;
import com.stayfit.amazonservice.AmazonServicePortType;
import com.stayfit.amazonservice.GetProductByNameRequest;
import com.stayfit.amazonservice.GetProductByNameResponse;
import com.stayfit.app.exception.ResourceNotFoundException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @author Matteo;
 *
 */

@Service
public class AmazonServiceImpl implements com.stayfit.app.service.AmazonService {
	
	@Override
	@PreAuthorize("hasAuthority('AMAZON_SEARCH')")
	public com.stayfit.amazonservice.Products getListFood(String name) throws ResourceNotFoundException {

		AmazonService Service = new AmazonService();
		AmazonServicePortType fatsecretPort = Service.getAmazonPort();

		GetProductByNameRequest request = new GetProductByNameRequest();
		request.setName(name);
		
		GetProductByNameResponse response = fatsecretPort.getProductByName(request);

		com.stayfit.amazonservice.Products foods = response.getProduct();
		if (foods != null) {
			return foods;
		}

		throw new ResourceNotFoundException("Foods", "name", name);
	}

}