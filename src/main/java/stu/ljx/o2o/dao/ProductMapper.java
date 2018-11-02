package stu.ljx.o2o.dao;

import stu.ljx.o2o.entity.Product;

public interface ProductMapper {
	
	/**
	 * 添加商品
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);

	/**
	 * 仅仅更新商品缩略图
	 * @param product
	 * @return
	 */
	int updateProductImg(Product product);
	
}
