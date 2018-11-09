package stu.ljx.o2o.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.LocalAuth;
import stu.ljx.o2o.util.MD5Util;

public class TestLocalAuthService extends BaseTest {
	
	@Autowired
	private LocalAuthService localAuthService;
	
	@Test
	public void testGetUser0() {
		LocalAuth localAuth = localAuthService.getUser("ljxcolin", MD5Util.getMd5("ljx456"));
		assertNotNull(localAuth);
		assertEquals("ljxcolin", localAuth.getUserName());
		System.out.println(localAuth);
	}
	
}
