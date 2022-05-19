package com.ssafy.through.model.service;

import java.util.Map;

public interface KakaoLoginService {
	Map<String, Object> getUserInfo(String access_Token);
}
