package com.stayfit.app.service;

import com.stayfit.app.model.ListProducts;

public interface AmazonService {

	ListProducts getListFood(String name) throws Exception;

}