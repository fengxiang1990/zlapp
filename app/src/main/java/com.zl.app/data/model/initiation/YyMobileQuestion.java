package com.zl.app.data.model.initiation;


public class YyMobileQuestion {
	private Integer questionId;
	private String headline;//标题
	private String createDateFormat;//string 创建日期
	private String url;//webview url
	private Integer clickNo;//浏览数量
	private Integer replyNum;//浏览数量
	
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getCreateDateFormat() {
		return createDateFormat;
	}
	public void setCreateDateFormat(String createDateFormat) {
		this.createDateFormat = createDateFormat;
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
	public Integer getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}
	
}
