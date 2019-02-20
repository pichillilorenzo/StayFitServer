/**
 * 
 */
package com.stayfit.Barcode.app.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;



import javax.xml.bind.JAXBElement;

import com.stayfit.Barcode.app.service.BarcodeService;
import com.stayfit.barcodeservice.GetNameByBarcodeRequest;
import com.stayfit.barcodeservice.GetNameByBarcodeResponse;
import com.stayfit.barcodeservice.ObjectFactory;



/**
 * @author Matteo
 *
 */
@Endpoint
public class BarcodeEndpoint {
	private static final String NAMESPACE_URI = "http://stayfit.com/barcodeservice";

	@Autowired
	private BarcodeService barcode;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetNameByBarcodeRequest")
	@ResponsePayload
	public JAXBElement<GetNameByBarcodeResponse> GetProductByName(@RequestPayload JAXBElement<GetNameByBarcodeRequest> request) throws Exception{
		ObjectFactory factory = new ObjectFactory();
		GetNameByBarcodeResponse response = factory.createGetNameByBarcodeResponse();
		String name = barcode.getNameByBarcode(request.getValue().getBarcode());
		response.setName(name);
		return factory.createGetNameByBarcodeResponse(response);
	}
	
}
