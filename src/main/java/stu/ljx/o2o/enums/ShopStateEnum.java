package stu.ljx.o2o.enums;

/**
 * 商铺状态字典
 * 		CHECKING		0		审核中
 * 		VALID			1		审核通过
 * 		INVALID			-1		非法商铺
 * 		SUCCESS			2		操作成功
 * 		FAILED			-2		操作失败
 * 		NULL_SHOP_ID	-3		商铺ID不能为空
 * 		NULL_SHOP_INFO	-4		商铺信息不能为空
 * @author Lijinxuan
 * 
 */
public enum ShopStateEnum {
	
	CHECKING(0,"审核中"), VALID(1,"审核通过"), INVALID(-1,"非法商铺"), SUCCESS(2,"操作成功"), FAILED(-2,"操作失败"), NULL_SHOP_ID(-3,"商铺ID为空"), NULL_SHOP_INFO(-4,"商铺信息为空");

	private int state;
	private String stateInfo;
	
	/**
	 * 私有构造，禁止外部改变定义好的常量
	 * @param state
	 * @param stateInfo
	 */
	private ShopStateEnum(int state, String stateInfo) {
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
	public static ShopStateEnum stateOf(int state) {
		for(ShopStateEnum stateEnum : values()) {
			if(stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

}
