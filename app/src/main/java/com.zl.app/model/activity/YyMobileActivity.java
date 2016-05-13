package com.zl.app.model.activity;


public class YyMobileActivity {
	private Integer activityId;//主键
	private String picPath;//头像图片
	private String username;//发布人姓名
	private Integer userId;//发布人Id
	private String headline;//标题
	private String keywords;//关键字
	private String zhaiyao;//摘要
	private String content;//内容
	private String address;//活动地点
	private String hdDate;//活动时间
	private String endDate;//报名结束时间
	private Integer charge;//费用
	private Integer rs;//人数
	private Integer ybrs;//报名人数
	private Integer plno;//评论条数
	private Integer active;//2 可以报名  3 不可报名;
	private Integer isjoin;//2 已报名  3 未报名;
	private Integer isover;//2 进行中  3 结束;
	private Integer ispublic;//2 公开 3不公开 仅限关注好友
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getZhaiyao() {
		return zhaiyao;
	}
	public void setZhaiyao(String zhaiyao) {
		this.zhaiyao = zhaiyao;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHdDate() {
		return hdDate;
	}
	public void setHdDate(String hdDate) {
		this.hdDate = hdDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getCharge() {
		return charge;
	}
	public void setCharge(Integer charge) {
		this.charge = charge;
	}
	public Integer getRs() {
		return rs;
	}
	public void setRs(Integer rs) {
		this.rs = rs;
	}
	public Integer getYbrs() {
		return ybrs;
	}
	public void setYbrs(Integer ybrs) {
		this.ybrs = ybrs;
	}
	public Integer getPlno() {
		return plno;
	}
	public void setPlno(Integer plno) {
		this.plno = plno;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public Integer getIsover() {
		return isover;
	}
	public void setIsover(Integer isover) {
		this.isover = isover;
	}
	public Integer getIspublic() {
		return ispublic;
	}
	public void setIspublic(Integer ispublic) {
		this.ispublic = ispublic;
	}
	public Integer getIsjoin() {
		return isjoin;
	}
	public void setIsjoin(Integer isjoin) {
		this.isjoin = isjoin;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
