package ${package}.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.fatsecret.platform.model.CompactFood;
import ${package}.app.exception.ResourceNotFoundException;

public interface FatSecretService {

	com.fatsecret.platform.model.Food getFoodById(Long id) throws ResourceNotFoundException;

	List<CompactFood> search(Map<String, Object> payload) throws Exception;

}