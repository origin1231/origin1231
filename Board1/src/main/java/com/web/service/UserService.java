package com.web.service;

import com.web.domain.UserVO;
import com.web.dto.LoginDTO;

public interface UserService {
	
	public UserVO login(LoginDTO dto) throws Exception;
}
