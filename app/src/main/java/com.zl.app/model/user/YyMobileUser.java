package com.zl.app.model.user;


public class YyMobileUser {
	private Integer userId; //主键
	private String account; //后台管理员账号
	private String password;//密码
	private String mobile;//手机（账号）
	private String email;//邮箱
	private Integer type; //类型 1 超级管理员 2 平台管理员 3 普通用户 4 机构管理员 5 机构用户
	private String company;//公司
	private Integer status; // 2 停用
	private String nickName;// 网名
	private String province; //省
	private String city;//市
	private String district;//区
	private String zuoyouming;//座右铭
	private String introduce;//个人介绍
	private String picPath;//头像路径
	private Integer mobileshow;//是否显示手机 1 是 0 否
	private Integer emailshow;//是否显示email 1 是 0 否
	private Integer qqshow;//是否显示qq 1 是 0 否
	private Integer plshow;//是否显示评论 1 是 0 否
	private Integer scshow;//是否显示收藏 1 是 0 否
	private Integer dzshow;//是否显示点赞 1 是 0 否
	private String qq;//qq
	private Integer age;//年龄
	private Integer fbno;//发布文章数量
	private Integer lrno;//浏览文章数量
	private Integer scno;//收藏数量
	private Integer dzno;//点赞数量
	private Integer plno;//评论数量
	private Integer gzgr;//关注个人数量
	private Integer gzjg;//关注机构数量
	private String newsurl;//最新发布文章URL
	private String headline;//最新发布文章标题
	private String updateDateFormat;//最新发布时间
	private String uid;//64位唯一码
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getZuoyouming() {
		return zuoyouming;
	}
	public void setZuoyouming(String zuoyouming) {
		this.zuoyouming = zuoyouming;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public Integer getMobileshow() {
		return mobileshow;
	}
	public void setMobileshow(Integer mobileshow) {
		this.mobileshow = mobileshow;
	}
	public Integer getEmailshow() {
		return emailshow;
	}
	public void setEmailshow(Integer emailshow) {
		this.emailshow = emailshow;
	}
	public Integer getQqshow() {
		return qqshow;
	}
	public void setQqshow(Integer qqshow) {
		this.qqshow = qqshow;
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
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
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
	public String getUpdateDateFormat() {
		return updateDateFormat;
	}
	public void setUpdateDateFormat(String updateDateFormat) {
		this.updateDateFormat = updateDateFormat;
	}

	@Override
	public String toString() {
		return "YyMobileUser{" +
				"userId=" + userId +
				", account='" + account + '\'' +
				", password='" + password + '\'' +
				", mobile='" + mobile + '\'' +
				", email='" + email + '\'' +
				", type=" + type +
				", company='" + company + '\'' +
				", status=" + status +
				", nickName='" + nickName + '\'' +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", district='" + district + '\'' +
				", zuoyouming='" + zuoyouming + '\'' +
				", introduce='" + introduce + '\'' +
				", picPath='" + picPath + '\'' +
				", mobileshow=" + mobileshow +
				", emailshow=" + emailshow +
				", qqshow=" + qqshow +
				", plshow=" + plshow +
				", scshow=" + scshow +
				", dzshow=" + dzshow +
				", qq='" + qq + '\'' +
				", age=" + age +
				", fbno=" + fbno +
				", lrno=" + lrno +
				", scno=" + scno +
				", dzno=" + dzno +
				", plno=" + plno +
				", gzgr=" + gzgr +
				", gzjg=" + gzjg +
				", newsurl='" + newsurl + '\'' +
				", headline='" + headline + '\'' +
				", updateDateFormat='" + updateDateFormat + '\'' +
				", uid='" + uid + '\'' +
				'}';
	}
}
