package com.ssafy.through.model.repo;

import java.util.List;

import com.ssafy.through.model.dto.Comment;

public interface CommentRepo {

	int insert(Comment comment);

	List<Comment> search(Integer product_id);

	int update(Comment comment);
	
	int delete(Integer id);

	Comment select(Integer id);

}