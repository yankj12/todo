package com.yan.todo.schema;

import java.util.Date;

import lombok.Data;

@Data
public class Meeting {

	private Long id;

	/**
	 * 会议日期
	 */
	private Date meetingDay;
	
	/**
	 * 会议标题
	 */
	private String title;

	/**
	 * 会议类型
	 */
	private String meetingType;
	
	/**
	 * 项目名称
	 */
	private String projectName;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 备注
	 */
	private String remark;

	private String validStatus;
	
	private Date insertTime;
	
	private Date updateTime;

}
