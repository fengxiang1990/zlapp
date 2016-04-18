package com.zl.app.model.customer;



public class YyMobilePeriodStudent {
	private Integer relationId;//课程表学生关系主键
	private Integer periodId;//课程ID
	private String periodName;//课程名称
	private Integer teacherId;//老师ID
	private String teacherName;//老师名字
	private Integer studentId;//学生ID
	private String studentName;//学生名字
	private String picPath;//学生头像
	private Integer status;//2 正常 3取消 4已上课
	private Integer type;//1 请假 2 正常 3补假 4已上课
	private Integer totype;//家长提交的动态修改  1 请假 2 正常 3补假 4已上课，老师页面判断字段是否有值，如果有状态显示用此字段，显示审核按钮，如果为空则用type正常显示
	private String content;//留言内容
	private String classtime;//上课时间
	public Integer getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Integer periodId) {
		this.periodId = periodId;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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

	public String getClasstime() {
		return classtime;
	}

	public void setClasstime(String classtime) {
		this.classtime = classtime;
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
