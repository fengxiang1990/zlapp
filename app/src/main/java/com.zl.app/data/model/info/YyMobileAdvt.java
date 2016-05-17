package com.zl.app.data.model.info;

public class YyMobileAdvt {
	private String picPath; //图片路径  以 /upload开头   
	private String headline; //标题
	private String linkUrl;//广告链接
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
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
}
