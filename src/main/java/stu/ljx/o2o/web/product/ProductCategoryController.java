package stu.ljx.o2o.web.product;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import stu.ljx.o2o.dto.ProductCategoryExecution;
import stu.ljx.o2o.dto.Result;
import stu.ljx.o2o.entity.ProductCategory;
import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.enums.ProductCategoryStateEnum;
import stu.ljx.o2o.exception.ProductException;
import stu.ljx.o2o.service.ProductCategoryService;

@Controller
@RequestMapping("/product/category")
public class ProductCategoryController {
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	
	
	/**
	 * 获取指定商铺的所有商品类别
	 * 注意点：从session中获取当前商铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getpcbyshopid", method=RequestMethod.GET)
	@ResponseBody
	public Result<List<ProductCategory>> getProductCategoryByShopId(HttpServletRequest request) {
        List<ProductCategory> productCategories = null;
        ProductCategoryStateEnum pcse = null;
		try {
			//从session中获取当前商铺，出于安全考虑，不依赖前端传入
			Shop currentShop = getCurrentShop(request);
			if(currentShop != null && currentShop.getShopId() != null) {
				productCategories = productCategoryService.getProductCategories(currentShop.getShopId());
				return new Result<List<ProductCategory>>(true, productCategories);
			}
			pcse = ProductCategoryStateEnum.NULL_SHOP;
			return new Result<List<ProductCategory>>(false, pcse.getState(), pcse.getStateInfo());
		}catch(Exception e) {
			e.printStackTrace();
			pcse = ProductCategoryStateEnum.FAILED;
			return new Result<List<ProductCategory>>(false, pcse.getState(), pcse.getStateInfo());
		}
	}

	/**
	 * 批量新增商品类别，注意事项：见方法内注释
	 * @param productCategories
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/batchadd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchAdd(@RequestBody List<ProductCategory> productCategories, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (productCategories != null && productCategories.size() > 0) {
			//从session中获取shop的信息
			Shop currentShop = getCurrentShop(request);
			if (currentShop != null && currentShop.getShopId() != null) {
				//为商品类别设置createTime/lastEditTime/shopId
				Date createTime = new Date();
				for (ProductCategory productCategory : productCategories) {
					productCategory.setCreateTime(createTime);
					productCategory.setLastEditTime(createTime);
					productCategory.setShopId(currentShop.getShopId());
				}
				try {
					//批量插入
					ProductCategoryExecution pce = productCategoryService.addProductCategories(productCategories);
					if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
						modelMap.put("success", true);
						//同时也将新增成功的数量返回给前台
						modelMap.put("count", pce.getCount());
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", pce.getStateInfo());
					}
				} catch (ProductException e) {
					e.printStackTrace();
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", ProductCategoryStateEnum.NULL_SHOP.getStateInfo());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ProductCategoryStateEnum.EMPTY_CATEGORY_LIST.getStateInfo());
		}
		return modelMap;
	}
	
	/**
	 * 删除指定的商品类别
	 * @param productCategoryId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> removeProductCategory(Integer productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (productCategoryId != null && productCategoryId > 0) {
            //从session中获取shop的信息
            Shop currentShop = getCurrentShop(request);
            if (currentShop != null && currentShop.getShopId() != null) {
                try {
                    //删除商品类别
                    Integer shopId = currentShop.getShopId();
                    ProductCategoryExecution pce = productCategoryService.deleteProductCategory(productCategoryId, shopId);
                    if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                        modelMap.put("success", true);
                    } else {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", pce.getStateInfo());
                    }
                } catch (ProductException e) {
                    e.printStackTrace();
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.getMessage());
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", ProductCategoryStateEnum.NULL_SHOP.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请选择商品类别");
        }
        return modelMap;
    }

	private Shop getCurrentShop(HttpServletRequest request) {
		return (Shop)request.getSession().getAttribute("currentShop");
	}
	
}
