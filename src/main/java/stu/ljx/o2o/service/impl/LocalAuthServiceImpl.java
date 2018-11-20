package stu.ljx.o2o.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import stu.ljx.o2o.dao.LocalAuthMapper;
import stu.ljx.o2o.dao.UserMapper;
import stu.ljx.o2o.dto.ImageHolder;
import stu.ljx.o2o.entity.LocalAuth;
import stu.ljx.o2o.entity.User;
import stu.ljx.o2o.exception.UserException;
import stu.ljx.o2o.service.LocalAuthService;
import stu.ljx.o2o.util.FileUtil;
import stu.ljx.o2o.util.ImageUtil;
import stu.ljx.o2o.util.MD5Util;

@Service
public class LocalAuthServiceImpl implements LocalAuthService{

	private static final Logger logger = LoggerFactory.getLogger(LocalAuthServiceImpl.class);
	
	@Autowired
	private LocalAuthMapper localAuthMapper;
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public LocalAuth getUser(String userName, String password) {
		return localAuthMapper.queryUserByNameAndPwd(userName, password);
	}

	@Override
	public LocalAuth getUser(String userName) {
		if(userName != null && !userName.equals("")) {
			return localAuthMapper.queryUserByName(userName);
		}
		logger.error("getUser error: {}", "用户名为空");
		return null;
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public int addUser(User user, LocalAuth localAuth, ImageHolder profileImg) {
		if(user == null || localAuth == null) {
			logger.error("addUser error: {}", "用户不存在");
			return -1;
		}
		LocalAuth tmpAuth = localAuthMapper.queryUserByName(localAuth.getUserName());
		if(tmpAuth != null) {
			throw new UserException("该用户名已存在");
		}
		//设置一些信息
		Date addTime = new Date();
		user.setCreateTime(addTime);
		user.setLastEditTime(addTime);
		user.setEnableStatus(0);
		//处理用户头像
		if(profileImg != null) {
			addProfileImg(user, localAuth, profileImg);
		}
		
		int row = 0;
		try {
			row = userMapper.insertUser(user);
			if(row > 0) {
				if(user.getUserId() != null && user.getUserId() > 0) {
					localAuth.setUser(user);
					localAuth.setCreateTime(addTime);
					localAuth.setLastEditTime(addTime);
					//将密码加密
					localAuth.setPassword(MD5Util.getMd5(localAuth.getPassword()));
					row = localAuthMapper.insertUser(localAuth);
				}
			}
		} catch (Exception e) {
			logger.error("addUser error: {}", e.toString());
			throw new UserException("注册失败");
		}
		return row;
	}

	//如果用户上传了头像，则上传处理
	private void addProfileImg(User user, LocalAuth localAuth, ImageHolder profileImg) {
		String userImgPath = FileUtil.getUserImgPath(localAuth.getUserName());
		String relativePath = ImageUtil.generateRawImg(profileImg, userImgPath);
		user.setProfileImg(relativePath);
	}

}
