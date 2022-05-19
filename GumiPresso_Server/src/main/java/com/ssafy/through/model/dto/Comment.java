package com.ssafy.through.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Comment : 코멘트 정보", description = "코멘트 정보")
public class Comment {

	@ApiModelProperty(value="아이디")
	private Integer id;
	@ApiModelProperty(value="유저아이디")
	private String userId;
	@ApiModelProperty(value="상품아이디")
	private Integer productId;
	@ApiModelProperty(value="평점")
	private Float rating;
	@ApiModelProperty(value="댓글")
	private String comment;

	public Comment() {
	}

	public Comment(Integer id, String userId, Integer productId, Float rating, String comment) {
		super();
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.rating = rating;
		this.comment = comment;
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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", userId=" + userId + ", productId=" + productId + ", rating=" + rating
				+ ", comment=" + comment + "]";
	}

	

}
