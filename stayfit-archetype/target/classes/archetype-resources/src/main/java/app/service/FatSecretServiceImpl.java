package ${package}.app.service;

import com.fatsecret.platform.services.FatsecretService;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.Food;
import ${package}.app.exception.ResourceNotFoundException;
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


	String key = "************************";
	String secret = "**********************";
	final FatsecretService service = new FatsecretService(key, secret);

	@Override
	public Food getFoodById(Long id) throws ResourceNotFoundException {
		return null;
	}

	@Override
	public List<CompactFood> search(@RequestBody Map<String, Object> payload) throws Exception {
		return null;
	}	
}