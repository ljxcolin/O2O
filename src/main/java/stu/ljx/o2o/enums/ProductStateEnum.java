package stu.ljx.o2o.enums;

/**
 * 商品状态字典
 * 		SUCCESS					2		操作成功
 * 		FAILED					-2		操作失败
 * 		PARAM_ERROR				-3		参数错误
 * 		NULL_IMAGE				-4		商品图片为空
 * @author Lijinxuan
 * 
 */
public enum ProductStateEnum {
		
	SUCCESS(2, "操作成功"), FAILED(-2, "操作失败"), PARAM_ERROR(-3, "商品参数错误"), 
	NULL_IMAGE(-4, "商品图片为空"), NULL_DETAIL_IMAGE(-5, "商品详情图片为空");
	
	private int state;
	private String stateInfo;
	
	/**
	 * 私有构造，禁止外部改变定义好的常量
	 * @param state
	 * @param stateInfo
	 */
	private ProductStateEnum(int state, String stateInfo) {
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
	 * 通过state值来获取ProductStateEnum
	 * @param state
	 * @return
	 */
	public static ProductStateEnum stateOf(int state) {
		for(ProductStateEnum stateEnum : values()) {
			if(stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}
	
}
