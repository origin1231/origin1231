package com.web.service;

import java.util.Date;

import com.web.domain.UserVO;
import com.web.dto.LoginDTO;

public interface UserService {
	
	public UserVO login(LoginDTO dto) throws Exception;
	
	public void keepLogin(String uid, String sessionId, Date next) throws Exception;
	
	public UserVO checkLoginBefore(String value);
}
