/**
 * 
 */
package com.stayfit.Amazon.app.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



import javax.xml.bind.JAXBElement;

import com.stayfit.Amazon.app.service.AmazonService;
import com.stayfit.Amazon.app.util.AmazonUtils;
import com.stayfit.amazonservice.GetProductByNameRequest;
import com.stayfit.amazonservice.GetProductByNameResponse;
import com.stayfit.amazonservice.ObjectFactory;
import com.stayfit.amazonservice.Products;

/**
 * @author Matteo
 *
 */
@Endpoint
public class AmazonEndpointImpl implements AmazonEndpoint {
	private static final String NAMESPACE_URI = "http://stayfit.com/amazonservice";

	@Autowired
	private AmazonService amazon;
	
	
	@Autowired
	private AmazonUtils amazontransformer;
	
	/* (non-Javadoc)
	 * @see com.stayfit.Amazon.app.endpoint.AmazonEndpoint#GetProductByName(javax.xml.bind.JAXBElement)
	 */
	@Override
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetProductByNameRequest")
	@ResponsePayload
	public JAXBElement<GetProductByNameResponse> GetProductByName(@RequestPayload JAXBElement<GetProductByNameRequest> request) throws Exception{
		ObjectFactory factory = new ObjectFactory();
		GetProductByNameResponse response = factory.createGetProductByNameResponse();
		Products product = amazontransformer.convert(amazon.getFoodByName(request.getValue().getName()));
		response.setProduct(product);
		return factory.createGetProductByNameResponse(response);
	}
	
}
