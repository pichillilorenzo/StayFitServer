package com.stayfit.fatsecretservice.app.endpoint;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.stayfit.fatsecretservice.GetfoodByIdRequest;
import com.stayfit.fatsecretservice.GetfoodByIdResponse;
import com.stayfit.fatsecretservice.GetfoodByNameRequest;
import com.stayfit.fatsecretservice.GetfoodByNameResponse;

public interface FatSecretEndpoint {

	JAXBElement<GetfoodByIdResponse> getUserById(JAXBElement<GetfoodByIdRequest> request);

	JAXBElement<GetfoodByNameResponse> getUserByName(JAXBElement<GetfoodByNameRequest> request);

}