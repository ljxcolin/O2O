package stu.ljx.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import stu.ljx.o2o.dto.ShopExecution;
import stu.ljx.o2o.entity.Area;
import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.entity.ShopCategory;
import stu.ljx.o2o.service.AreaService;
import stu.ljx.o2o.service.ShopCategoryService;
import stu.ljx.o2o.service.ShopService;
import stu.ljx.o2o.util.HttpSvlReqUtil;

/**
 * 前端商铺列表控制器
 * 	1、用户点击全部商铺时，加载全部一级商铺类别和所属区域以及全部商铺
 * 	2、用户点击特定类别的商铺时，加载全部一级商铺类别和所属区域以及全部该类别的商铺
 * 	3、支持条件筛选和分页
 * @author Lijinxuan
 *
 */
@Controller
@RequestMapping("/frontend")
public class ShopListController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 加载所有商铺类别和商铺区域
     * @param request
     * @return
     */
    @RequestMapping(value="/initsearchcnd", method=RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> initSearchCnd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Integer parentId = HttpSvlReqUtil.getInteger(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        List<Area> areaList = null;
        try {
	        //parentId不为空，查询出对应parentId目录下的全部商品目录
	        if (parentId != -1) {
	        	ShopCategory childCategory = new ShopCategory();
	        	ShopCategory parentCategory = new ShopCategory();
	        	parentCategory.setShopCategoryId(parentId);
	        	childCategory.setParent(parentCategory);
	        	shopCategoryList = shopCategoryService.getShopCategoryList(childCategory);
	        } else {
	            //parentId为空，查询出一级的shopCategory
	        	shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
	        }
	        areaList = areaService.getAreaList();
	        modelMap.put("success", true);
	        modelMap.put("shopCategoryList", shopCategoryList);
	        modelMap.put("areaList", areaList);
        } catch (Exception e) {
        	modelMap.put("success", false);
        	modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    @RequestMapping(value = "/listshops", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
	        int pageIndex = HttpSvlReqUtil.getInteger(request, "pageIndex");
	        int pageSize = HttpSvlReqUtil.getInteger(request, "pageSize");
	        if ((pageIndex > -1) && (pageSize > -1)) {
	        	String shopName = HttpSvlReqUtil.getString(request, "shopName");
	            Integer parentId = HttpSvlReqUtil.getInteger(request, "parentId");
	            Integer shopCategoryId = HttpSvlReqUtil.getInteger(request, "shopCategoryId");
	            Integer areaId = HttpSvlReqUtil.getInteger(request, "areaId");
	            //封装查询条件
	            Shop shopCnd = combineShopCnd(shopName, parentId, shopCategoryId, areaId);
	            //调用service层提供的方法
	            ShopExecution se = shopService.getShopList(shopCnd, pageIndex, pageSize);
	            modelMap.put("success", true);
	            modelMap.put("shopList", se.getShopList());
	            modelMap.put("count", se.getCount());
	        } else {
	            modelMap.put("success", false);
	            modelMap.put("errMsg", "分页异常");
	        }
        } catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
            modelMap.put("errMsg", "系统异常");
		}
        return modelMap;
    }
    
    private Shop combineShopCnd(String shopName, Integer parentId, Integer shopCategoryId, Integer areaId) {
        Shop shopCnd = new Shop();
        if (shopName != null) {
        	shopCnd.setShopName(shopName);
        }
        if (parentId != -1) {
            ShopCategory childCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCnd.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCnd.setShopCategory(shopCategory);
        }
        if (areaId != -1) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCnd.setArea(area);
        }      
        //查询状态为审核通过的商铺
        shopCnd.setEnableStatus(1);
        return shopCnd;
    }

}
