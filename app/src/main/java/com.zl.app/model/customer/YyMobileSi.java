package com.zl.app.model.customer;

import java.util.Date;

public class YyMobileSi {
	private Integer siId;//主键
	private String content;//内容
	private Integer type;//类型 1 邀请老师
	private Date createDate;//通知时间
	public Integer getSiId() {
		return siId;
	}
	public void setSiId(Integer siId) {
		this.siId = siId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}