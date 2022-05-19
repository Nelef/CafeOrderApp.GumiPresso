package com.ssafy.through.model.dto;

public class AosOrderDetail {
	private int dId;
	private int orderId;
	private int productId;
	private int quantity;
	public AosOrderDetail(int orderId, int productId, int quantity) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}
	public AosOrderDetail(int dId, int orderId, int productId, int quantity) {
		super();
		this.dId = dId;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public AosOrderDetail() {
		super();
	}
	public AosOrderDetail(int productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	public int getdId() {
		return dId;
	}
	public void setdId(int dId) {
		this.dId = dId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AosOrderDetail [dId=");
		builder.append(dId);
		builder.append(", orderId=");
		builder.append(orderId);
		builder.append(", productId=");
		builder.append(productId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}
	
	
}
