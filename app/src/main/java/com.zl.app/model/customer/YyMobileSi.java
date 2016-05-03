package com.zl.app.model.customer;


public class YyMobileSi {
	private Integer siId;//主键
	private String content;//内容
	private Integer type;//类型 1 邀请老师
	private String createDate;//通知时间
	private Integer status;//2 可操作    3 不可操作
	private Integer mainId;//操作关联的主键

	public Integer getMainId() {
		return mainId;
	}

	public void setMainId(Integer mainId) {
		this.mainId = mainId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
