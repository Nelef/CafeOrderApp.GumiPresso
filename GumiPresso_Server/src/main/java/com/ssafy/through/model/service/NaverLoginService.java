package com.ssafy.through.model.service;

import java.util.Map;

public interface NaverLoginService {
	Map<String, String> getUserInfo(String accessToken);
}
