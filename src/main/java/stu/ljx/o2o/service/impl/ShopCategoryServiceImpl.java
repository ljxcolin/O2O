package stu.ljx.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu.ljx.o2o.dao.ShopCategoryMapper;
import stu.ljx.o2o.entity.ShopCategory;
import stu.ljx.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

	@Autowired
	private ShopCategoryMapper shopCategoryMapper;
	
	/*获取所有商铺类别*/
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategory) {
		return shopCategoryMapper.queryShopCategory(shopCategory);
	}

}
