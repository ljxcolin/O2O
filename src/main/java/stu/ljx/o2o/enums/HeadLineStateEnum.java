package stu.ljx.o2o.enums;

/**
 * 头条状态字典
 * 		DISABLED		0		不可用
 * 		ENABLED			1		可用
 * 		INVALID			-1		头条为空
 * 		SUCCESS			2		操作成功
 * 		FAILED			-2		操作失败
 * @author Lijinxuan
 * 
 */
public enum HeadLineStateEnum {
	
	DISABLED(0,"不可用"), ENABLED(1,"可用"), NULL_HEAD_LINE(-1,"头条为空"), SUCCESS(2,"操作成功"), FAILED(-2,"操作失败");

	private int state;
	private String stateInfo;
	
	/**
	 * 私有构造，禁止外部改变定义好的常量
	 * @param state
	 * @param stateInfo
	 */
	private HeadLineStateEnum(int state, String stateInfo) {
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
	 * 通过state值来获取HeadLineStateEnum
	 * @param state
	 * @return
	 */
	public static HeadLineStateEnum stateOf(int state) {
		for(HeadLineStateEnum stateEnum : values()) {
			if(stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

}
