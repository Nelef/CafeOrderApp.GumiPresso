package com.ssafy.through.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.through.model.dto.Order;
import com.ssafy.through.model.dto.OrderDetail;
import com.ssafy.through.model.dto.OrderForm;
import com.ssafy.through.model.dto.RecentOrder;
import com.ssafy.through.model.repo.OrderDetailRepo;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepo repo;

	@Transactional
	@Override
	public int insert(OrderForm orderForm) {
		return repo.insert(orderForm);
	}

	@Override
	public List<OrderDetail> select(String order_id) {
		return repo.select(order_id);
	}

	@Override
	public int search(String productId) {
		return repo.search(productId);
	}

	@Override
	public List<Order> searchRecentOrder(String userId) {
		return repo.searchRecentOrder(userId);
	}

	@Override
	public Order searchOrderDetail(String orderId) {
		return repo.searchOrderDetail(orderId);
	}
	

}
