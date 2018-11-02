package stu.ljx.o2o.util;

import javax.servlet.http.HttpServletRequest;

import stu.ljx.o2o.entity.Shop;

public class SessionUtil {
	
	public static Shop getCurrentShop(HttpServletRequest request) {
		return (Shop)request.getSession().getAttribute("currentShop");
	}
	
}
