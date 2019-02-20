package com.stayfit.Amazon.app.service;

import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

public interface AmazonService {

	Document getFoodByName(String name) throws Exception;

}