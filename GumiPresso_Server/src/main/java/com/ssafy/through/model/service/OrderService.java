package com.ssafy.through.model.service;

import java.util.List;

import com.ssafy.through.model.dto.Order;
import com.ssafy.through.model.dto.Table;

public interface OrderService {

	int insert(Order order);
	List<Order> select(String user_id);
	int search();
	List<Table> getTable();
	int updateTable(int tableId);
}
