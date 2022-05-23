package com.ssafy.through.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.through.model.dto.Comment;
import com.ssafy.through.model.repo.CommentRepo;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo repo;

	@Transactional
	@Override
	public int insert(Comment comment) {
		return repo.insert(comment);
	}

	@Override
	public List<Comment> search(Integer product_id) {
		System.out.println("service" + product_id+ repo.search(product_id).toString());
		return repo.search(product_id);
	}

	@Override
	public int update(Comment comment) {
		return repo.update(comment);
	}

	@Override
	public int delete(Integer id) {
		return repo.delete(id);
	}

	@Override
	public Comment select(Integer id) {
		return repo.select(id);
	}

	@Override
	public int deleteCommentItem(Comment comment) {
		return repo.deleteCommentItem(comment);
	}
	
	

}
