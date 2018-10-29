package stu.ljx.o2o.dto;

import java.util.List;

import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.enums.ShopStateEnum;

public class ShopExecution {
	
	/*操作结果状态*/
	private int state;
	/*操作结果状态说明*/
	private String stateInfo;
	
	/*商铺数量*/
	private int count;
	/*单一商铺，增删改时用*/
	private Shop shop;
	/*多个商铺，查询列表时用*/
	private List<Shop> shopList;
	
	/*商铺操作失败时使用*/
	public ShopExecution(ShopStateEnum shopStateEnum) {
		this.state = shopStateEnum.getState();
		this.stateInfo = shopStateEnum.getStateInfo();
	}
	
	/*增删改商铺操作成功时使用*/
	public ShopExecution(ShopStateEnum shopStateEnum, Shop shop) {
		this.state = shopStateEnum.getState();
		this.stateInfo = shopStateEnum.getStateInfo();
		this.shop = shop;
	}
	
	/*查询商铺列表操作成功时使用*/
	public ShopExecution(ShopStateEnum shopStateEnum, List<Shop> shopList, int count) {
		this.state = shopStateEnum.getState();
		this.stateInfo = shopStateEnum.getStateInfo();
		this.shopList = shopList;
		this.count = count;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

}
