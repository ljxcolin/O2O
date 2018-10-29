package stu.ljx.o2o.web.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 控制商铺模块静态页面的访问
 * @author Lijinxuan
 *
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {

	/*访问/WEB-INF/view/shop/operation.html*/
	@RequestMapping(value="/operation", method=RequestMethod.GET)
	public String operation() {
		return "shop/shopoperation";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list() {
		return "shop/shoplist";
	}
	
	@RequestMapping(value="/management", method=RequestMethod.GET)
	public String management() {
		return "shop/shopmanagement";
	}
	
}
