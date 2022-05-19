package com.ssafy.through.model.service;

import java.util.List;

import com.ssafy.through.model.dto.Order;
import com.ssafy.through.model.dto.OrderDetail;
import com.ssafy.through.model.dto.OrderForm;
import com.ssafy.through.model.dto.RecentOrder;

public interface OrderDetailService {

	int insert(OrderForm orderForm);
	List<OrderDetail> select(String order_id);
	int search(String productId);
	List<Order> searchRecentOrder(String userId);
	Order searchOrderDetail(String orderId);
}
