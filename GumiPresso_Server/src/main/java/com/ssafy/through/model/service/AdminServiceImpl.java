package com.ssafy.through.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.through.model.dto.AosOrderDetail;
import com.ssafy.through.model.dto.AosOrderForm;
import com.ssafy.through.model.dto.DateDTO;
import com.ssafy.through.model.dto.Order;
import com.ssafy.through.model.dto.OrderDetail;
import com.ssafy.through.model.dto.RecentOrder;
import com.ssafy.through.model.dto.Sales;
import com.ssafy.through.model.dto.User;
import com.ssafy.through.model.repo.AdminRepo;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminRepo repo;
	
	@Override
	public User loginAdmin(User user) {
		return repo.loginAdmin(user);
	}

	@Override
	public User getAdminUser(String id) {
		return repo.getAdminUser(id);
	}

	@Override
	public int checkId(String id) {
		return repo.checkId(id);
	}

	@Transactional
	@Override
	public int insertAdmin(User user) { 
		return repo.insertAdmin(user);
	}

	@Override
	public List<Order> selectOrderByCompleted() {
		return repo.selectOrderByCompleted();
	}

	@Override
	public List<OrderDetail> selectOrderDetailByOrderId(String orderId) {
		return repo.selectOrderDetailByOrderId(orderId);
	}

	@Override
	public List<RecentOrder> convertOrdersToRecentOrder(List<Order> orders) {
		List<RecentOrder> list = new ArrayList<RecentOrder>();
		List<OrderDetail> detailList;
		for(int i = 0; i < orders.size(); i++) {
			Order order = orders.get(i);
			detailList = selectOrderDetailByOrderId(order.getoId().toString());
			list.add(new RecentOrder(order.getoId(), order.getOrderTime(), order.getOrderTable(), 
					order.getCompleted(), detailList));
		}
		
		return list;
	}

	@Override
	public int orderComplete(int orderId) {		
		return repo.orderComplete(orderId);
	}

	@Override
	public List<Sales> selectYear(DateDTO date) {
		return repo.selectYear(date);
	}

	@Override
	public List<Sales> selectYearType(DateDTO date) {
		return repo.selectYearType(date);
	}

	@Override
	public List<Sales> selectMonth(DateDTO date) {
		return repo.selectMonth(date);
	}

	@Override
	public List<Sales> selectMonthType(DateDTO date) {
		return repo.selectMonthType(date);
	}

	@Override
	public List<Sales> selectDay(DateDTO date) {
		return repo.selectDay(date);
	}

	@Override
	public List<Sales> selectDayType(DateDTO date) {
		return repo.selectDayType(date);
	}
	
	
	
	
	
	
}
