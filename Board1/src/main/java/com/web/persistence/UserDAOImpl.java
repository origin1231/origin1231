package com.web.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.web.domain.UserVO;
import com.web.dto.LoginDTO;

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "com.web.mappers.UserMapper";
	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		
		return session.selectOne(namespace+".login",dto);
	}
}
