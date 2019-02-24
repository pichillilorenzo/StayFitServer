/**
 * 
 */
package com.stayfit.app.service;

import com.fatsecret.platform.services.FatsecretService;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.Food;
import com.stayfit.app.exception.ResourceNotFoundException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ;
 *
 */

@Service
public class FatSecretServiceImpl implements FatSecretService {

	/*
	 * we will need to initialize Fatsecret Service with our Fatsecret Application
	 * Consumer Key and associated Consumer Secret.
	 */
	String key = "b81048f4a42a486a86e9dd9cf1b920e9";
	String secret = "dccaedac974841ebacfce130397d15f3";
	final FatsecretService service = new FatsecretService(key, secret);

	@Override
	@PreAuthorize("hasAuthority('FOOD_READ')")
	public Food getFoodById(Long id) throws ResourceNotFoundException {

		Food food = service.getFood(id);

		if (food != null) {
			return food;
		}

		throw new ResourceNotFoundException("Food", "id", id);
	}

	@Override
	@PreAuthorize("hasAuthority('FOOD_SEARCH')")
	public List<CompactFood> search(@RequestBody Map<String, Object> payload) throws Exception {

		String name = payload.get("name").toString();
		Integer kcal = Integer.parseInt(payload.get("kcal").toString());
		String barcode = payload.get("barcode").toString();

		// we check if the user inserts the barcode
		if (barcode != "") {
			BarcodeService barcode_impl = new BarcodeServiceImpl();
			name = barcode_impl.getNameByBarcode(barcode);

		}
		Response<CompactFood> response = service.searchFoods(name);
		List<CompactFood> foods = response.getResults();

		List<CompactFood> items = new ArrayList<>();
		// we check if the user inserts the Kcal
		if (kcal != 0) {
			foods.forEach(listItem -> {
				// we use the regular expressions for filter for take the Kcal parametar from
				// the description of product
				Pattern pattern = Pattern.compile("Calories: ([\\d]+)kcal");
				Matcher matcher = pattern.matcher(listItem.getDescription().toString());
				if (matcher.find()) {
					// we transform the parameter in integer
					Integer kilocalories = Integer.parseInt(matcher.group(1).toString());
					if (kilocalories <= kcal) {
						// add the product in a list
						items.add(listItem);

					}

				}
			});
			// we add the list of product filtered
			return items;

		} else {

			return foods;
		}

	}

}