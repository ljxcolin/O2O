package stu.ljx.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import stu.ljx.o2o.entity.Shop;

public interface ShopMapper {

	/**
	 * 注册商铺
	 * @param shop
	 * @return 受影响的行数，1表示成功，-1表示失败
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新店铺
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
	
	/**
	 * 根据ID获取商铺
	 * @param shopId
	 * @return 返回指定ID的商铺
	 */
	Shop getShopById(Integer shopId);
	
	/**
	 * 查询符合条件的商铺列表
	 * @param shopCnd 条件封装类
	 * @param rowIndex 分页，起始记录
	 * @param pageSize 分页，记录条数
	 * @return List<Shop> 商铺列表
	 */
	List<Shop> queryShop(@Param("shopCnd")Shop shopCnd, @Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	
	/**
	 * 查询符合条件的商铺的数量
	 * @param shopCnd
	 * @return
	 */
	int countShop(@Param("shopCnd")Shop shopCnd);
	
}
