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
public class FatSecretServiceImpl {
	
	@PreAuthorize("hasAuthority('FOOD_READ')")
    public com.stayfit.fatsecretservice.Food getFoodById(Long id) throws ResourceNotFoundException {

        FatSecretService Service = new FatSecretService();
        FatSecretServicePortType fatsecretPort = Service.getFatSecretPort();

        GetfoodByIdRequest request = new GetfoodByIdRequest();
        request.setId(id);

        GetfoodByIdResponse response = fatsecretPort.getFoodById(request);

        com.stayfit.fatsecretservice.Food food = response.getFood();
        if (food != null) {
            return food;
        }

        throw new ResourceNotFoundException("Food", "id", id);
    }
	
	@PreAuthorize("hasAuthority('FOOD_SEARCH')")
    public com.stayfit.fatsecretservice.Foods search(@RequestBody Map<String, Object> payload)
            throws ResourceNotFoundException {

        String name = payload.get("name").toString();
        Integer kcal = Integer.parseInt(payload.get("kcal").toString());
        String barcode = payload.get("barcode").toString();
        System.out.println(barcode);
        if (barcode != "") {
            BarcodeService barcode_Service = new BarcodeService();
            BarcodeServicePortType barcode_port = barcode_Service.getBarcodePort();

            GetNameByBarcodeRequest request_barcode = new GetNameByBarcodeRequest();
            request_barcode.setBarcode(barcode);
            GetNameByBarcodeResponse response_barcode = barcode_port.getNameByBarcode(request_barcode);

            name = response_barcode.getName();
            System.out.println(name);
        }

        FatSecretService Service = new FatSecretService();
        FatSecretServicePortType fatsecretPort = Service.getFatSecretPort();

        GetfoodByNameRequest request = new GetfoodByNameRequest();
        request.setName(name);

        GetfoodByNameResponse response = fatsecretPort.getFoodByName(request);

        com.stayfit.fatsecretservice.Foods foods = new com.stayfit.fatsecretservice.Foods();

        List<com.stayfit.fatsecretservice.Item> items = new ArrayList<>();

        if (kcal != 0) {
            response.getFoods().getFood().forEach(listItem -> {
                Pattern pattern = Pattern.compile("Calories: ([\\d]+)kcal");
                Matcher matcher = pattern.matcher(listItem.getDescription().toString());
                if (matcher.find()) {
                    Integer kilocalories = Integer.parseInt(matcher.group(1).toString());
                    if (kilocalories <= kcal) {
                        items.add(listItem);

                    }

                }
            });

            foods.getFood().addAll(items);

        } else {

            foods = response.getFoods();
        }

        return foods;

    }

}