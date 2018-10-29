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
	
	@RequestMapping(value="/category/management", method=RequestMethod.GET)
	public String pcManagement() {
		return "product/pcmanagement";
	}
	
}
