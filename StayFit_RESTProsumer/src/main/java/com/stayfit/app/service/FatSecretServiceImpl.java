/**
 * 
 */
package com.stayfit.app.service;

import com.stayfit.app.exception.ResourceNotFoundException;
import com.stayfit.barcodeservice.BarcodeService;
import com.stayfit.barcodeservice.BarcodeServicePortType;
import com.stayfit.barcodeservice.GetNameByBarcodeRequest;
import com.stayfit.barcodeservice.GetNameByBarcodeResponse;
import com.stayfit.fatsecretservice.FatSecretService;
import com.stayfit.fatsecretservice.FatSecretServicePortType;
import com.stayfit.fatsecretservice.GetfoodByIdRequest;
import com.stayfit.fatsecretservice.GetfoodByIdResponse;
import com.stayfit.fatsecretservice.GetfoodByNameRequest;
import com.stayfit.fatsecretservice.GetfoodByNameResponse;
import com.stayfit.fatsecretservice.Food;
import com.stayfit.fatsecretservice.Foods;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matteo;
 *
 */

@Service
public class FatSecretServiceImpl implements com.stayfit.app.service.FatSecretService {
	
	
	//This method call the Fat-Secret SOAP service and return returns the 
	//product by its id.
	@Override
	@PreAuthorize("hasAuthority('FOOD_READ')")
    public Food getFoodById(Long id) throws ResourceNotFoundException {

        FatSecretService Service = new FatSecretService();
        FatSecretServicePortType fatsecretPort = Service.getFatSecretPort();

        GetfoodByIdRequest request = new GetfoodByIdRequest();
        request.setId(id);

        GetfoodByIdResponse response = fatsecretPort.getFoodById(request);

        Food food = response.getFood();
        if (food != null) {
            return food;
        }

        throw new ResourceNotFoundException("Food", "id", id);
    }
	
	
	
	
	//This function call the Amazon and Barcode SOAP service and returns the 
	//list of products filtered by product name or Kcal and Name or barcode and Name or Barcode.
	@Override
	@PreAuthorize("hasAuthority('FOOD_SEARCH')")
    public Foods search(@RequestBody Map<String, Object> payload)
            throws ResourceNotFoundException {
		
        String name = payload.get("name").toString();
        Integer kcal = Integer.parseInt(payload.get("kcal").toString());
        String barcode = payload.get("barcode").toString();
        
        // we control if the user insert the barcode 
        if (barcode != "") {
            BarcodeService barcode_Service = new BarcodeService();
            BarcodeServicePortType barcode_port = barcode_Service.getBarcodePort();

            GetNameByBarcodeRequest request_barcode = new GetNameByBarcodeRequest();
            request_barcode.setBarcode(barcode);
            GetNameByBarcodeResponse response_barcode = barcode_port.getNameByBarcode(request_barcode);

            name = response_barcode.getName();
            
        }

        FatSecretService Service = new FatSecretService();
        FatSecretServicePortType fatsecretPort = Service.getFatSecretPort();

        GetfoodByNameRequest request = new GetfoodByNameRequest();
        request.setName(name);

        GetfoodByNameResponse response = fatsecretPort.getFoodByName(request);

        Foods foods = new Foods();

        List<com.stayfit.fatsecretservice.Item> items = new ArrayList<>();
        // we control if the user insert the Kcal 
        if (kcal != 0) {
            response.getFoods().getFood().forEach(listItem -> {
            	//we use the regular expressions for filter for take the Kcal parametar from the desciprion of product
                Pattern pattern = Pattern.compile("Calories: ([\\d]+)kcal");
                Matcher matcher = pattern.matcher(listItem.getDescription().toString());
                if (matcher.find()) {
                	//we transform the parameter in integer
                    Integer kilocalories = Integer.parseInt(matcher.group(1).toString());
                    if (kilocalories <= kcal) {
                    	//add the product in a list 
                        items.add(listItem);

                    }

                }
            });
            // we add the list of product filtered
            foods.getFood().addAll(items);

        } else {

            foods = response.getFoods();
        }

        return foods;

    }

}