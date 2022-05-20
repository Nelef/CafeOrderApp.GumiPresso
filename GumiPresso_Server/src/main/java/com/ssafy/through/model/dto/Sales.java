package com.ssafy.through.model.dto;

import java.util.Date;

public class Sales {
	private String orderdate;
	private int total;
	private String type;
	public Sales() {
		super();
	}
	public Sales(String orderdate, int total) {
		super();
		this.orderdate = orderdate;
		this.total = total;
	}
	public Sales(String orderdate, int total, String type) {
		super();
		this.orderdate = orderdate;
		this.total = total;
		this.type = type;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sales [orderdate=");
		builder.append(orderdate);
		builder.append(", total=");
		builder.append(total);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
	
}
