package stu.ljx.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import stu.ljx.o2o.dto.HeadLineExecution;
import stu.ljx.o2o.entity.HeadLine;
import stu.ljx.o2o.entity.ShopCategory;
import stu.ljx.o2o.service.HeadLineService;
import stu.ljx.o2o.service.ShopCategoryService;

@Controller
@RequestMapping("/frontend")
public class MainController {
	
	@Autowired
	private HeadLineService headLineService;
	@Autowired
	private ShopCategoryService shopCategerService;	
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> mainPage(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		HeadLineExecution hle = null;
		List<ShopCategory> shopCategoryList = null;
		try {
			//获取所有状态可用的头条
			HeadLine headLineCnd = new HeadLine();
			headLineCnd.setEnableStatus(1);
			hle = headLineService.getHeadLineList(headLineCnd );
			//获取所有一级商铺类别，即parent_id = null
			shopCategoryList = shopCategerService.getShopCategoryList(new ShopCategory());
			modelMap.put("success", true);
			modelMap.put("headLineList", hle.getHeadLineList());
			modelMap.put("shopCategoryList", shopCategoryList);
		}catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	
	
}
