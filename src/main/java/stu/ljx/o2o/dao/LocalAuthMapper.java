package stu.ljx.o2o.dao;

import org.apache.ibatis.annotations.Param;

import stu.ljx.o2o.entity.LocalAuth;

public interface LocalAuthMapper {

	/**
	 * 根据用户名与密码查询用户，用于登录
	 * @param userName
	 * @param password
	 * @return
	 */
	LocalAuth queryUserByNameAndPwd(@Param("userName")String userName, @Param("password")String password);
	
	/**
	 * 根据用户名查询用户，用于注册时校验用户是否已存在
	 * @param userName
	 * @param password
	 * @return
	 */
	LocalAuth queryUserByName(String userName);
	
}
