package com.zl.app.data.user.model;


public class YyMobileUserFans {
	private Integer fansId;//主键
	private Integer personId;//关注人userId
	private String personName;//关注人姓名
	private String personPic;//关注人头像
	private Integer fbno;//发布文章数量
	private Integer lrno;//浏览文章数量
	private Integer scno;//收藏数量
	private Integer dzno;//点赞数量
	private Integer plno;//评论数量
	private Integer gzgr;//关注个人数量
	private Integer gzjg;//关注机构数量
	private String newsurl;//关注人最新动态URL
	private String headline;//关注人最新动态标题
	private String updateDate;//关注人最新动态时间
	private Integer plshow;//是否显示评论 1 是 0 否
	private Integer scshow;//是否显示收藏 1 是 0 否
	private Integer dzshow;//是否显示点赞 1 是 0 否
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
	public Integer getFbno() {
		return fbno;
	}
	public void setFbno(Integer fbno) {
		this.fbno = fbno;
	}
	public Integer getLrno() {
		return lrno;
	}
	public void setLrno(Integer lrno) {
		this.lrno = lrno;
	}
	public Integer getScno() {
		return scno;
	}
	public void setScno(Integer scno) {
		this.scno = scno;
	}
	public Integer getDzno() {
		return dzno;
	}
	public void setDzno(Integer dzno) {
		this.dzno = dzno;
	}
	public Integer getPlno() {
		return plno;
	}
	public void setPlno(Integer plno) {
		this.plno = plno;
	}
	public Integer getGzgr() {
		return gzgr;
	}
	public void setGzgr(Integer gzgr) {
		this.gzgr = gzgr;
	}
	public Integer getGzjg() {
		return gzjg;
	}
	public void setGzjg(Integer gzjg) {
		this.gzjg = gzjg;
	}
	public String getNewsurl() {
		return newsurl;
	}
	public void setNewsurl(String newsurl) {
		this.newsurl = newsurl;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getPlshow() {
		return plshow;
	}
	public void setPlshow(Integer plshow) {
		this.plshow = plshow;
	}
	public Integer getScshow() {
		return scshow;
	}
	public void setScshow(Integer scshow) {
		this.scshow = scshow;
	}
	public Integer getDzshow() {
		return dzshow;
	}
	public void setDzshow(Integer dzshow) {
		this.dzshow = dzshow;
	}
	
}
