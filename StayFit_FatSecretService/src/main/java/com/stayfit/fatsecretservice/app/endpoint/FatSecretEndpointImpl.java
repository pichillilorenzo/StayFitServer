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
public class FatSecretEndpointImpl implements FatSecretEndpoint {
	private static final String NAMESPACE_URI = "http://stayfit.com/fatsecretservice";
	
	@Autowired
    private FatSecretService fatsecret;
	
	
	@Autowired
	private FatSecretTransformer fatsecrettransformer;
	
	
	/**
	  * This method maps the WSDL operation "getFoodById" with a GetfoodByIdRequest input message
	  * and a GetfoodByIdResponse output message.
	  * 
	  * It returns the product by its id.
	  */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetfoodByIdRequest")
	@ResponsePayload
	public JAXBElement<GetfoodByIdResponse> getFoodById(@RequestPayload JAXBElement<GetfoodByIdRequest> request) {
    
		ObjectFactory factory = new ObjectFactory();
		GetfoodByIdResponse response = factory.createGetfoodByIdResponse();
		com.stayfit.fatsecretservice.Food food =fatsecrettransformer.convert( fatsecret.getFoodById(request.getValue().getId()));
		response.setFood(food);
		return factory.createGetfoodByIdResponse(response);
	}
	
	
	
	/**
	  * This method maps the WSDL operation "getFoodByName" with a GetfoodByNameRequest input message
	  * and a GetfoodByNameResponse output message.
	  * 
	  * Return the list of products, through the rest Api of Fat-Secret, filtered by the name that 
	  * the user inserts as a parameter.
	  */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetfoodByNameRequest")
	@ResponsePayload
	public JAXBElement<GetfoodByNameResponse> getFoodByName(@RequestPayload JAXBElement<GetfoodByNameRequest> request) {
		ObjectFactory factory = new ObjectFactory();
		GetfoodByNameResponse response = factory.createGetfoodByNameResponse();
		com.stayfit.fatsecretservice.Foods foods =fatsecrettransformer.convertbyname(fatsecret.getFoodByName((request.getValue().getName())));
		response.setFoods(foods);;
		return factory.createGetfoodByNameResponse(response);
	}
}
