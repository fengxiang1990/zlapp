package com.zl.app.data.model.customer;



public class YyMobilePeriodStudent {
	private Integer relationId;//课程表学生关系主键
	private String studentName;//学生名字
	private String picPath;//学生头像
	private Integer type;//1 请假 2 正常 3补假 4已上课
	private Integer totype;//家长提交的动态修改  1 请假 2 正常 3补假 4已上课，老师页面判断字段是否有值，如果有状态显示用此字段，显示审核按钮，如果为空则用type正常显示
	private String content;//留言内容
	private Integer parentId;//判断是否有值  如果有就是你的孩子，可以操作请假，空值的不能操作请假
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getRelationId() {
		return relationId;
	}
	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}
	public Integer getTotype() {
		return totype;
	}
	public void setTotype(Integer totype) {
		this.totype = totype;
	}
	
}
