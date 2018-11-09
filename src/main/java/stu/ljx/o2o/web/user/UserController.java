package stu.ljx.o2o.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import stu.ljx.o2o.entity.LocalAuth;
import stu.ljx.o2o.service.LocalAuthService;
import stu.ljx.o2o.util.HttpSvlReqUtil;
import stu.ljx.o2o.util.MD5Util;
import stu.ljx.o2o.util.VerifyCodeUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private LocalAuthService localAuthService;
	
	/**
	 * 本地用户登录
	 * 	1、判断是否要校验验证码
	 * 		是，校验
	 * 	2、调用服务获取用户
	 * 	3、校验用户是否存在
	 * 		是，将用户放入session
	 * 		否，返回登录错误信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/localauth/login", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> localAuthLogin(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Boolean needVerify = HttpSvlReqUtil.getBoolean(request, "needVerify");
		//判断是否要校验验证码
		if(needVerify) {
			if(!VerifyCodeUtil.verifyCode(request)) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "验证码不正确，请重新输入");
				return modelMap;
			}
		}
		//获取用户输入的用户名与密码
		String userName = HttpSvlReqUtil.getString(request, "userName");
		String password = HttpSvlReqUtil.getString(request, "password");
		if(userName != null && password != null) {
			try {
				//由于数据库中的密码是MD5加密的，此处需先将密码加密再传入
				password = MD5Util.getMd5(password);
				LocalAuth localAuth = localAuthService.getUser(userName, password);
				if(localAuth != null) {
					request.getSession().setAttribute("user", localAuth.getUser());
					modelMap.put("success", true);
					modelMap.put("sucMsg", "登录成功");
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "用户名或密码不正确");
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "系统异常");
			}	
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码不能为空");
		}
		return modelMap;
	}

}
