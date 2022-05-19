package com.ssafy.through.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.through.model.dto.User;
import com.ssafy.through.model.repo.AdminRepo;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminRepo repo;
	
	@Override
	public User loginAdmin(User user) {
		return repo.loginAdmin(user);
	}

	@Override
	public User getAdminUser(String id) {
		return repo.getAdminUser(id);
	}

	@Override
	public int checkId(String id) {
		return repo.checkId(id);
	}

	@Transactional
	@Override
	public int insertAdmin(User user) { 
		return repo.insertAdmin(user);
	}
}
