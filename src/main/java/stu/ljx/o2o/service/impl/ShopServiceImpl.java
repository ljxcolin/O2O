package stu.ljx.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import stu.ljx.o2o.dao.ShopMapper;
import stu.ljx.o2o.dto.ImageHolder;
import stu.ljx.o2o.dto.ShopExecution;
import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.enums.ShopStateEnum;
import stu.ljx.o2o.exception.ShopException;
import stu.ljx.o2o.service.ShopService;
import stu.ljx.o2o.util.FileUtil;
import stu.ljx.o2o.util.ImageUtil;
import stu.ljx.o2o.util.PageUtil;

@Service
public class ShopServiceImpl implements ShopService {

	private static final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);
	
	@Autowired
	private ShopMapper shopMapper;
	
	/**
	 * 添加商铺
	 * 	1、将shop基本信息添加到数据库，返回shopId
	 * 	2、根据shopId创建目录,得到图片存储的相对路径
	 * 	3、将相对路径更新到数据库
	 * 以上3个步骤需要在一个事务中，使用@Transactional注解声明事务
	 */
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public ShopExecution addShop(Shop shop, InputStream shopImgIns, String shopImgName) throws ShopException{
		//一般性非空判断
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		}
		//关键步骤1：设置商铺基本信息，初始状态为"审核中"，插入shop
		shop.setEnableStatus(ShopStateEnum.CHECKING.getState());
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		int row = shopMapper.insertShop(shop);
		
		//判断是否插入成功
		if(row < 1) { //插入失败
			throw new ShopException("商铺创建失败");
		}else { //插入成功
			//关键步骤2：处理商铺头像
			if(shopImgIns != null) {
				try {
					//添加商铺头像
					addShopImg(shop, shopImgIns, shopImgName);
				}catch(Exception e) {
					logger.error("addShopImg error: {}", e.toString());
					throw new ShopException("addShopImg error: " + e.getMessage());
				}
				row = shopMapper.updateShop(shop);
				if(row < 1) {
					logger.error("updateShop error: {}", "商铺图片上传失败");
					throw new ShopException("商铺图片上传失败");
				}
			}
		}
		//返回该商铺及其状态
		return new ShopExecution(ShopStateEnum.CHECKING, shop);
	}

	/**
	 * 根据shopId创建商铺图片目录，并生成水印图片
	 * @param shop
	 * @param shopImg
	 */
	private void addShopImg(Shop shop, InputStream shopImgIns, String shopImgName) {
		//根据shopId获得商铺图片的相对目录
		String shopImgPath = FileUtil.getShopImgPath(shop.getShopId());
		//生成水印图片
		String relativePath = ImageUtil.generateThumbnail(new ImageHolder(shopImgIns, shopImgName), shopImgPath);
		//将商铺图片的相对路径设置到shop的shopImg属性中，用于更新数据库shop_img字段
		shop.setShopImg(relativePath);
	}

	/**
	 * 获取指定ID的商铺，无需事务
	 */
	@Override
	public Shop getShopById(Integer shopId) {
		return shopMapper.getShopById(shopId);
	}

	/**
	 * 修改商铺信息，参考addShop，注意以下几点
	 * 1、更新最后修改时间
	 * 2、若更换商铺图片，则删掉旧的图片并生成新的商铺图片
	 */
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public ShopExecution modifyShop(Shop shop, InputStream shopImgIns, String newShopImg, String oldShopImg) throws ShopException {
		//一般性非空判断
		if(shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		}
		try {
			//关键步骤1：更新最后修改时间，状态不变
			shop.setLastEditTime(new Date());
			//关键步骤2：判断是否需要修改商铺头像
			if(shopImgIns != null) {
				//删除旧的商铺头像
				ImageUtil.removePath(oldShopImg);
				//生成新的商铺头像
				addShopImg(shop, shopImgIns, newShopImg);
			}
			//更新商铺
			int row = shopMapper.updateShop(shop);
			if(row < 1) {
				throw new ShopException(ShopStateEnum.FAILED.getStateInfo());
			}
			//返回该商铺及其状态
			return new ShopExecution(ShopStateEnum.SUCCESS, shop);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("modifyShop error: {}", e.toString());
			throw new ShopException("modifyShop error: " + e.getMessage());
		}
	}
	
	/**
	 * 获取满足条件的带分页的商铺列表
	 */
	@Override
	public ShopExecution getShopList(Shop shopCnd, int pageIndex, int pageSize) {
		//将pageIndex转换为rowIndex
		int rowIndex = PageUtil.calculate(pageIndex, pageSize);
		//查询带有分页的商铺列表
		List<Shop> shopList = shopMapper.queryShop(shopCnd, rowIndex, pageSize);
		//查询符合条件的商铺数量
		int count = shopMapper.countShop(shopCnd);
		ShopExecution se = null;
		if(shopList != null && count >= 0) {
			se = new ShopExecution(ShopStateEnum.SUCCESS, shopList, count);
		}else {
			se = new ShopExecution(ShopStateEnum.FAILED);
		}
		return se;
	}

}
