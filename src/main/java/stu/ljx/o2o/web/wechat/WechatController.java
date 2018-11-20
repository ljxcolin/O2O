package stu.ljx.o2o.web.wechat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stu.ljx.o2o.util.wechat.SignUtil;

/**
 * 微信请求响应Servlet
 * @author Lijinxuan
 *
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {

	private static Logger logger = LoggerFactory.getLogger(WechatController.class);
	
	@RequestMapping(method=RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("wechat getting...");
		//获取微信请求参数
		//微信加密签名
		String signature = request.getParameter("signature");
		//时间戳
		String timestamp = request.getParameter("timestamp");
		//随机数
		String nonce = request.getParameter("nonce");
		//随机字符串
		String echostr = request.getParameter("echostr");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if(SignUtil.verifySignature(signature, timestamp, nonce)) {
				logger.debug("wechat get success!");
				out.print(echostr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				out.close();
			}
		}
	}
	
}
