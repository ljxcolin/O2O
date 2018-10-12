package stu.ljx.o2o.entity;

import java.io.Serializable;
import java.util.Date;

public class Area implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer areaId;
	private String areaName;
	private String areaDesc;
	/*权重，数值越大展示越靠前*/
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;

	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
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
	
	@Override
	public String toString() {
		return "Area [areaId=" + areaId + ", areaName=" + areaName + ", areaDesc=" + areaDesc + ", priority=" + priority
				+ ", createTime=" + createTime + ", lastEditTime=" + lastEditTime + "]";
	}

}
