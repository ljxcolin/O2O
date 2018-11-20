package stu.ljx.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.LocalAuth;
import stu.ljx.o2o.entity.User;
import stu.ljx.o2o.util.MD5Util;

public class TestUserMapper extends BaseTest {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private LocalAuthMapper localAuthMapper;
	
	@Test
	public void testInsertUser() {
		User user = new User();
		LocalAuth localAuth = new LocalAuth();
		Date date = new Date();
		user.setName("sdfh");
		user.setProfileImg("/user/xxx");
		user.setEmail("3784567726@qq.com");
		user.setGender("0");
		user.setEnableStatus(0);
		user.setUserType(1);
		user.setCreateTime(date);
		user.setLastEditTime(date);
		int row1 = userMapper.insertUser(user);
		assertEquals(1, row1);
		System.out.println("userId=" + user.getUserId());
		localAuth.setUser(user);
		localAuth.setUserName("tongsiqi");
		localAuth.setPassword(MD5Util.getMd5("tsq123"));
		localAuth.setCreateTime(date);
		localAuth.setLastEditTime(date);
		int row2 = localAuthMapper.insertUser(localAuth);
		assertEquals(1, row2);
		System.out.println("localAuthId=" + localAuth.getLocalAuthId());
	}
	
}
