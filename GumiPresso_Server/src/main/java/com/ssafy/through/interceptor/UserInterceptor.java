package com.ssafy.through.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class UserInterceptor implements HandlerInterceptor  {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Cookie[] cookies = request.getCookies();
		String userId = null;
		System.out.println(request.toString() +"////"+ response.toString());
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginId")) {
					userId = cookie.getValue();
					System.out.println("쿠키 정보 출력: " + cookie.getName() + " : " + userId);
					break;
				}
			}
		}
		
		if (userId == null && request.getRequestURI().startsWith("/login")) {
			response.sendError(HttpStatus.UNAUTHORIZED.value());
			return false;
		}
		
		return true;
	}
}
