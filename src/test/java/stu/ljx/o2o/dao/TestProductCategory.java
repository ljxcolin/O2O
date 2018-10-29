package stu.ljx.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.ProductCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProductCategory extends BaseTest {
	
	@Autowired
	private ProductCategoryMapper productCategoryMapper;
	
	@Test
	public void testA_queryProductCategory() {
		List<ProductCategory> productCategories = productCategoryMapper.queryProductCategory(3);
		assertEquals(4, productCategories.size());
		assertEquals("typeone", productCategories.get(0).getProductCategoryName());
		for (ProductCategory productCategory : productCategories) {
			System.out.println(productCategory);
		}
		
		productCategories = productCategoryMapper.queryProductCategory(2);
		assertEquals(0, productCategories.size());
	}
	
	@Test
	public void testC_batchInsertProductCategory() {
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryName("pc1type");
		pc1.setProductCategoryDesc("pc1shop1");
		pc1.setPriority(120);
		pc1.setCreateTime(new Date());
		pc1.setLastEditTime(new Date());
		pc1.setShopId(1);
		ProductCategory pc2 = new ProductCategory();
		pc2.setProductCategoryName("pc2type");
		pc2.setProductCategoryDesc("pc2shop1");
		pc2.setPriority(128);
		pc2.setCreateTime(new Date());
		pc2.setLastEditTime(new Date());
		pc2.setShopId(1);
		
		List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
		productCategories.add(pc1);
		productCategories.add(pc2);
		
		int row = productCategoryMapper.batchInsertProductCategory(productCategories);
		assertEquals(2, row);	
	}
	
	@Test
	public void testB_deleteProductCategory() {
		List<ProductCategory> productCategories = productCategoryMapper.queryProductCategory(1);
		for (ProductCategory productCategory : productCategories) {
			int row = productCategoryMapper.deleteProductCategory(productCategory.getProductCategoryId(), 1);
			assertEquals(1, row);
		}
	}
	
}
