package stu.ljx.o2o.service;

import java.util.List;

import stu.ljx.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	
	public static final String SCLISTKEY = "shopcategory";
	
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCnd);
	
}
