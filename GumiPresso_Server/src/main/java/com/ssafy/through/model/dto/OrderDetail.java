package com.ssafy.through.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Product : 주문 상세 정보", description = "주문 상세 정보")
public class OrderDetail {
	@ApiModelProperty(value="주문상세 아이디")
	private Integer dId;
	@ApiModelProperty(value="주문아이디")
	private Integer orderId;
	@ApiModelProperty(value="아이디")
	private Integer productId;
	@ApiModelProperty(value="수량")
	private Integer quantity;
	@ApiModelProperty(value="이름")
	private String name;
	@ApiModelProperty(value="타입")
	private String type;
	@ApiModelProperty(value="가격")
	private Integer price;
	@ApiModelProperty(value="이미지")
	private String img;

	public OrderDetail() {
	}
	
	

	public OrderDetail(Integer orderId, Integer productId, Integer quantity) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}



	public OrderDetail(Integer dId, Integer orderId, Integer productId, Integer quantity, String name, String type,
			Integer price, String img) {
		super();
		this.dId = dId;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.name = name;
		this.type = type;
		this.price = price;
		this.img = img;
	}

	public Integer getdId() {
		return dId;
	}

	public void setdId(Integer dId) {
		this.dId = dId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getproductId() {
		return productId;
	}

	public void setproductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "OrderDetail [dId=" + dId + ", orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity + ", name="
				+ name + ", type=" + type + ", price=" + price + ", img=" + img + "]";
	}

	
}