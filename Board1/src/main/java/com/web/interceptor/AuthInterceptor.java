package com.web.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.web.domain.UserVO;
import com.web.service.UserService;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Inject
	private UserService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		
		if(session.getAttribute("login") == null) {
			
			logger.info("current user is not logined");
			
			saveDest(request);
			
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			// 현재 사용자가 HttpSession에 적당한 값이 없는 경우 loginCookie를 가지고 있는지 체크
			if(loginCookie != null) {
				// 사용자의 정보가 존재하는지 확인
				UserVO userVO = service.checkLoginBefore(loginCookie.getValue());
				
				logger.info("USERVO: " + userVO);
				
				if(userVO != null) {
					session.setAttribute("login", userVO);
					return true;
				}
			}
			
			response.sendRedirect("/user/login");
			return false;
		}
		System.out.println("pre=========== true");
		return true;
	}
	
	private void saveDest(HttpServletRequest req) {
		
		String uri = req.getRequestURI();
		
		String query = req.getQueryString();
		
		if(query == null || query.equals("null")) {
			query = "";
		}else {
			query = "?" + query;
		}
		
		if(req.getMethod().equals("GET")) {
			logger.info("dest: " +(uri + query));
			req.getSession().setAttribute("dest", uri + query);
		}
	}
}
