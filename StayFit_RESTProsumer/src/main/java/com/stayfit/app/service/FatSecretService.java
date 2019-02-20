package com.stayfit.app.service;

import java.util.Map;

import com.stayfit.app.exception.ResourceNotFoundException;
import com.stayfit.fatsecretservice.Food;
import com.stayfit.fatsecretservice.Foods;

public interface FatSecretService {

	Food getFoodById(Long id) throws ResourceNotFoundException;

	Foods search(Map<String, Object> payload) throws ResourceNotFoundException;

}