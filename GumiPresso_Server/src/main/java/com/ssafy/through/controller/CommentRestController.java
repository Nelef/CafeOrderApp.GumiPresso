package com.ssafy.through.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.through.model.dto.Comment;
import com.ssafy.through.model.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(allowCredentials = "true", originPatterns = { "*" })
@RestController
@RequestMapping("/comment")
@Api(value = "Comment")
public class CommentRestController {
	
	@Autowired
	private CommentService cService;
	
	@ApiOperation(value="{product_id}에 해당하는 게시물 정보를 반환한다.", response = Comment.class)
	@GetMapping("/{product_id}")
	public ResponseEntity<?> selectComments(@PathVariable("product_id") Integer product_id) {
	
		List<Comment> comments = cService.search(product_id);		
		if(comments != null) {
			return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="comment를 저장한다", response = Comment.class)
	@PostMapping("/")
	public ResponseEntity<?> insertComment(@RequestBody Comment comment)
	{		
		int result = cService.insert(comment);
		if(result > 0) {
			List<Comment> comments = cService.search(comment.getProductId());			
			return new ResponseEntity<List<Comment>>(comments, HttpStatus.CREATED);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="{id}에 해당하는 comment를 수정한다", response = Comment.class)
	@PutMapping("/")
	public ResponseEntity<?> updateComment(@RequestBody Comment comment){		
		int result = cService.update(comment);
		if(result > 0) {
			List<Comment> comments = cService.search(comment.getProductId());
			return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="{id}에 해당하는  comment를 삭제한다", response = Comment.class)
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable("id") Integer id){
		int productId = cService.select(id).getProductId();
		int result = cService.delete(id);
		if(result > 0) {
			List<Comment> comments = cService.search(productId);
			if(comments != null) {
				return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
			}
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
}
