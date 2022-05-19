package com.ssafy.through.model.repo;

import com.ssafy.through.model.dto.AosOrderForm;
import com.ssafy.through.model.dto.User;

public interface UserRepo {
	
	User select(String id);

	int insert(User user);
	
	int update(User user);
	
	int selectId(String user_id);

	int orderUseOrderForm(AosOrderForm orderForm);
	
	int insertKakaoUser(User user);
}
