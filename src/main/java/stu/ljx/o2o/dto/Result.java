package stu.ljx.o2o.dto;

/**
 * 操作结果封装泛型类
 * @author Lijinxuan
 * @param <D> 要封装的数据
 */
public class Result<D> {
	
	/*结果标识*/
	private boolean success;
	
	/*成功时返回的数据*/
	private D data;
	
	/*错误代码*/
	private int errCode;
	
	/*错误信息*/
	private String errMsg;

	/**
	 * 无参构造
	 */
	public Result() {
		super();
	}
	
	/**
	 * 带参构造，成功返回数据时使用
	 * @param success true
	 * @param data	数据
	 */
	public Result(boolean success, D data) {
		super();
		this.success = success;
		this.data = data;
	}

	/**
	 * 带参构造，数据获取失败时使用
	 * @param success false
	 * @param errCode 错误代码
	 * @param errMsg 错误信息
	 */
	public Result(boolean success, int errCode, String errMsg) {
		super();
		this.success = success;
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public D getData() {
		return data;
	}

	public void setData(D data) {
		this.data = data;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
