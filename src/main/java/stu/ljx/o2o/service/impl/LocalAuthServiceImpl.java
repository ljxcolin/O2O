package stu.ljx.o2o.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu.ljx.o2o.dao.LocalAuthMapper;
import stu.ljx.o2o.entity.LocalAuth;
import stu.ljx.o2o.service.LocalAuthService;

@Service
public class LocalAuthServiceImpl implements LocalAuthService{

	@Autowired
	private LocalAuthMapper localAuthMapper;
	
	@Override
	public LocalAuth getUser(String userName, String password) {
		return localAuthMapper.queryUserByNameAndPwd(userName, password);
	}

}
