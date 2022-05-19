package com.ssafy.through.model.repo;

import com.ssafy.through.model.dto.User;

public interface AdminRepo {
	User loginAdmin(User user);
	User getAdminUser(String id);
	int checkId(String id);
	int insertAdmin(User user);
}
