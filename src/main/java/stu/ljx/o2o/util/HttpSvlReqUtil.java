package stu.ljx.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpSvlReqUtil {

	//String转为Integer
	public static Integer getInteger(HttpServletRequest request, String name) {
        try {
            return Integer.valueOf(request.getParameter(name));
            //return Integer.decode(request.getParameter(name));
        } catch (Exception e) {
            return -1;
        }
    }
	
	//String转为Long
	public static Long getLong(HttpServletRequest request, String name) {
		try {
			return Long.valueOf(request.getParameter(name));
		} catch (Exception e) {
			return -1L;
		}
	}
	
	//String转为Double
	public static Double getDouble(HttpServletRequest request, String name) {
		try {
			return Double.valueOf(request.getParameter(name));
		} catch (Exception e) {
			return -1D;
		}
	}
	
	//String转为Boolean
	public static Boolean getBoolean(HttpServletRequest request, String name) {
		try {
			return Boolean.valueOf(request.getParameter(name));
		} catch (Exception e) {
			return false;
		}
	}
	
	//String转为trim后的String
	public static String getString(HttpServletRequest request, String name) {
		try {
			String param = request.getParameter(name);
			if(param != null) {
				param = param.trim();
				if(param.equals("")) {
					param = null;
				}
			}
			return param;
		}catch(Exception e) {
			return null;
		}
	}

}
