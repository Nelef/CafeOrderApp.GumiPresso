package com.ssafy.through.model.service;

import com.ssafy.through.model.dto.User;

public interface AdminService {
	User loginAdmin(User user);
	User getAdminUser(String id);
	int checkId(String id);
	int insertAdmin(User user);
}
