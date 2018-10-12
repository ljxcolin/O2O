package stu.ljx.o2o.entity;

import java.io.Serializable;
import java.util.Date;

public class LocalAuth implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer localAuthId;
	private String userName;
	private String password;
	private Date createTime;
	private Date lastEditTime;
	
	/*关联用户*/
	private User user;

	public Integer getLocalAuthId() {
		return localAuthId;
	}

	public void setLocalAuthId(Integer localAuthId) {
		this.localAuthId = localAuthId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "LocalAuth [localAuthId=" + localAuthId + ", userName=" + userName + ", password=" + password
				+ ", createTime=" + createTime + ", lastEditTime=" + lastEditTime + ", user=" + user + "]";
	}

}
