package com.yan.todo.schema;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
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
	private Date startTimeExpected;

	/**
	 * 预期结束时间
	 */
	private Date endTimeExpected;
	/**
	 * 实际开始时间
	 */
	private Date startTimeActual;

	/**
	 * 实际结束时间
	 */
	private Date endTimeActual;

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
	
}
