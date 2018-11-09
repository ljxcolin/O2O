package stu.ljx.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.ShopCategory;

public class TestShopCategoryMapper extends BaseTest{

	@Autowired
	private ShopCategoryMapper shopCategoryMapper;
	
	@Test
	public void testQueryShopCategory() {
		/*查询shopCategoryCnd!=null && shopCategoryCnd.parent==null的情况下的
		 * parent_id is not null */
		//即查询所有二级类别
		ShopCategory shopCategory = new ShopCategory();
		List<ShopCategory> shopCategoryList = shopCategoryMapper.queryShopCategory(shopCategory);
		assertEquals(3, shopCategoryList.size());
		for (ShopCategory category : shopCategoryList) {
			System.out.println(category);
		}
		
		/*查询shopCategoryCnd!=null && shopCategoryCnd.parent!=null的情况下的
		 parent_id is not null and parent_id = shopCategoryCnd.parent.shopCategoryId*/
		//即查询指定一级类别对应的二级类别
		ShopCategory child = new ShopCategory();
		ShopCategory parent = new ShopCategory();
		//parent.setShopCategoryId(1);
		parent.setShopCategoryId(2);
		child.setParent(parent);
		shopCategoryList = shopCategoryMapper.queryShopCategory(child);
		//assertEquals(2, shopCategoryList.size());
		assertEquals(1, shopCategoryList.size());
		for (ShopCategory category : shopCategoryList) {
			System.out.println(category);
		}
		
		//查询所有一级商铺类别parent_id = null
		shopCategoryList = shopCategoryMapper.queryShopCategory(null);
		assertEquals(2, shopCategoryList.size());
		System.out.println(shopCategoryList.get(0));
	}
	
}
