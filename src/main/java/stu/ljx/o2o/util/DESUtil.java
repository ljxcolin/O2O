package stu.ljx.o2o.util;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * DES加密工具类
 * DES是一种对称加密算法，即指使用相同的密钥
 * @author Lijinxuan
 *
 */
public class DESUtil {
	
	private static Key key;
    //设置密钥key
    private static String KEY_STR = "o2oKey";
    private static String CHARSETNAME = "UTF-8";
    private static String ALGORITHM = "DES";

    static {
        try {
            //生成DES算法对象
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            //运行SHA1安全策略
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            //设置上密钥种子
            secureRandom.setSeed(KEY_STR.getBytes());
            //初始化基于SHA1的算法对象
            generator.init(secureRandom);
            //生成密钥对象
            key = generator.generateKey();
            //销毁DES算法对象
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取密文
     * @param str
     * @return
     */
    public static String getEncryptString(String str) {
        //基于BASE64编码，接收byte[]并转换为String
        //BASE64Encoder base64encoder = new BASE64Encoder();
    	Encoder base64encoder = Base64.getEncoder();
        try {
            //按UTF-8编码
            byte[] bytes = str.getBytes(CHARSETNAME);
            //获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //加密
            byte[] doFinal = cipher.doFinal(bytes);
            //byte[] to encode好的String并返回
            return base64encoder.encodeToString(doFinal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取明文
     * @param str
     * @return
     */
    public static String getDecryptString(String str) {
        //基于BASE64编码，接收byte[]并转换为String
        //BASE64Decoder base64decoder = new BASE64Decoder();
    	Decoder base64decoder = Base64.getDecoder();
        try {
            //将字符串decode成byte[]
            byte[] bytes = base64decoder.decode(str);
            //获取解密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE, key);
            //解密
            byte[] doFinal = cipher.doFinal(bytes);
            //返回解密之后的信息
            return new String(doFinal, CHARSETNAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //测试
    public static void main(String[] args) {
        System.out.println(getEncryptString("root"));
        System.out.println(getEncryptString("ljx1234"));
        System.out.println("###########################");
        //System.out.println(getDecryptString("ekgfTy/5pYk="));
        //System.out.println(getDecryptString("x7sp2OYFH04="));
    	System.out.println(getEncryptString("mine"));
    	System.out.println(getEncryptString("minEcSql&5312"));
    	System.out.println("###########################");
    	System.out.println(getEncryptString("work"));
    	System.out.println(getEncryptString("workEcSql&5312"));
    	System.out.println("###########################");
    	System.out.println(getEncryptString("ecSRedis&5312"));
    	/*ekgfTy/5pYk=
    	x7sp2OYFH04=
    	###########################
    	U0OyKTKoR9U=
    	dzsLKlW2RZU1MB5jNkD9RA==
    	###########################
    	nun/E6RgtRE=
    	orxXxELuXfxC/Brcn6rnSw==
    	###########################
    	7yzGDmHMv0M1MB5jNkD9RA==*/
    }
	
}
