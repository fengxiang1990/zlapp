package com.zl.app.data.model.user;


public class YyMobileUserLetter {
	//私信记录 2个人生成一条主记录，私信内容存在YyMobileLetter
	private Integer letterId;//主键
	private Integer personId;//私信人ID
	private String personName;//私信人昵称
	private String picPath;//私信人头像
	private String updateDate;//私信最新信息的日期
	private String content;//私信最新信息的内容
	private String updateBy;//私信最新更新人的昵称
	public Integer getLetterId() {
		return letterId;
	}
	public void setLetterId(Integer letterId) {
		this.letterId = letterId;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
}
