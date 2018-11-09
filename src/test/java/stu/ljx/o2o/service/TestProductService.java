package stu.ljx.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.dto.ImageHolder;
import stu.ljx.o2o.dto.ProductExecution;
import stu.ljx.o2o.entity.Product;
import stu.ljx.o2o.entity.ProductCategory;
import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.enums.ProductStateEnum;
//import stu.ljx.o2o.util.PageUtil;

public class TestProductService extends BaseTest {

	@Autowired
	private ProductService productService;
	
	@Test
	public void testEditProduct() throws FileNotFoundException {
		Product product = productService.getProductById(6);
		product.setProductName("速溶咖啡");
		product.setNormalPrice("12.00");
		product.getProductCategory().setProductCategoryId(2);
		
		File productImg = new File("E:\\STSFile\\Resources\\images\\coffee.jpg");
		File detailImg0 = new File("E:\\STSFile\\Resources\\images\\cafei0.jpg");
		File detailImg1 = new File("E:\\STSFile\\Resources\\images\\kafei1.jpg");
		
		InputStream ins = new FileInputStream(productImg);
		InputStream ins0 = new FileInputStream(detailImg0);
		InputStream ins1 = new FileInputStream(detailImg1);
		
		ImageHolder imgHolder = new ImageHolder(ins, productImg.getName());
		ImageHolder imgHolder0 = new ImageHolder(ins0, detailImg0.getName());
		ImageHolder imgHolder1 = new ImageHolder(ins1, detailImg1.getName());
		
		Set<ImageHolder> detailImgs = new HashSet<ImageHolder>();
		detailImgs.add(imgHolder0);
		detailImgs.add(imgHolder1);
		
		ProductExecution pe = productService.editProduct(product, imgHolder, detailImgs);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}
	
	@Test
	public void testQueryAndCountProduct() {
		ProductExecution pe = null;
		Product productCnd = new Product();
		//商铺ID
	    Shop shop = new Shop();
	    shop.setShopId(3);
	    productCnd.setShop(shop);

	    /*pe = productService.getProductList(productCnd, 1, PageUtil.PAGESIZE);
	    assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	    assertEquals(2, pe.getProductList().size());
	    assertEquals(2, pe.getCount());*/
	
	    //商铺ID+商品类别
	    /*ProductCategory productCategory = new ProductCategory();
	    productCategory.setProductCategoryId(2);
	    productCnd.setProductCategory(productCategory);*/
	    
	    /*pe = productService.getProductList(productCnd, 1, PageUtil.PAGESIZE);
	    assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	    assertEquals(1, pe.getProductList().size());
	    assertEquals(1, pe.getCount());*/
	    
	    //商铺ID+商品状态
	    /*productCnd.setEnableStatus(0);  
	    pe = productService.getProductList(productCnd, 1, PageUtil.PAGESIZE);
	    assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	    assertEquals(1, pe.getProductList().size());
	    assertEquals(1, pe.getCount());*/
	    
	    //分页
	    pe = productService.getProductList(productCnd, 1, 1);
	    assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	    assertEquals(1, pe.getProductList().size());
	    assertEquals(2, pe.getCount());
	}
	
	@Test
	public void testAddProduct() throws Exception {
		Product product = new Product();
		Shop shop = new Shop();
		ProductCategory pc = new ProductCategory();
		product.setProductName("hamburger");
		product.setProductDesc("汉堡包不好吃");
		product.setNormalPrice("15.00");
		product.setPromotionPrice("10.00");	
		product.setPriority(87);
		shop.setShopId(1);
		product.setShop(shop);
		pc.setProductCategoryId(14);
		product.setProductCategory(pc);
		File productImg = new File("E:\\STSFile\\Resources\\images\\hamburger.jpg");
		InputStream ins = new FileInputStream(productImg);
		ImageHolder imgHolder = new ImageHolder(ins, productImg.getName());
		
		File detail1 = new File("E:\\STSFile\\Resources\\images\\hanbao.jpg");
		File detail2 = new File("E:\\STSFile\\Resources\\images\\hanbaobao.jpg");
		InputStream ins1 = new FileInputStream(detail1);
		ImageHolder imgHolder1 = new ImageHolder(ins1, detail1.getName());
		InputStream ins2 = new FileInputStream(detail2);
		ImageHolder imgHolder2 = new ImageHolder(ins2, detail2.getName());
		Set<ImageHolder> detailSet = new HashSet<ImageHolder>();
		detailSet.add(imgHolder1);
		detailSet.add(imgHolder2);
		
		ProductExecution pe = productService.addProduct(product, imgHolder, detailSet);
		//assertEquals(-5, pe.getState());
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}
	
}
