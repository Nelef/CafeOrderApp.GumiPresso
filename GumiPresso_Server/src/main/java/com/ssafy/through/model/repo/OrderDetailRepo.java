package com.ssafy.through.model.repo;

import java.util.List;

import com.ssafy.through.model.dto.Order;
import com.ssafy.through.model.dto.OrderDetail;
import com.ssafy.through.model.dto.OrderForm;
import com.ssafy.through.model.dto.RecentOrder;

public interface OrderDetailRepo {

	int insert(OrderForm orderForm);
	List<OrderDetail> select(String orderId);
	int search(String productId);
	List<Order> searchRecentOrder(String userId);
	Order searchOrderDetail(String orderId);
}
