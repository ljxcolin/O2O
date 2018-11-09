package stu.ljx.o2o.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import stu.ljx.o2o.cache.JedisKit;
import stu.ljx.o2o.dao.ShopCategoryMapper;
import stu.ljx.o2o.entity.ShopCategory;
import stu.ljx.o2o.enums.ShopStateEnum;
import stu.ljx.o2o.exception.ShopException;
import stu.ljx.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

	@Autowired
	private ShopCategoryMapper shopCategoryMapper;
	@Autowired
	private JedisKit.Keys jedisKeys;
	@Autowired
	private JedisKit.Strings jedisStrings;

	/**
	 * 获取所有满足条件的商铺类别
	 * shopCategoryCnd!=null && parent!=null && parent.shopCategoryId != null-->查询指定一级商铺类别下的所有二级商铺类别
	 * shopCategoryCnd!=null && parent!=null-->查询所有二级商铺类别
	 * shopCategoryCnd!=null--> 查询所有一级商铺类别
	 */
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCnd) {
		List<ShopCategory> shopCategoryList = null;
		String key = retrieveKey(shopCategoryCnd, SCLISTKEY);
		ObjectMapper mapper = new ObjectMapper();
		try {
			if(jedisKeys.exists(key)) { //存在
				String jsonStr = jedisStrings.get(key);
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
				shopCategoryList = mapper.readValue(jsonStr, javaType);
			}else { //不存在
				shopCategoryList = shopCategoryMapper.queryShopCategory(shopCategoryCnd);
				String jsonStr = mapper.writeValueAsString(shopCategoryList);
				jedisStrings.set(key, jsonStr);
			}
		} catch (Exception e) {
			logger.error("getShopCategoryList error: {}", e.getMessage());
			throw new ShopException(ShopStateEnum.FAILED.getStateInfo());
		}
		return shopCategoryList;
	}
	
	/**
	 * 根据mapper中的查询条件，拼装shopcategory的key
	 * key取值：shopcategory_first_level/shopcategory_parent_xxx/shopcategory_second_level
	 * @param shopCategory
	 * @param sclistkey
	 * @return
	 */
	private String retrieveKey(ShopCategory shopCategoryCnd, String sclistkey) {
		String key = sclistkey;
        if (shopCategoryCnd != null && shopCategoryCnd.getParent() != null && shopCategoryCnd.getParent().getShopCategoryId() != null) {
        	//查询某一一级类别下的所有二级商铺类别
        	key = key + "_parent_" + shopCategoryCnd.getParent().getShopCategoryId();
        }else if (shopCategoryCnd != null && shopCategoryCnd.getParent() != null) {
        	//查询所有二级商铺类别
        	key = key + "_second_level";
        }else if(shopCategoryCnd != null) {
        	//查询所有一级商铺类别
        	key = key + "_first_level";
        }
		return key;
	}

}
