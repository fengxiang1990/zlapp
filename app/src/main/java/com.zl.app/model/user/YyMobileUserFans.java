package com.zl.app.model.user;



public class YyMobileUserFans {
	private Integer fansId;//主键
	private Integer personId;//关注人userId
	private String personName;//关注人姓名
	private String personPic;//关注人头像
	private String hdurl; //活动URL
	private String hdheadline;//活动标题
	private String hdupdateDate;//活动发布时间
	private Integer haveletter;//未读私信 2 有  1无
	public Integer getFansId() {
		return fansId;
	}
	public void setFansId(Integer fansId) {
		this.fansId = fansId;
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
	public String getPersonPic() {
		return personPic;
	}
	public void setPersonPic(String personPic) {
		this.personPic = personPic;
	}
	public String getHdurl() {
		return hdurl;
	}
	public void setHdurl(String hdurl) {
		this.hdurl = hdurl;
	}
	public String getHdheadline() {
		return hdheadline;
	}
	public void setHdheadline(String hdheadline) {
		this.hdheadline = hdheadline;
	}

	public String getHdupdateDate() {
		return hdupdateDate;
	}

	public void setHdupdateDate(String hdupdateDate) {
		this.hdupdateDate = hdupdateDate;
	}
	public Integer getHaveletter() {
		return haveletter;
	}
	public void setHaveletter(Integer haveletter) {
		this.haveletter = haveletter;
	}
	
}
