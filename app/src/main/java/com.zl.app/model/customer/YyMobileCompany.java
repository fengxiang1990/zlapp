package com.zl.app.model.customer;


public class YyMobileCompany {
	private Integer companyId;//主键
	private String address;//地址
	private String phone;//电话
	private String companyname;//名称
//	private String lng;//百度经度
//	private String lat;//百度纬度
	private String distance;//距离
	private String typeName;//分类
	private String picPath;//LOGO图片
	private Double grade;//评分（平均）
	private Integer hpno;//好评次数 10分
	private Integer zpno;//中评次数 6 8分
	private Integer cpno;//差评次数 2 4分
	private String zhaiyao;//摘要
	private Integer plactive;//1 不能评价 2 可以评价
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public Double getGrade() {
		return grade;
	}
	public void setGrade(Double grade) {
		this.grade = grade;
	}
	public Integer getHpno() {
		return hpno;
	}
	public void setHpno(Integer hpno) {
		this.hpno = hpno;
	}
	public Integer getZpno() {
		return zpno;
	}
	public void setZpno(Integer zpno) {
		this.zpno = zpno;
	}
	public Integer getCpno() {
		return cpno;
	}
	public void setCpno(Integer cpno) {
		this.cpno = cpno;
	}
	public String getZhaiyao() {
		return zhaiyao;
	}
	public void setZhaiyao(String zhaiyao) {
		this.zhaiyao = zhaiyao;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public Integer getPlactive() {
		return plactive;
	}
	public void setPlactive(Integer plactive) {
		this.plactive = plactive;
	}

}
