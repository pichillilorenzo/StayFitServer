package com.stayfit.fatsecretservice.app.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.Food;

public interface FatSecretService {

	Food getFoodById(Long id);

	List<CompactFood> getFoodByName(String name);

}