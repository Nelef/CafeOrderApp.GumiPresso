package com.ssafy.through.model.repo;

import java.util.List;
import java.util.Map;

import com.ssafy.through.model.dto.DateDTO;
import com.ssafy.through.model.dto.Order;
import com.ssafy.through.model.dto.OrderDetail;
import com.ssafy.through.model.dto.Product;
import com.ssafy.through.model.dto.Sales;
import com.ssafy.through.model.dto.User;

public interface AdminRepo {
	User loginAdmin(User user);
	
	User getAdminUser(String id);
	
	int checkId(String id);
	
	int insertAdmin(User user);
	
	List<Order> selectOrderByCompleted();
	 
	List<OrderDetail> selectOrderDetailByOrderId(String orderId);
	
	int orderComplete(int orderId);
	
	List<Sales> selectYear(DateDTO date);
	List<Sales> selectYearType(DateDTO date);
	List<Sales> selectMonth(DateDTO date);
	List<Sales> selectMonthType(DateDTO date);
	List<Sales> selectDay(DateDTO date);
	List<Sales> selectDayType(DateDTO date);
	
	int insertFCMTokenUser(Map<String, String> map);
	int updateFCMTokenUser(Map<String, String> map);
	List<String> selectAllToken(); 
	int insertProduct(Product product);
	int updateProduct(Product product);
	int deleteProduct(int id);
}
