package com.stayfit.app.service;

import com.stayfit.app.exception.ResourceNotFoundException;

public interface AmazonService {

	com.stayfit.amazonservice.Products getListFood(String name) throws ResourceNotFoundException;

}