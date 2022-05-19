package com.ssafy.through.model.dto;

import java.util.Date;
import java.util.List;

public class AosOrderForm {
	private Order order;
	private List<AosOrderDetail> aosOrderList;
	public AosOrderForm(Order order, List<AosOrderDetail> aosOrderList) {
		super();
		this.order = order;
		this.aosOrderList = aosOrderList;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public List<AosOrderDetail> getAosOrderList() {
		return aosOrderList;
	}
	public void setAosOrderList(List<AosOrderDetail> aosOrderList) {
		this.aosOrderList = aosOrderList;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AosOrderForm [order=");
		builder.append(order);
		builder.append(", aosOrderList=");
		builder.append(aosOrderList);
		builder.append("]");
		return builder.toString();
	}

	
}
