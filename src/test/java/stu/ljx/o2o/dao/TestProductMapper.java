package stu.ljx.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	public void testUpdateProductCategoryToNull() {
		int row = productMapper.updateProductCategoryToNull(14, 1);
		assertEquals(2, row);
	}
	
	@Test
	public void testQueryProduct() {
	    List<Product> productList = new ArrayList<Product>();
	    int row = 0;

	    Shop shop = new Shop();
	    shop.setShopId(3);
	
	    Product productCnd = new Product();
	    productCnd.setShop(shop);

	    productList = productMapper.queryProduct(productCnd, 0, 1);
	    assertEquals(1, productList.size());
	    row = productMapper.countProduct(productCnd);
	    assertEquals(2, row);
	
	    ProductCategory productCategory = new ProductCategory();
	    productCategory.setProductCategoryId(2);
	
	    productCnd = new Product();
	    productCnd.setShop(shop);
	    productCnd.setProductCategory(productCategory);
	    productCnd.setProductName("喝");
	
	    productList = productMapper.queryProduct(productCnd, 0, 2);
	    assertEquals(1, productList.size());
	    row = productMapper.countProduct(productCnd);
	    assertEquals(1, row);
	}
	
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
	
	@Test
	public void testUpdateProduct() {
		Product product = productMapper.getProductById(2);
		product.setImgAddr("\\shopImage\\5\\product_2\\kabunuoqi");
		product.setNormalPrice("20.00");
		product.setPromotionPrice("13.00");
		product.setPriority(92);
		product.setLastEditTime(new Date());
		product.setEnableStatus(1);
		int row = productMapper.updateProduct(product);
		assertEquals(1, row);
	}

}
