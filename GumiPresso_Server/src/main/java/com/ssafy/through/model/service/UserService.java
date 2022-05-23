package com.ssafy.through.model.service;

import com.ssafy.through.model.dto.AosOrderForm;
import com.ssafy.through.model.dto.User;

public interface UserService {

	User select(String id);

	int insert(User user);
	
	int update(User user);
	
	int selectId(String id);
	
	int orderUseOrderForm(AosOrderForm orderForm);
	
	int insertKakaoUser(User user);
	
	int updateMoney(User user);
}
