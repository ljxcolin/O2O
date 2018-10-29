package stu.ljx.o2o.dto;

import java.util.List;

import stu.ljx.o2o.entity.ProductCategory;
import stu.ljx.o2o.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {
	
	/*操作结果状态*/
	private int state;
	/*操作结果状态说明*/
	private String stateInfo;
	
	/*批量操作商品类别时使用*/
	private List<ProductCategory> productCategories;
	/*操作商品类别的数量*/
	private int count;
	
	/**
	 * 空参构造
	 */
	public ProductCategoryExecution() {
		super();
	}
	
	/**
	 * 带参构造，操作失败时使用
	 */
	public ProductCategoryExecution(ProductCategoryStateEnum pcse) {
		this.state = pcse.getState();
		this.stateInfo = pcse.getStateInfo();
	}
	
	/**
	 * 带参构造，操作成功时使用
	 */
	public ProductCategoryExecution(ProductCategoryStateEnum pcse, List<ProductCategory> productCategories, int count) {
		this.state = pcse.getState();
		this.stateInfo = pcse.getStateInfo();
		this.productCategories = productCategories;
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

	public List<ProductCategory> getProductCategories() {
		return productCategories;
	}

	public void setProductCategories(List<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
