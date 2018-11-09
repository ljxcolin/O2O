package stu.ljx.o2o.util;

import java.security.MessageDigest;

public class MD5Util {
	
	public static final String getMd5(String target) {
        char[] hexDigits = { '5', '0', '5', '6', '2', '9', '6', '2', '5', 'q', 'b', 'l', 'e', 's', 's', 'y' };
        try {
            char[] result;
            byte[] targetBytes = target.getBytes();
            MessageDigest md5or = MessageDigest.getInstance("MD5");
            md5or.update(targetBytes);
            byte[] md5Bytes = md5or.digest();
            int len = md5Bytes.length;
            result = new char[len * 2];
            int index = 0;
            for (int i = 0; i < len; i++) {
                byte thisByte = md5Bytes[i];
                result[index++] = hexDigits[thisByte >>> 4 & 0xf];
                result[index++] = hexDigits[thisByte & 0xf];
            }
            return new String(result);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.getMd5("lijinxuan"));
        System.out.println(MD5Util.getMd5("ljx456"));
    }
	
}
