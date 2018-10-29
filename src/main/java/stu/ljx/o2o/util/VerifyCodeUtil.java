package stu.ljx.o2o.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.kaptcha.Constants;
/**
 * 验证码校验工具类
 * KAPTCHA验证码存在session域中，key为Constants.KAPTCHA_SESSION_KEY
 * @author Lijinxuan
 *
 */
public class VerifyCodeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(VerifyCodeUtil.class);

	public static boolean verifyCode(HttpServletRequest request) {
		//从session中获取验证码本身的值
		String expectedValue = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		logger.debug("verifyCodeExpected: {}", expectedValue);
		//从request中获取用户实际输入的值
		String actualValue = HttpSvlReqUtil.getString(request, "verifyCodeActual");
		logger.debug("verifyCodeActual: {}", actualValue);
		//判断验证码本身值与用户实际输入值是否匹配，匹配则返回true
		if(actualValue.equalsIgnoreCase(expectedValue)) { //此处无需非空判断，因为actualValue不会为null
			return true;
		}
		return false;
	}
	
}
