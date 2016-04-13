package com.zl.app.data.news.model;

public class YyMobileUserComment {
	private Integer commentId;//主键
	private Integer userId;//微站的userId
	private Integer formUserId;//留言的userId
	private String formusername;//留言人的网名
	private String content;//留言内容
	private String yycontent;//引用的内容
	private Integer yyuserId;//引用人的userId
	private String yyusername;//引用人的网名
	private String createDate;//留言时间
	private String yydate;//引用内容的留言时间

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	private String picPath;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getFormUserId() {
		return formUserId;
	}
	public void setFormUserId(Integer formUserId) {
		this.formUserId = formUserId;
	}
	public String getFormusername() {
		return formusername;
	}
	public void setFormusername(String formusername) {
		this.formusername = formusername;
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

	@Override
	public String toString() {
		return "YyMobileUserComment{" +
				"commentId=" + commentId +
				", userId=" + userId +
				", formUserId=" + formUserId +
				", formusername='" + formusername + '\'' +
				", content='" + content + '\'' +
				", yycontent='" + yycontent + '\'' +
				", yyuserId=" + yyuserId +
				", yyusername='" + yyusername + '\'' +
				", createDate='" + createDate + '\'' +
				", yydate='" + yydate + '\'' +
				'}';
	}
}
