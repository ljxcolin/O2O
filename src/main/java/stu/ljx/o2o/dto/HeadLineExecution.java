package stu.ljx.o2o.dto;

import java.util.List;

import stu.ljx.o2o.entity.HeadLine;
import stu.ljx.o2o.enums.HeadLineStateEnum;

public class HeadLineExecution {
	
	/*操作结果状态*/
	private int state;
	/*操作结果状态说明*/
	private String stateInfo;

	/*单一头条，增删改时用*/
	private HeadLine headLine;
	/*多个头条，查询列表时用*/
	private List<HeadLine> headLineList;
	
	/*无参构造*/
	public HeadLineExecution() {
		super();
	}
	
	/*商品操作失败时使用*/
	public HeadLineExecution(HeadLineStateEnum headLineStateEnum) {
		this.state = headLineStateEnum.getState();
		this.stateInfo = headLineStateEnum.getStateInfo();
	}
	
	/*增删改商品操作成功时使用*/
	public HeadLineExecution(HeadLineStateEnum headLineStateEnum, HeadLine headLine) {
		this.state = headLineStateEnum.getState();
		this.stateInfo = headLineStateEnum.getStateInfo();
		this.headLine = headLine;
	}
	
	/*查询商品列表操作成功时使用*/
	public HeadLineExecution(HeadLineStateEnum headLineStateEnum, List<HeadLine> headLineList) {
		this.state = headLineStateEnum.getState();
		this.stateInfo = headLineStateEnum.getStateInfo();
		this.headLineList = headLineList;
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
	public HeadLine getHeadLine() {
		return headLine;
	}
	public void setHeadLine(HeadLine headLine) {
		this.headLine = headLine;
	}
	public List<HeadLine> getHeadLineList() {
		return headLineList;
	}
	public void setHeadLineList(List<HeadLine> headLineList) {
		this.headLineList = headLineList;
	}

}
