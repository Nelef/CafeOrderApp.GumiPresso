package com.ssafy.through.model.service;

import java.util.List;

import com.ssafy.through.model.dto.Product;

public interface ProductService {

	Product select(String id);

	List<Product> search();
	
	List<Product> selectProductOrderByRating();
	List<Product> selectProductOrderByQuantity();
}
