package com.web.persistence;

import com.web.domain.UserVO;
import com.web.dto.LoginDTO;

public interface UserDAO {
	public UserVO login(LoginDTO dto) throws Exception;
}
