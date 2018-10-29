package stu.ljx.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import stu.ljx.o2o.entity.ProductCategory;

public interface ProductCategoryMapper {
	
	List<ProductCategory> queryProductCategory(Integer shopId);
	
	/**
	 * 批量新增商品类别
	 * @param productCategories
	 * @return 新增类别的数量
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategories);
	
	/**
	 * 删除指定的商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId")Integer productCategoryId, @Param("shopId")Integer shopId);
}
