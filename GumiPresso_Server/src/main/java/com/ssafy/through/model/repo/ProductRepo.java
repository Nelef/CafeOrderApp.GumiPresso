package com.ssafy.through.model.repo;

import java.util.List;

import com.ssafy.through.model.dto.Product;

public interface ProductRepo {
	
	Product select(String id);

	List<Product> search();
}
