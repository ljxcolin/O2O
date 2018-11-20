package stu.ljx.o2o.util.wechat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 微信校验工具类
 * @author Lijinxuan
 *
 */
public class SignUtil {
	
	//Token，与接口配置一致
	private static String token = "ljxo2o";
	
	/**
	 * 校验微信签名
	 * @return
	 */
	public static boolean verifySignature(String signature, String timestamp, String nonce) {
		//将参数装配成数组
		String[] params = new String[] {token, timestamp, nonce};
		//将token,timestamp,nonce进行字典排序
		Arrays.sort(params);
		StringBuilder concater = new StringBuilder();
		//将排序好的三个参数进行拼接
		for (int i=0; i<params.length; i++) {
			concater.append(params[i]);
		}
		
		MessageDigest md = null;
		String result = null;
		try {
			//将拼接好的字符串进行SHA-1加密
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(concater.toString().getBytes());
			result = byteArrToHexStr(digest);
			concater = null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//将sha-1加密后的字符串与signature对比，标识该请求是否来自微信
		return result != null ? result.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转为十六进制字符串
	 * @param bytes
	 * @return
	 */
	private static String byteArrToHexStr(byte[] bytes) {
		StringBuffer sbuf = new StringBuffer();
		for(int i=0; i<bytes.length; i++) {
			sbuf.append(byteToHexStr(bytes[i]));
		}
		return sbuf.toString();
	}

	/**
	 * 将字节转为十六进制字符串
	 * @param bt
	 * @return
	 */
	private static String byteToHexStr(byte bt) {
		char[] bases = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char[] temp = new char[2];
		temp[0] = bases[(bt >>> 4) & 0X0F];
		temp[1] = bases[bt & 0X0F];
		return new String(temp);
	}
	
}
