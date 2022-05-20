package com.ssafy.through.model.repo;

import java.util.List;

import com.ssafy.through.model.dto.Order;
import com.ssafy.through.model.dto.OrderDetail;
import com.ssafy.through.model.dto.User;

public interface AdminRepo {
	User loginAdmin(User user);
	
	User getAdminUser(String id);
	
	int checkId(String id);
	
	int insertAdmin(User user);
	
	List<Order> selectOrderByCompleted();
	 
	List<OrderDetail> selectOrderDetailByOrderId(String orderId);
	
	int orderComplete(int orderId);
}
