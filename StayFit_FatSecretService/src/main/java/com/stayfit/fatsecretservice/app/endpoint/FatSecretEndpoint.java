/**
 * 
 */
package com.stayfit.fatsecretservice.app.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;

import com.stayfit.fatsecretservice.Food;
import com.stayfit.fatsecretservice.GetfoodByIdRequest;
import com.stayfit.fatsecretservice.GetfoodByIdResponse;
import com.stayfit.fatsecretservice.GetfoodByNameRequest;
import com.stayfit.fatsecretservice.GetfoodByNameResponse;
import com.stayfit.fatsecretservice.ObjectFactory;
import com.stayfit.fatsecretservice.app.service.FatSecretService;
import com.stayfit.fatsecretservice.app.util.FatSecretTransformer;





/**
 * @author Matteo
 *
 */
@Endpoint
public class FatSecretEndpoint {
	private static final String NAMESPACE_URI = "http://stayfit.com/fatsecretservice";
	
	@Autowired
    private FatSecretService fatsecret;
	
	
	@Autowired
	private FatSecretTransformer fatsecrettransformer;
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetfoodByIdRequest")
	@ResponsePayload
	public JAXBElement<GetfoodByIdResponse> getUserById(@RequestPayload JAXBElement<GetfoodByIdRequest> request) {
    
		ObjectFactory factory = new ObjectFactory();
		GetfoodByIdResponse response = factory.createGetfoodByIdResponse();
		com.stayfit.fatsecretservice.Food food =fatsecrettransformer.convert( fatsecret.getFoodById(request.getValue().getId()));
		response.setFood(food);
		return factory.createGetfoodByIdResponse(response);
	}
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetfoodByNameRequest")
	@ResponsePayload
	public JAXBElement<GetfoodByNameResponse> getUserByName(@RequestPayload JAXBElement<GetfoodByNameRequest> request) {
		ObjectFactory factory = new ObjectFactory();
		GetfoodByNameResponse response = factory.createGetfoodByNameResponse();
		com.stayfit.fatsecretservice.Foods foods =fatsecrettransformer.convertbyname(fatsecret.getFoodByName((request.getValue().getName())));
		response.setFoods(foods);;
		return factory.createGetfoodByNameResponse(response);
	}
}
