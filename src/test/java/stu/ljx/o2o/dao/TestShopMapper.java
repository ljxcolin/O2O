package stu.ljx.o2o.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.Area;
import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.entity.ShopCategory;
import stu.ljx.o2o.entity.User;

public class TestShopMapper extends BaseTest {
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Test
	public void testInsertShop() {
		Shop shop = new Shop();
		User owner = new User();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		/*由于tb_shop有owner_id,area_id和shop_category_id外键，因此要确保与之关联的表该外键记录要存在
		 *避免抛出com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException异常*/
		owner.setUserId(1);
		area.setAreaId(1);
		shopCategory.setShopCategoryId(1);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("Artisan");
		shop.setShopDesc("sfjaoifhd");
		shop.setShopAddr("GuangZhou");
		shop.setPhone("13476589834");
		shop.setShopImg("/shop/img1");
		shop.setPriority(99);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("warning");
		
		int row = shopMapper.insertShop(shop);
		assertEquals(row, 1);
	}
	
	@Test
	public void testUpdateShop() {
		Shop shop = shopMapper.getShopById(1);
		/*商铺ID、商铺拥有者owner、创建时间createTime不可更新*/
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		area.setAreaId(2);
		shopCategory.setShopCategoryId(2);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopAddr("NanJing");
		shop.setPriority(66);
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("normal");
		
		int row = shopMapper.updateShop(shop);
		assertEquals(row, 1);
	}
	
	@Test
	public void testQueryAandCountShop() {
		Shop shopCnd = new Shop();
		User owner = new User();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		List<Shop> shopList = null;
		int count = 0;
		/**
         * 可输入的查询条件： 1、商铺名称（要求模糊查询） 2、所属区域  3、商铺类别  4、商铺状态  5、商铺拥有者
         * 首先按照单个条件进行逐个测试，然后组合测试
         **/
		//1、商铺名称
//		shopCnd.setShopName("s");
//		shopList = shopMapper.queryShop(shopCnd, 0, 6);
//		count = shopMapper.countShop(shopCnd);
//		assertEquals(3, shopList.size());
//		assertEquals(3, count);
//		shopList = shopMapper.queryShop(shopCnd, 0, 2);//验证分页
//		count = shopMapper.countShop(shopCnd);
//		assertEquals(2, shopList.size());
//		assertEquals(3, count);
		//2、所属区域
//		area.setAreaId(1);
//		shopCnd.setArea(area);
//		shopList = shopMapper.queryShop(shopCnd, 0, 6);
//		count = shopMapper.countShop(shopCnd);
//		assertEquals(3, shopList.size());
//		assertEquals(3, count);
		//3、商铺类别
		//shopCategory.setShopCategoryId(3);
//		shopCategory.setShopCategoryId(4);
//		shopCnd.setShopCategory(shopCategory);
//		shopList = shopMapper.queryShop(shopCnd, 0, 6);
//		count = shopMapper.countShop(shopCnd);
//		assertEquals(1, shopList.size());
//		assertEquals(1, count);
		//4、商铺状态
//		shopCnd.setEnableStatus(0);
//		shopList = shopMapper.queryShop(shopCnd, 0, 6);
//		count = shopMapper.countShop(shopCnd);
//		assertEquals(5, shopList.size());
//		assertEquals(5, count);
		//5、商铺拥有者
		//owner.setUserId(1);
//		owner.setUserId(2);
//		shopCnd.setOwner(owner);
//		shopList = shopMapper.queryShop(shopCnd, 0, 6);
//		count = shopMapper.countShop(shopCnd);
//		assertEquals(6, shopList.size());
//		assertEquals(6, count);
//		assertEquals(0, shopList.size());
//		assertEquals(0, count);
		
		//6、组合条件查询
		//组合1：shop_name like %a% and area_id = 2
//		area.setAreaId(2);
//		shopCnd.setShopName("a");
//		shopCnd.setArea(area);
//		shopList = shopMapper.queryShop(shopCnd, 0, 6);
//		count = shopMapper.countShop(shopCnd);
//		assertEquals(2, shopList.size());
//		assertEquals(2, count);
		//组合2：shop_name like %e% and area_id = 2 and shop_category_id = 3 and owner_id = 1
		owner.setUserId(1);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(3);
		shopCnd.setShopName("e");
		shopCnd.setOwner(owner);
		shopCnd.setArea(area);
		shopCnd.setShopCategory(shopCategory);
		shopList = shopMapper.queryShop(shopCnd, 0, 6);
		count = shopMapper.countShop(shopCnd);
		assertEquals(1, shopList.size());
		assertEquals(1, count);
	}
	
	@Test
	public void testGetShopById() {
		Shop shop = shopMapper.getShopById(5);
		assertNotNull(shop);
		assertEquals("TsqTea", shop.getShopName());
		assertEquals("Artisan", shop.getOwner().getName());
		assertEquals("北京", shop.getArea().getAreaName());
		assertEquals("奶茶", shop.getShopCategory().getShopCategoryName());
		System.out.println(shop);
	}
	
	@Test
	public void testQueryAandCountShopPlus() {
		Shop shopCnd = new Shop();
		ShopCategory shopCategory = new ShopCategory();
		ShopCategory parent = new ShopCategory();
		parent.setShopCategoryId(1);
		shopCategory.setParent(parent);
		shopCnd.setShopCategory(shopCategory);
		List<Shop> shopList = shopMapper.queryShop(shopCnd, 0, 5);
		int count = shopMapper.countShop(shopCnd);
		assertEquals(3, shopList.size());
		assertEquals(3, count);
	}
	
}
