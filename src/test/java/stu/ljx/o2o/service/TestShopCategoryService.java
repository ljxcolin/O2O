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
		ShopCategory shopCategory = new ShopCategory();
		List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(shopCategory);
		assertEquals(3, shopCategoryList.size());
		for (ShopCategory category : shopCategoryList) {
			System.out.println(category);
		}
	}
	
}
