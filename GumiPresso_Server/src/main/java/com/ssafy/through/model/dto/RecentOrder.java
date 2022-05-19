package com.ssafy.through.model.dto;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "RecentOrder: 최근 주문", description = "최근 주문 정보")
public class RecentOrder {
	private int oId;
	private Date orderTime;
	private List<OrderDetail> recentOrderDetail;
	public RecentOrder(int oId, Date orderTime, List<OrderDetail> recentOrderDetail) {
		super();
		this.oId = oId;
		this.orderTime = orderTime;
		this.recentOrderDetail = recentOrderDetail;
	}
	public int getoId() {
		return oId;
	}
	public void setoId(int oId) {
		this.oId = oId;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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
		builder.append(", orderTime=");
		builder.append(orderTime);
		builder.append(", recentOrderDetail=");
		builder.append(recentOrderDetail);
		builder.append("]");
		return builder.toString();
	}
	

	
	

}
