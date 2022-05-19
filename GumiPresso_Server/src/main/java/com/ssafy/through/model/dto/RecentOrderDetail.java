package com.ssafy.through.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value ="RecentOrderDetail: 최근 주문 정보",description = "최근 주문 상세정보")
public class RecentOrderDetail {
	@ApiModelProperty(value="상품아이디")
	private int product_id;
	@ApiModelProperty(value="주문수량")
	private int quantity;
	@ApiModelProperty(value="상품 이름")
	private String name;
	@ApiModelProperty(value="상품 타입")
	private String type;
	@ApiModelProperty(value="상품 가격")
	private int price;
	@ApiModelProperty(value="상품 이미지")
	private String img;
	public RecentOrderDetail(int product_id, int quantity, String name, String type, int price, String img) {
		super();
		this.product_id = product_id;
		this.quantity = quantity;
		this.name = name;
		this.type = type;
		this.price = price;
		this.img = img;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
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
		StringBuilder builder = new StringBuilder();
		builder.append("RecentOrderDetail [product_id=");
		builder.append(product_id);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", price=");
		builder.append(price);
		builder.append(", img=");
		builder.append(img);
		builder.append("]");
		return builder.toString();
	}	
	
	
	
}
