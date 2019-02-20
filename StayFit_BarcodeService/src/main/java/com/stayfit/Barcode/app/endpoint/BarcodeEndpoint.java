package com.stayfit.Barcode.app.endpoint;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.stayfit.barcodeservice.GetNameByBarcodeRequest;
import com.stayfit.barcodeservice.GetNameByBarcodeResponse;

public interface BarcodeEndpoint {

	JAXBElement<GetNameByBarcodeResponse> GetProductByName(JAXBElement<GetNameByBarcodeRequest> request)
			throws Exception;

}