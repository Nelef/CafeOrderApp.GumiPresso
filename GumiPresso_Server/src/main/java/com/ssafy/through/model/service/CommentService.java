package com.ssafy.through.model.service;

import java.util.List;

import com.ssafy.through.model.dto.Comment;

public interface CommentService {

	int insert(Comment comment);

	List<Comment> search(Integer product_id);

	int update(Comment comment);
	
	int delete(Integer id);
	
	Comment select(Integer id);

	int deleteCommentItem(Comment comment);
}
