package stu.ljx.o2o.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.dto.ProductCategoryExecution;
import stu.ljx.o2o.entity.ProductCategory;

public class TestProductCategoryService extends BaseTest {
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Test
	public void testDeleteProductCategory() {
		ProductCategoryExecution productCategoryExecution = productCategoryService.deleteProductCategory(7, 5);
        Assert.assertEquals(2, productCategoryExecution.getState());
        ProductCategoryExecution productCategoryExecution2 = productCategoryService.deleteProductCategory(27, 5);
        Assert.assertEquals(-2, productCategoryExecution2.getState());
	}
	
	@Test
	public void testGetProductCategories() {
		List<ProductCategory> productCategories = productCategoryService.getProductCategories(2);
		assertNotNull(productCategories);
		assertEquals(2, productCategories.size());
		assertEquals("typeone", productCategories.get(0).getProductCategoryName());
		for (ProductCategory productCategory : productCategories) {
			System.out.println(productCategory);
		}
	}
	
	@Test
	public void testAddProductCategories() {
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryName("pc3type");
		pc1.setProductCategoryDesc("pc3shop5");
		pc1.setPriority(65);
		pc1.setCreateTime(new Date());
		pc1.setLastEditTime(new Date());
		pc1.setShopId(5);
		ProductCategory pc2 = new ProductCategory();
		pc2.setProductCategoryName("pc4type");
		pc2.setProductCategoryDesc("pc4shop5");
		pc2.setPriority(78);
		pc2.setCreateTime(new Date());
		pc2.setLastEditTime(new Date());
		pc2.setShopId(5);
		
		List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
		productCategories.add(pc1);
		productCategories.add(pc2);
		
		ProductCategoryExecution pce = productCategoryService.addProductCategories(productCategories);
		assertEquals(2, pce.getState());
		assertEquals(2, pce.getProductCategories().size());
		assertEquals(2, pce.getCount());
		
		pce = productCategoryService.addProductCategories(null);
		assertEquals(-4, pce.getState());
	}
	
}
