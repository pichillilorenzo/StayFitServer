package ${package}.app.web.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fatsecret.platform.model.CompactFood;
import com.stayfit.app.service.FatSecretService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/v1/fatsecret")
public class FatSecretController {
	
	@Autowired
    private FatSecretService fatsecret;
	
	@RequestMapping(value = "/getfoodbyid/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody
    com.fatsecret.platform.model.Food getFoodById(@PathVariable("id") Long id) {
		
		//TODO
		return null;
    }

	@RequestMapping(value= "/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
	public List<CompactFood> search(@RequestBody Map<String, Object> payload) throws Exception {
		//TODO
		return null;
    }
	
}
