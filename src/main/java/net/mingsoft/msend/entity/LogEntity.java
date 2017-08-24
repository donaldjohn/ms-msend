package net.mingsoft.msend.entity;

import com.mingsoft.base.entity.BaseEntity;
import java.util.Date;

 /**
 * 发送日志实体
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-24 14:41:18<br/>
 * 历史修订：<br/>
 */
public class LogEntity extends BaseEntity {

	private static final long serialVersionUID = 1503556878953L;
	
	/**
	 * 
	 */
	private Integer logId; 
	/**
	 * 应用编号
	 */
	private Integer appId; 
	/**
	 * 时间
	 */
	private Date logDatetime; 
	/**
	 * 接收内容
	 */
	private String logContent; 
	/**
	 * 接收人
	 */
	private String logReceive; 
	/**
	 * 日志类型0邮件1短信
	 */
	private Integer logType; 
	
		
	/**
	 * 设置
	 */
	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	/**
	 * 获取
	 */
	public Integer getLogId() {
		return this.logId;
	}
	
	/**
	 * 设置应用编号
	 */
	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	/**
	 * 获取应用编号
	 */
	public Integer getAppId() {
		return this.appId;
	}
	
	/**
	 * 设置时间
	 */
	public void setLogDatetime(Date logDatetime) {
		this.logDatetime = logDatetime;
	}

	/**
	 * 获取时间
	 */
	public Date getLogDatetime() {
		return this.logDatetime;
	}
	
	/**
	 * 设置接收内容
	 */
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	/**
	 * 获取接收内容
	 */
	public String getLogContent() {
		return this.logContent;
	}
	
	/**
	 * 设置接收人
	 */
	public void setLogReceive(String logReceive) {
		this.logReceive = logReceive;
	}

	/**
	 * 获取接收人
	 */
	public String getLogReceive() {
		return this.logReceive;
	}
	
	/**
	 * 设置日志类型0邮件1短信
	 */
	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	/**
	 * 获取日志类型0邮件1短信
	 */
	public Integer getLogType() {
		return this.logType;
	}
	
}