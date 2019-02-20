package com.stayfit.Amazon.app.endpoint;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.stayfit.amazonservice.GetProductByNameRequest;
import com.stayfit.amazonservice.GetProductByNameResponse;

public interface AmazonEndpoint {

	JAXBElement<GetProductByNameResponse> GetProductByName(JAXBElement<GetProductByNameRequest> request)
			throws Exception;

}