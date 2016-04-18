package com.zl.app.model.customer;


public class YyMobilePeriod {
	private Integer periodId;//课程表ID
	private Integer companyId;//机构Id
	private String picPath;//机构LOGO
	private String classtime;//上课时间
	private String classname;//班级名称
	private String periodname;//课程名称
	private String studentname;//学生姓名
	private Integer classId;//班级ID
	private Integer status;//课程表状态 2 正常 3取消 4已上课
	private Integer teacherId;//老师ID
	private String teachername;//老师名字
	public Integer getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Integer periodId) {
		this.periodId = periodId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getClasstime() {
		return classtime;
	}

	public void setClasstime(String classtime) {
		this.classtime = classtime;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getStudentname() {
		return studentname;
	}
	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getPeriodname() {
		return periodname;
	}
	public void setPeriodname(String periodname) {
		this.periodname = periodname;
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
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeachername() {
		return teachername;
	}
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	
}
