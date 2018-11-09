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

import stu.ljx.o2o.dto.ProductExecution;
import stu.ljx.o2o.entity.Product;
import stu.ljx.o2o.entity.ProductCategory;
import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.enums.ShopStateEnum;
import stu.ljx.o2o.service.ProductCategoryService;
import stu.ljx.o2o.service.ProductService;
import stu.ljx.o2o.service.ShopService;
import stu.ljx.o2o.util.HttpSvlReqUtil;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
	
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 加载该商铺及其商品类别信息
     * @param request
     * @return
     */
    @RequestMapping(value="/initshopinfo", method=RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> initShopInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Integer shopId = HttpSvlReqUtil.getInteger(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        try {
        	if (shopId != -1) {
        		shop = shopService.getShopById(shopId);
        		productCategoryList = productCategoryService.getProductCategories(shopId);
        		modelMap.put("success", true);
        		modelMap.put("shop", shop);
        		modelMap.put("productCategoryList", productCategoryList);
        	} else {
        		modelMap.put("success", false);
        		modelMap.put("errMsg", ShopStateEnum.NULL_SHOP_ID.getStateInfo());
        	}
		} catch (Exception e) {
			modelMap.put("success", false);
        	modelMap.put("errMsg", e.toString());
		}
        return modelMap;
    }

    /**
     * 加载符合条件的商品列表，支持分页功能
     * @param request
     * @return
     */
    @RequestMapping(value = "/listproducts", method=RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> listProducts(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
        	int pageIndex = HttpSvlReqUtil.getInteger(request, "pageIndex");
        	int pageSize = HttpSvlReqUtil.getInteger(request, "pageSize");
        	Integer shopId = HttpSvlReqUtil.getInteger(request, "shopId");
        	if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
        		String productName = HttpSvlReqUtil.getString(request, "productName");
        		Integer productCategoryId = HttpSvlReqUtil.getInteger(request, "productCategoryId");
        		Product productCnd = combineProductCnd(productName, shopId, productCategoryId);
        		ProductExecution pe = productService.getProductList(productCnd, pageIndex, pageSize);
        		modelMap.put("success", true);
        		modelMap.put("productList", pe.getProductList());
        		modelMap.put("count", pe.getCount());
        	} else {
        		modelMap.put("success", false);
        		modelMap.put("errMsg", "分页异常或商铺不存在");
        	}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
            modelMap.put("errMsg", "系统异常");
		}
        return modelMap;
    }

    private Product combineProductCnd(String productName, Integer shopId, Integer productCategoryId) {
        Product productCnd = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCnd.setShop(shop);
        if (productName != null) {
        	productCnd.setProductName(productName);
        }
        if (productCategoryId != -1) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCnd.setProductCategory(productCategory);
        }
        //查询上架的商品
        productCnd.setEnableStatus(1);
        return productCnd;
    }


}
