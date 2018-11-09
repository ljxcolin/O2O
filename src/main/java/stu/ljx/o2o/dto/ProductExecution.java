package stu.ljx.o2o.dto;

import java.util.List;

import stu.ljx.o2o.entity.Product;
import stu.ljx.o2o.enums.ProductStateEnum;

public class ProductExecution {
	
	/*操作结果状态*/
	private int state;
	/*操作结果状态说明*/
	private String stateInfo;
	
	/*操作成功的商品数量*/
	private int count;
	/*单一商品，增删改时用*/
	private Product product;
	/*多个商品，查询列表时用*/
	private List<Product> productList;
	
	/*无参构造*/
	public ProductExecution() {
		super();
	}
	
	/*商品操作失败时使用*/
	public ProductExecution(ProductStateEnum productStateEnum) {
		this.state = productStateEnum.getState();
		this.stateInfo = productStateEnum.getStateInfo();
	}
	
	/*增删改商品操作成功时使用*/
	public ProductExecution(ProductStateEnum productStateEnum, Product product) {
		this.state = productStateEnum.getState();
		this.stateInfo = productStateEnum.getStateInfo();
		this.product = product;
	}
	
	/*查询商品列表操作成功时使用*/
	public ProductExecution(ProductStateEnum productStateEnum, List<Product> productList, int count) {
		this.state = productStateEnum.getState();
		this.stateInfo = productStateEnum.getStateInfo();
		this.productList = productList;
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
