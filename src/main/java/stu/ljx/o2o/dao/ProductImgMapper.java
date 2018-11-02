package stu.ljx.o2o.dao;

import java.util.Set;

import stu.ljx.o2o.entity.ProductImg;

public interface ProductImgMapper {
	
	/**
	 * 批量添加商品详情图片
	 * 注意：MyBatis入参为Set类型时，parameterType应为"java.util.Set"，foreach的key应为collection
	 * @param product
	 * @return
	 */
	int batchInsertProductImg(Set<ProductImg> productImgSet);
	
}
