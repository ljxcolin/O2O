package stu.ljx.o2o.entity;

import java.io.Serializable;
import java.util.Date;

public class Shop implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer shopId;
	private String shopName;
	private String shopDesc;
	private String shopAddr;
	private String phone;
	private String shopImg;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	/*商铺状态：-1:'不可用'，0:'审核中'，1:'可用'*/
	private Integer enableStatus;
	/*管理员给商家的建议*/
	private String advice;
	
	/*关联商家：商铺所属商家*/
	private User owner;	
	/*关联区域：商铺所在区域*/
	private Area area;
	/*关联商铺类别：商铺所属类别*/
	private ShopCategory shopCategory;

	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopDesc() {
		return shopDesc;
	}
	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}
	public String getShopAddr() {
		return shopAddr;
	}
	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public ShopCategory getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}
	@Override
	public String toString() {
		return "Shop [shopId=" + shopId + ", shopName=" + shopName + ", shopDesc=" + shopDesc + ", shopAddr=" + shopAddr
				+ ", phone=" + phone + ", shopImg=" + shopImg + ", priority=" + priority + ", createTime=" + createTime
				+ ", lastEditTime=" + lastEditTime + ", enableStatus=" + enableStatus + ", advice=" + advice
				+ ", owner=" + owner + ", area=" + area + ", shopCategory=" + shopCategory + "]";
	}

}
