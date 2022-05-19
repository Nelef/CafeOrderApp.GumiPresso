package com.ssafy.through.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "OrderForm : 주문 정보", description = "주문 상세 정보")
public class OrderForm {
	@ApiModelProperty(value="주문자 아이디")
	private String userId;
	@ApiModelProperty(value="주문아이디")
	private Integer orderId;
	@ApiModelProperty(value="상품아이디")
	private Integer productId;
	@ApiModelProperty(value="주문 수량")
	private Integer quantity;

	public OrderForm() {
	}

	public OrderForm(String userId, Integer orderId, Integer productId, Integer quantity) {
		super();
		this.userId = userId;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderForm [userId=" + userId + ", orderId=" + orderId + ", productId=" + productId + ", quantity="
				+ quantity + "]";
	}

}
