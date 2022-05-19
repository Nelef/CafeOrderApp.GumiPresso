package com.ssafy.through.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.through.model.dto.AosOrderForm;
import com.ssafy.through.model.dto.User;
import com.ssafy.through.model.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo repo;	

	@Override
	public User select(String id) {
		return repo.select(id);
	}

	@Transactional
	@Override
	public int insert(User user) {
		return repo.insert(user);
	}

	@Transactional
	@Override
	public int update(User user) {
		return repo.update(user);
	}

	@Override
	public int selectId(String id) {		
		return repo.selectId(id);
	}
	

	@Override
	public int orderUseOrderForm(AosOrderForm orderForm) {
		return repo.orderUseOrderForm(orderForm);
	}

	@Override
	public int insertKakaoUser(User user) { 
		return repo.insertKakaoUser(user);
	}	

	
}
