package com.ssafy.through.model.dto;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "RecentOrder: 최근 주문", description = "최근 주문 정보")
public class RecentOrder {
	private int oId;
	private String userId;
	private Date orderTime;
	private String orderTable;
	private String completed;
	private String arrival_time;
	private String remain_time;
	private List<OrderDetail> recentOrderDetail;
	public RecentOrder(int oId, Date orderTime, List<OrderDetail> recentOrderDetail) {
		super();
		this.oId = oId;
		this.orderTime = orderTime;
		this.recentOrderDetail = recentOrderDetail;
	}
	
	
	public RecentOrder(int oId, Date orderTime, String orderTable, String completed,
			List<OrderDetail> recentOrderDetail) {
		super();
		this.oId = oId;
		this.orderTime = orderTime;
		this.orderTable = orderTable;
		this.completed = completed;
		this.recentOrderDetail = recentOrderDetail;
	}

	

	public RecentOrder(int oId, String userId, Date orderTime, String orderTable, String completed, String arrival_time,
			String remain_time, List<OrderDetail> recentOrderDetail) {
		super();
		this.oId = oId;
		this.userId = userId;
		this.orderTime = orderTime;
		this.orderTable = orderTable;
		this.completed = completed;
		this.arrival_time = arrival_time;
		this.remain_time = remain_time;
		this.recentOrderDetail = recentOrderDetail;
	}


	public int getoId() {
		return oId;
	}


	public void setoId(int oId) {
		this.oId = oId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Date getOrderTime() {
		return orderTime;
	}


	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}


	public String getOrderTable() {
		return orderTable;
	}


	public void setOrderTable(String orderTable) {
		this.orderTable = orderTable;
	}


	public String getCompleted() {
		return completed;
	}


	public void setCompleted(String completed) {
		this.completed = completed;
	}


	public String getArrival_time() {
		return arrival_time;
	}


	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}


	public String getRemain_time() {
		return remain_time;
	}


	public void setRemain_time(String remain_time) {
		this.remain_time = remain_time;
	}


	public List<OrderDetail> getRecentOrderDetail() {
		return recentOrderDetail;
	}


	public void setRecentOrderDetail(List<OrderDetail> recentOrderDetail) {
		this.recentOrderDetail = recentOrderDetail;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RecentOrder [oId=");
		builder.append(oId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", orderTime=");
		builder.append(orderTime);
		builder.append(", orderTable=");
		builder.append(orderTable);
		builder.append(", completed=");
		builder.append(completed);
		builder.append(", arrival_time=");
		builder.append(arrival_time);
		builder.append(", remain_time=");
		builder.append(remain_time);
		builder.append(", recentOrderDetail=");
		builder.append(recentOrderDetail);
		builder.append("]");
		return builder.toString();
	}

	

}
