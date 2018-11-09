package stu.ljx.o2o.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer productId;
	private String productName;
	private String productDesc;
	/*商品缩略图*/
	private String imgAddr;
	private String normalPrice; //原价
	private String promotionPrice; //折后价
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	/*商品状态：-1:'不可用'，0:'下架'，1:'展示'*/
	private Integer enableStatus;
	
	/*关联商品图片详情（一对多）*/
	private Set<ProductImg> productImgSet;
	/*关联商品所属类别（一对一）*/
	private ProductCategory productCategory;
	/*关联所属店铺*/
	private Shop shop;
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getNormalPrice() {
		return normalPrice;
	}
	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}
	public String getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(String promotionPrice) {
		this.promotionPrice = promotionPrice;
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
	public Set<ProductImg> getProductImgSet() {
		return productImgSet;
	}
	public void setProductImgSet(Set<ProductImg> productImgSet) {
		this.productImgSet = productImgSet;
	}
	public ProductCategory getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productDesc=" + productDesc
				+ ", imgAddr=" + imgAddr + ", normalPrice=" + normalPrice + ", promotionPrice=" + promotionPrice
				+ ", priority=" + priority + ", createTime=" + createTime + ", lastEditTime=" + lastEditTime
				+ ", enableStatus=" + enableStatus + ", productImgSet=" + productImgSet + ", productCategory="
				+ productCategory + ", shop=" + shop + "]";
	}

}
