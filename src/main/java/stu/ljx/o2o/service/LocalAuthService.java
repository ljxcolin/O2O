package stu.ljx.o2o.service;

import stu.ljx.o2o.dto.ImageHolder;
import stu.ljx.o2o.entity.LocalAuth;
import stu.ljx.o2o.entity.User;

public interface LocalAuthService {

	LocalAuth getUser(String userName, String password);
	
	LocalAuth getUser(String userName);
	
	int addUser(User user, LocalAuth localAuth, ImageHolder profileImg);
}
