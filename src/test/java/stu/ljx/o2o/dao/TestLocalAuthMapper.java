package stu.ljx.o2o.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.LocalAuth;
import stu.ljx.o2o.util.MD5Util;

public class TestLocalAuthMapper extends BaseTest {

	@Autowired
	private LocalAuthMapper localAuthMapper;
	
	@Test
	public void testQueryUserByNameAndPwd() {
		LocalAuth localAuth = localAuthMapper.queryUserByNameAndPwd("ljxcolin", MD5Util.getMd5("ljx456"));
		System.out.println(localAuth);
	}
	
	@Test
	public void testQueryUserByName() {
		LocalAuth localAuth = localAuthMapper.queryUserByName("tongsiqi");
		assertNotNull(localAuth);
		System.out.println(localAuth);
	}
	
}
