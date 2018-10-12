package stu.ljx.o2o.entity;

import java.io.Serializable;
import java.util.Date;

public class WechatAuth implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer wechatAuthId;
	/*微信账号唯一标识*/
	private String openId;
	private Date createTime;
	
	/*关联用户*/
	private User user;
	
	public Integer getWechatAuthId() {
		return wechatAuthId;
	}
	public void setWechatAuthId(Integer wechatAuthId) {
		this.wechatAuthId = wechatAuthId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "WechatAuth [wechatAuthId=" + wechatAuthId + ", openId=" + openId
				+ ", createTime=" + createTime + ", user=" + user + "]";
	}

}
