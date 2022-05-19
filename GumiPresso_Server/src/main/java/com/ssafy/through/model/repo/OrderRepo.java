package com.ssafy.through.model.repo;

import java.util.List;

import com.ssafy.through.model.dto.Order;

public interface OrderRepo {

	int insert(Order order);
	List<Order> select(String user_id);
	int search();
}
