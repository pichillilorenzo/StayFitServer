package ${package}.app.service;

import ${package}.app.model.ListProducts;

public interface AmazonService {

	ListProducts getListFood(String name) throws Exception;

}