package com.zl.app.model.info;


public class YyMobileNewsComment {
	private Integer commentId;//主键
	private Integer newsId;//新闻主键
	private Integer userId;//评论人userId
	private String username;//评论人网名
	private String picPath;//评论人头像
	private String content;//评论内容
	private String yycontent;//引用评论内容
	private Integer yyuserId;//引用评论人userId
	private String yyusername;//引用评论人网名
	private String createDate;//评论时间
	private String yydate;//引用评论的评论时间
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getYycontent() {
		return yycontent;
	}
	public void setYycontent(String yycontent) {
		this.yycontent = yycontent;
	}
	public Integer getYyuserId() {
		return yyuserId;
	}
	public void setYyuserId(Integer yyuserId) {
		this.yyuserId = yyuserId;
	}
	public String getYyusername() {
		return yyusername;
	}
	public void setYyusername(String yyusername) {
		this.yyusername = yyusername;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getYydate() {
		return yydate;
	}
	public void setYydate(String yydate) {
		this.yydate = yydate;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
}
