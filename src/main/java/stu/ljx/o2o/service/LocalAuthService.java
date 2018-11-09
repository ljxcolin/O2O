package stu.ljx.o2o.service;

import stu.ljx.o2o.entity.LocalAuth;

public interface LocalAuthService {

	LocalAuth getUser(String userName, String password);
	
}
