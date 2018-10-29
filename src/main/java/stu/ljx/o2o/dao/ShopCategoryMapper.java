package stu.ljx.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import stu.ljx.o2o.entity.ShopCategory;

public interface ShopCategoryMapper {
	
	ShopCategory getCategoryById(Integer shopCategoryId);

	/**
	 * 查询商铺类别
	 * 	实际需求：
	 * 		1、首页展示一级类别（即parent_id为 null的商铺类别）
	 * 		2、点击一级类别加载该目录对应的二级类别
	 * 	因此需要加个入参ShopCategory，并通过MyBatis提供的注解@Param与Mapper映射文件中的SQL关联起来，在SQL中进行判断
	 * @return
	 */
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCnd")ShopCategory shopCategory);
	
}
