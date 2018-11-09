package stu.ljx.o2o.web.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 控制商品模块静态页面的访问
 * @author Lijinxuan
 *
 */
@Controller
@RequestMapping("/productadmin")
public class ProductAdminController {
	
	@RequestMapping(value="/management", method=RequestMethod.GET)
	public String management() {
		return "product/productmanagement";
	}
	
	@RequestMapping(value="/operation", method=RequestMethod.GET)
	public String operation() {
		return "product/productoperation";
	}
	
	@RequestMapping(value="/category/management", method=RequestMethod.GET)
	public String pcManagement() {
		return "product/pcmanagement";
	}
	
}
