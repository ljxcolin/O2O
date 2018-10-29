package stu.ljx.o2o.service;

import java.io.InputStream;

import stu.ljx.o2o.dto.ShopExecution;
import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.exception.ShopException;

public interface ShopService {

	ShopExecution addShop(Shop shop, InputStream shopImgIns, String shopImgName);
	
	Shop getShopById(Integer shopId);
	
	ShopExecution modifyShop(Shop shop, InputStream shopImgIns, String newShopImg, String oldShopImg) throws ShopException;
	
	ShopExecution getShopList(Shop shopCnd, int pageIndex, int pageSize);
	
}
