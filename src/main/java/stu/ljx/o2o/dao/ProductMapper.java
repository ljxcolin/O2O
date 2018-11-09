package stu.ljx.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import stu.ljx.o2o.entity.Product;

public interface ProductMapper {
	
	/**
	 * 查询符合条件的商品列表
	 * @param shopCnd 条件封装类
	 * @param rowIndex 分页，起始记录
	 * @param pageSize 分页，记录条数
	 * @return List<Shop> 商铺列表
	 */
	List<Product> queryProduct(@Param("productCnd")Product productCnd, @Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	
	/**
	 * 查询符合条件的商品的数量
	 * @param shopCnd
	 * @return
	 */
	int countProduct(@Param("productCnd")Product productCnd);
	
	/**
	 * 根据ID获取商品
	 * @param productId
	 * @return
	 */
	Product getProductById(Integer productId);
	
	
	/**
	 * 添加商品
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
	
	/**
	 * 更新商品
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);
	
	/**
	 * 删除商品
	 * @param productId
	 * @return
	 */
	int deleteProduct(Integer productId);
	
	/**
	 * 仅仅更新商品缩略图
	 * @param product
	 * @return
	 */
	int updateProductImg(Product product);
	
	/**
	 * 仅仅更新商品状态
	 * @param product
	 * @return
	 */
	int updateProductStatus(Product product);
	
	/**
	 * 将商品的类别置为null，解除商品与商品类别的关系
	 * @param product
	 * @return
	 */
	int updateProductCategoryToNull(@Param("productCategoryId")Integer productCategoryId, @Param("shopId")Integer shopId);
	
}
