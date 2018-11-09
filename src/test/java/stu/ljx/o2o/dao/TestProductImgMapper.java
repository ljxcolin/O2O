package stu.ljx.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.ProductImg;

public class TestProductImgMapper extends BaseTest {
	
	@Autowired
	private ProductImgMapper productImgMapper;
	
	@Test
	public void testBatchInsertProductImg() {
		ProductImg pic1 = new ProductImg();
        pic1.setImgAddr("/productId/nskf/pic1");
        pic1.setImgDesc("浓缩咖啡商品详情图片1");
        pic1.setPriority(43);
        pic1.setCreateTime(new Date());
        pic1.setProductId(2);

        ProductImg pic2 = new ProductImg();
        pic2.setImgAddr("/productId/nskf/pic2");
        pic2.setImgDesc("浓缩咖啡商品详情图片2");
        pic2.setPriority(79);
        pic2.setCreateTime(new Date());
        pic2.setProductId(2);

        //添加到productImgSet中
        Set<ProductImg> productImgSet = new HashSet<ProductImg>();
        productImgSet.add(pic1);
        productImgSet.add(pic2);

        //调用接口批量新增商品详情图片
        int row = productImgMapper.batchInsertProductImg(productImgSet);
        System.out.println(pic1.getProductImgId() + "---" + pic2.getProductImgId());
        assertEquals(2, row);
	}
	
	@Test
	public void testDeleteProductById() {
		int row = productImgMapper.deleteProductImgById(7);
		assertEquals(2, row);
	}

}
