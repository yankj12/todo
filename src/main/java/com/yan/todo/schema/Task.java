package com.yan.todo.schema;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 任务标题
	 */
	private String title;

	/**
	 * 紧急任务标志
	 * 0不紧急
	 * 1紧急
	 */
	private String emergencyFlag;

	/**
	 * 任务内容
	 */
	private String content;

	/**
	 * 用户编码
	 */
	private String userCode;

	/**
	 * 指派给
	 */
	private String assignTo;
	
	/**
	 * 截止时间
	 */
	private Date deadLine;
	
	/**
	 * 预期开始时间
	 */
	private Date startTime;

	/**
	 * 预期结束时间
	 */
	private Date endTime;

	/**
	 * 任务完成标志
	 */
	private String finishFlag;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 有效状态
	 */
	private String validStatus;

	/**
	 * 插入时间
	 */
	private Date insertTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmergencyFlag() {
		return emergencyFlag;
	}

	public void setEmergencyFlag(String emergencyFlag) {
		this.emergencyFlag = emergencyFlag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFinishFlag() {
		return finishFlag;
	}

	public void setFinishFlag(String finishFlag) {
		this.finishFlag = finishFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	
}
