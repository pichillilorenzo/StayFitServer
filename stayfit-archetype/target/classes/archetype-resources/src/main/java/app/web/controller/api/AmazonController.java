package ${package}.app.web.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ${package}.app.model.ListProducts;

import ${package}.app.model.ListProducts;
import ${package}.app.service.AmazonService;

@RestController
@RequestMapping("/api/v1/amazon")
public class AmazonController {
	
	@Autowired
    private AmazonService amazon;
	
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody
    ListProducts getListFood(@PathVariable("name") String name) throws Exception {
		//TODO
		return null;
    }
	
}
