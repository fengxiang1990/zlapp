package com.zl.app.model.info;

import java.text.ParseException;
import java.util.Date;

@SuppressWarnings("unused")
public class YyMobileNews {
	private String headline;//标题
	private String zhaiyao;//摘要
	private Date createDate;//创建时间
	private String picPath;//图片路径  以 /upload开头   
	private String url;//新闻唯一码
	private String name;//新闻分类名称
	private String code;//新闻大分类标识
	private String value;//新闻小分类标识
	private Integer clickNo;//浏览数量
	private Integer commentNo;//评论数量
	private Integer goodNo;//点赞数量
	private Integer favoriteNo;//收藏数量
	private Integer userId;//发表人ID
	private String username;//发表人姓名
	private String content;//新闻内容
	
	public Integer getClickNo() {
		return clickNo;
	}
	public void setClickNo(Integer clickNo) {
		this.clickNo = clickNo;
	}
	public Integer getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(Integer commentNo) {
		this.commentNo = commentNo;
	}
	public Integer getGoodNo() {
		return goodNo;
	}
	public void setGoodNo(Integer goodNo) {
		this.goodNo = goodNo;
	}
	public Integer getFavoriteNo() {
		return favoriteNo;
	}
	public void setFavoriteNo(Integer favoriteNo) {
		this.favoriteNo = favoriteNo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getZhaiyao() {
		if(zhaiyao!=null&&zhaiyao.length()>50){
			return this.zhaiyao.substring(0, 50)+"...";
		}else{
			return this.zhaiyao;
		}
	}
	public void setZhaiyao(String zhaiyao) {
		this.zhaiyao = zhaiyao;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private String createDateFormat;
	public String getCreateDateFormat() {
		return createDateFormat;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
