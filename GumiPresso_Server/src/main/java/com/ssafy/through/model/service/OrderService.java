package com.ssafy.through.model.service;

import java.util.List;

import com.ssafy.through.model.dto.Order;

public interface OrderService {

	int insert(Order order);
	List<Order> select(String user_id);
	int search();
}
