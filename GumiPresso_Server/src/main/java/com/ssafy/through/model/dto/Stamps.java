package com.ssafy.through.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Stamp : 스탬프 정보", description = "스탬프 상세 정보")
public class Stamps {
	@ApiModelProperty(value="아이디")
	private Integer id;
	@ApiModelProperty(value="유저아이디")
	private String userId;
	@ApiModelProperty(value="주문번호")
	private Integer orderId;
	@ApiModelProperty(value="수량")
	private Integer quantity;

	public Stamps() {
	}
	
	

	public Stamps(String userId, Integer orderId, Integer quantity) {
		super();
		this.userId = userId;
		this.orderId = orderId;
		this.quantity = quantity;
	}



	public Stamps(Integer id, String userId, Integer orderId, Integer quantity) {
		super();
		this.id = id;
		this.userId = userId;
		this.orderId = orderId;
		this.quantity = quantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Stamps [id=" + id + ", userId=" + userId + ", orderId=" + orderId + ", quantity=" + quantity + "]";
	}

}
