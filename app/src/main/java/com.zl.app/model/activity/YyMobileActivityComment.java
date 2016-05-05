package com.zl.app.model.activity;


public class YyMobileActivityComment {
	private Integer commentId;//主键
	private Integer userId;//留言人id
	private String username;//留言人昵称
	private String userPic;//留言人头像
	private String picPath;
	private String content;//留言内容
	private Integer yyuser;//回复人ID
	private String createDate;//留言时间
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getYyuser() {
		return yyuser;
	}
	public void setYyuser(Integer yyuser) {
		this.yyuser = yyuser;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}
}
