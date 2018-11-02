package stu.ljx.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.Product;
import stu.ljx.o2o.entity.ProductCategory;
import stu.ljx.o2o.entity.Shop;

public class TestProductMapper extends BaseTest {

	@Autowired
	private ProductMapper productMapper;
	
	@Test
	public void testInsertProduct() {
		Product product = new Product();
		product.setProductName("KaBuNuoQi");
		product.setProductDesc("卡布诺奇很好喝的");
		product.setImgAddr("/productImage/shopId/kabunuoqi");
		product.setNormalPrice("18.00");
		product.setPromotionPrice("12.00");
		product.setPriority(89);
		product.setCreateTime(new Date());
		product.setLastEditTime(new Date());
		product.setEnableStatus(1);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(2);
		Shop shop = new Shop();
		shop.setShopId(5);
		product.setProductCategory(pc);
		product.setShop(shop);
		int row = productMapper.insertProduct(product);
		assertEquals(1, row);
	}
	
	
}
