package com.ssafy.through.model.service;

import java.util.List;

import com.ssafy.through.model.dto.AosOrderForm;
import com.ssafy.through.model.dto.Order;
import com.ssafy.through.model.dto.OrderDetail;
import com.ssafy.through.model.dto.RecentOrder;
import com.ssafy.through.model.dto.User;

public interface AdminService {
	User loginAdmin(User user);
	
	User getAdminUser(String id);
	
	int checkId(String id);
	
	int insertAdmin(User user);
	
	List<Order> selectOrderByCompleted();
	 
	List<OrderDetail> selectOrderDetailByOrderId(String orderId);

	List<RecentOrder> convertOrdersToRecentOrder(List<Order> orders);
	
	int orderComplete(int orderId);
}
