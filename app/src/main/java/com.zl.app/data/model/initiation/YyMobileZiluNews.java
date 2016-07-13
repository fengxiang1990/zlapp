package com.zl.app.data.model.initiation;


public class YyMobileZiluNews {
	private String headline;//标题
	private String zhaiyao;//摘要
	private String createDateFormat;//string 创建日期
	private String picPath;//图片路径  
	private String url;//webview url
	private Integer clickNo;//浏览数量
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getZhaiyao() {
		return zhaiyao;
	}
	public void setZhaiyao(String zhaiyao) {
		this.zhaiyao = zhaiyao;
	}
	public String getCreateDateFormat() {
		return createDateFormat;
	}
	public void setCreateDateFormat(String createDateFormat) {
		this.createDateFormat = createDateFormat;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getClickNo() {
		return clickNo;
	}
	public void setClickNo(Integer clickNo) {
		this.clickNo = clickNo;
	}
	
}
