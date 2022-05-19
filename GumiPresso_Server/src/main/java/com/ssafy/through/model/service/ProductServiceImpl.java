package com.ssafy.through.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.through.model.dto.Product;
import com.ssafy.through.model.repo.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo repo;

	@Transactional
	@Override
	public Product select(String id) {
		return repo.select(id);
	}

	@Override
	public List<Product> search() {
		return repo.search();
	}

}
