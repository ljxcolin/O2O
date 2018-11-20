package stu.ljx.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesResolver extends PropertyPlaceholderConfigurer {
	
	//需要加密的字段数组
    private String[] encryptPropNames = {"jdbc.user", "jdbc.password", "redis.password"};

    /**
     * 对密文属性转换为明文属性
     * @param propertyName
     * @param propertyValue
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
    	//判断是否是加密属性
    	if (isEncryptProp(propertyName)) { //是，解码后返回
            //解密
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        } else {
            return propertyValue; //不是返回原始值
        }
    }

    /**
     * 判断该属性是否是加密属性
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp(String propertyName) {
        for (String encryptpropertyName : encryptPropNames) {
            if (encryptpropertyName.equals(propertyName)) {
            	return true;
            }
        }
        return false;
    }

}
