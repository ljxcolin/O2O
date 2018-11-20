package stu.ljx.o2o.util.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import stu.ljx.o2o.dto.UserAccessToken;
import stu.ljx.o2o.dto.WechatUser;

/**
 * 微信工具类，主要用来提交https请求给微信获取用户信息
 * https=http+ssl，URL参数传输是加密的
 * @author Lijinxuan
 *
 */
public class WechatUtil {
	
	private static Logger log = LoggerFactory.getLogger(WechatUtil.class);
	
	/**
	 * 获取UserAccessToken实体类
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public static UserAccessToken getUserAccessToken(String code) throws IOException {
		//测试号信息里的appId
		String appId = "wx0040043275994af1";
		log.debug("appId: {}", appId);
		//测试号信息里的appsecret
		String appsecret = "2dcc9cdeb0ddcf14761bd1785bce1831";
		log.debug("appsecret: {}", appsecret);
		//根据传入的code，拼接出访问微信定义好的接口的URL
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
		//向相应URL发送请求获取token（json字符串）
		String tokenStr = httpsRequest(url, "GET", null);
		log.debug("userAccessToken: {}", tokenStr);
		UserAccessToken token = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			//将json字符串转换成相应对象
			token = mapper.readValue(tokenStr, UserAccessToken.class);
		} catch (JsonParseException | JsonMappingException e) {
			log.error("getUserAccessToken error: {}", e.getMessage());
			e.printStackTrace();
		}
		if (token == null) {
			log.error("getUserAccessToken failed.");
		}
		return token;
	}
	
	/**
	 * 获取WechatUser实体类
	 * @param accessToken
	 * @param openId
	 * @return
	 * @throws IOException 
	 */
	public static WechatUser getUserInfo(String accessToken, String openId) throws IOException {
		//根据传入的accessToken以及openId拼接出访问微信定义的端口并获取用户信息的URL
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
		//访问该URL获取用户信息（json字符串）
		String userStr = httpsRequest(url, "GET", null);
		log.debug("user info: {}", userStr);
		WechatUser user = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			//将json字符串转换成相应对象
			user = objectMapper.readValue(userStr, WechatUser.class);
		} catch (JsonParseException | JsonMappingException e) {
			log.error("getUserInfo error: {}", e.getMessage());
			e.printStackTrace();
		}
		if (user == null) {
			log.error("getUserInfo failed");
		}
		return user;
	}
	
	/**
	 * 发起https请求并获取结果
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return json字符串
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			//创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			//从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			URL url = new URL(requestUrl); //将请求封装成URL
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			//设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			
			if (requestMethod.equalsIgnoreCase("GET")) {
				httpUrlConn.connect();
			}
			
			//当有数据需要提交时
			if (outputStr != null) {
				OutputStream outs = httpUrlConn.getOutputStream();
				//注意编码格式，防止中文乱码
				outs.write(outputStr.getBytes("UTF-8"));
				outs.close();
			}
			
			//将返回的输入流转换成字符串
			InputStream ins = httpUrlConn.getInputStream();
			InputStreamReader insReader = new InputStreamReader(ins, "UTF-8");
			BufferedReader bufReader = new BufferedReader(insReader);
			
			String str = null;
			while ((str = bufReader.readLine()) != null) {
				buffer.append(str);
			}
			bufReader.close();
			insReader.close();
			//释放资源
			ins.close();
			ins = null;
			httpUrlConn.disconnect();
			log.debug("https buffer:" + buffer.toString());
		} catch (ConnectException ce) {
			log.error("weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error: {}", e.toString());
		}
		return buffer.toString();
	}

}
