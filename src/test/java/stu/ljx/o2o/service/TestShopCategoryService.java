package stu.ljx.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.ShopCategory;

public class TestShopCategoryService extends BaseTest {

	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@Test
	public void testGetShopCategoryList() {
		List<ShopCategory> shopCategoryList = null;
		ShopCategory shopCategory = new ShopCategory();
		//shopCategoryCnd!=null--> 查询所有一级商铺类别
//		shopCategoryList = shopCategoryService.getShopCategoryList(shopCategory);
//		assertEquals(2, shopCategoryList.size());
//		for (ShopCategory category : shopCategoryList) {
//			System.out.println(category);
//		}
		
		//shopCategoryCnd!=null && parent!=null-->查询所有二级商铺类别
//		ShopCategory parent = new ShopCategory();
//		shopCategory.setParent(parent);
//		shopCategoryList = shopCategoryService.getShopCategoryList(shopCategory);
//		assertEquals(3, shopCategoryList.size());
//		for (ShopCategory category : shopCategoryList) {
//			System.out.println(category);
//		}
		
		//shopCategoryCnd!=null && parent!=null && parent.shopCategoryId != null-->查询指定一级商铺类别下的所有二级商铺类别
		ShopCategory parent = new ShopCategory();
		parent.setShopCategoryId(1);
		shopCategory.setParent(parent);
		shopCategoryList = shopCategoryService.getShopCategoryList(shopCategory);
		assertEquals(2, shopCategoryList.size());
		for (ShopCategory category : shopCategoryList) {
			System.out.println(category);
		}
		
//		shopCategoryList = shopCategoryService.getShopCategoryList(null);
//		assertEquals(5, shopCategoryList.size());
//		for (ShopCategory category : shopCategoryList) {
//			System.out.println(category);
//		}
	}
	
}
