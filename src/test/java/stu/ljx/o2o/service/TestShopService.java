package stu.ljx.o2o.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.dto.ShopExecution;
import stu.ljx.o2o.entity.Area;
import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.entity.ShopCategory;
import stu.ljx.o2o.entity.User;
import stu.ljx.o2o.enums.ShopStateEnum;

public class TestShopService extends BaseTest {
	
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testAddShop() {
		Shop shop = new Shop();
        User user = new User();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        user.setUserId(1);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1);

        shop.setOwner(user);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("咖啡点");
        shop.setShopDesc("Colin咖啡店");
        shop.setShopAddr("GuangZhou");
        shop.setPhone("020-234945");
        shop.setPriority(99);
        shop.setAdvice("审核中");

        File shopImg = new File("E:/STSFile/Resources/images/colincoffee.jpg");
        FileInputStream shopImgIns = null;
        ShopExecution se = null;
		try {
			shopImgIns = new FileInputStream(shopImg);
			se = shopService.addShop(shop, shopImgIns, shopImg.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        assertEquals(ShopStateEnum.CHECKING.getState(), se.getState());
	}
	
	@Test
	public void testModifyShop() {
		//获取要修改的商铺
		Shop shop = shopService.getShopById(1);
		//修改商铺信息
		shop.getArea().setAreaId(1);
		shop.getShopCategory().setShopCategoryId(5);
		shop.setShopName("ColinHanbao");
		shop.setShopDesc("Conlin汉堡店");
		shop.setShopAddr("广州市天河区珠江新城");
		shop.setPhone("18219110066");
		shop.setPriority(82);
		shop.setAdvice("modfiying");
		
		File shopImg = new File("E:/STSFile/Resources/images/hamburger.jpg");
        FileInputStream shopImgIns = null;
        ShopExecution se = null;
		try {
			shopImgIns = new FileInputStream(shopImg);
			se = shopService.modifyShop(shop, shopImgIns, shopImg.getName(), shop.getShopImg());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        assertEquals(ShopStateEnum.SUCCESS.getState(), se.getState());
	}
	
	@Test
	public void testGetShopList() {
		Shop shopCnd = new Shop();
		User owner = new User();
		owner.setUserId(1);
		shopCnd.setShopName("a");
		shopCnd.setOwner(owner);
		//符合添加的数据有3条，每页取两条，第1页
		ShopExecution se = shopService.getShopList(shopCnd, 1, 2);
		assertNotNull(se);
		assertEquals(2, se.getShopList().size());
		assertEquals(3, se.getCount());
		
		//符合添加的数据有3条，每页取两条，第2页
		se = shopService.getShopList(shopCnd, 2, 2);
		assertNotNull(se);
		assertEquals(1, se.getShopList().size());
		assertEquals(3, se.getCount());
	}
	
}
