package stu.ljx.o2o.enums;

/**
 * 商铺类别状态字典
 * 		SUCCESS					2		操作成功
 * 		FAILED					-2		操作失败
 * 		NULL_SHOP				-3		商铺ID为空
 * 		EMPTY_CATEGORY_LIST		-4		商品类别列表为空
 * @author Lijinxuan
 * 
 */
public enum ProductCategoryStateEnum {
	
	SUCCESS(2, "操作成功"), FAILED(-2, "操作失败"), NULL_SHOP(-3, "商铺ID为空"), EMPTY_CATEGORY_LIST(-4, "请输入商品类别信息");

	private int state;
	private String stateInfo;
	
	/**
	 * 私有构造，禁止外部改变定义好的常量
	 * @param state
	 * @param stateInfo
	 */
	private ProductCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	/**
	 * 仅对外提供get方法，防止外部通过set方法改变定义好的常量
	 * @return
	 */
	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	/**
	 * 通过state值来获取ShopStateEnum
	 * @param state
	 * @return
	 */
	public static ProductCategoryStateEnum stateOf(int state) {
		for(ProductCategoryStateEnum stateEnum : values()) {
			if(stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

}
