package com.zl.app.data.model.user;


public class YyMobileLetter {
	private Integer contentId;//主键
	private Integer createBy;//发送人id
	private String createName;//发送人名称
	private String createDate;//发送日期
	private String content;//内容
	private Integer status;//3 未读 2已读
	private String picPath;//发送人头像
	private String contentPic;//发送的图片路径
	
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public String getContentPic() {
		return contentPic;
	}
	public void setContentPic(String contentPic) {
		this.contentPic = contentPic;
	}
	public Integer getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	@Override
	public String toString() {
		return "YyMobileLetter{" +
				"contentId=" + contentId +
				", createBy=" + createBy +
				", createName='" + createName + '\'' +
				", createDate='" + createDate + '\'' +
				", content='" + content + '\'' +
				", status=" + status +
				", picPath='" + picPath + '\'' +
				", contentPic='" + contentPic + '\'' +
				'}';
	}
}
