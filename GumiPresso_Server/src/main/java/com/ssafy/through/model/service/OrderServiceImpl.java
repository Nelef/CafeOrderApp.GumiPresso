package com.ssafy.through.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.through.model.dto.Order;
import com.ssafy.through.model.repo.OrderRepo;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo repo;

	@Transactional
	@Override
	public int insert(Order order) {
		return repo.insert(order);
	}

	@Transactional
	@Override
	public List<Order> select(String user_id) {
		return repo.select(user_id);
	}

	@Override
	public int search() {
		return repo.search();
	}

}
