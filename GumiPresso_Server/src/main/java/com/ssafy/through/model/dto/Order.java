package com.ssafy.through.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Order : 주문 정보", description = "주문 정보")
public class Order {
	@ApiModelProperty(value="주문아이디")
	private Integer oId;
	@ApiModelProperty(value="유저아이디")
	private String userId;
	@ApiModelProperty(value="주문테이블")
	private String orderTable;
	@ApiModelProperty(value="주문시간")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date orderTime;
	@ApiModelProperty(value="completed")
	private String completed;
	private String arrivalTime;
	private String remainTime;
	
	public Order() {
	}

	public Order(String user_id) {
		super();		
		this.userId = user_id;
	}
	

	public Order(String userId, String orderTable) {
		super();
		this.userId = userId;
		this.orderTable = orderTable;
	}

	public Order(Integer oId, String userId, String orderTable, Date orderTime, String completed) {
		super();
		this.oId = oId;
		this.userId = userId;
		this.orderTable = orderTable;
		this.orderTime = orderTime;
		this.completed = completed;
	}

	public Order(Integer oId, String userId, String orderTable, Date orderTime, String completed, String arrivalTime,
			String remainTime) {
		super();
		this.oId = oId;
		this.userId = userId;
		this.orderTable = orderTable;
		this.orderTime = orderTime;
		this.completed = completed;
		this.arrivalTime = arrivalTime;
		this.remainTime = remainTime;
	}

	public Integer getoId() {
		return oId;
	}

	public void setoId(Integer oId) {
		this.oId = oId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderTable() {
		return orderTable;
	}

	public void setOrderTable(String orderTable) {
		this.orderTable = orderTable;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [oId=");
		builder.append(oId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", orderTable=");
		builder.append(orderTable);
		builder.append(", orderTime=");
		builder.append(orderTime);
		builder.append(", completed=");
		builder.append(completed);
		builder.append(", arrivalTime=");
		builder.append(arrivalTime);
		builder.append(", remainTime=");
		builder.append(remainTime);
		builder.append("]");
		return builder.toString();
	}

		
}
