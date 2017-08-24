package net.mingsoft.msend.entity;

import com.mingsoft.base.entity.BaseEntity;
import java.util.Date;

 /**
 * 邮件实体
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-24 14:41:18<br/>
 * 历史修订：<br/>
 */
public class MailEntity extends BaseEntity {

	private static final long serialVersionUID = 1503556878920L;
	
	/**
	 * 应用编号
	 */
	private Integer appId; 
	/**
	 * 邮件类型
	 */
	private String mailType; 
	/**
	 * 账号
	 */
	private String mailName; 
	/**
	 * 
	 */
	private String mailPassword; 
	/**
	 * 
	 */
	private Integer mailPort; 
	/**
	 * 服务器
	 */
	private String mailServer; 
	/**
	 * 
	 */
	private String mailForm; 
	/**
	 * 
	 */
	private String mailFormName; 
	/**
	 * 0启用 1禁用
	 */
	private Integer mailEnable; 
	
	public MailEntity(){}
	public MailEntity(Integer appId) {
	this.appId = appId;	
	}
	
	public MailEntity(String mailType) {
		this.mailType = mailType;	
	}
	
	public MailEntity(String mailType,String mailName) {
		this.mailType = mailType;		this.mailName = mailName;	
	}
	
	public MailEntity(String mailType,String mailName,String mailPassword) {
		this.mailType = mailType;		this.mailName = mailName;		this.mailPassword = mailPassword;	
	}
	
	public MailEntity(String mailType,String mailName,String mailPassword,Integer mailPort) {
		this.mailType = mailType;		this.mailName = mailName;		this.mailPassword = mailPassword;		this.mailPort = mailPort;	
	}
	
	public MailEntity(String mailType,String mailName,String mailPassword,Integer mailPort,String mailServer) {
		this.mailType = mailType;		this.mailName = mailName;		this.mailPassword = mailPassword;		this.mailPort = mailPort;		this.mailServer = mailServer;	
	}
	
	public MailEntity(String mailType,String mailName,String mailPassword,Integer mailPort,String mailServer,String mailForm) {
		this.mailType = mailType;		this.mailName = mailName;		this.mailPassword = mailPassword;		this.mailPort = mailPort;		this.mailServer = mailServer;		this.mailForm = mailForm;	
	}
	
	public MailEntity(String mailType,String mailName,String mailPassword,Integer mailPort,String mailServer,String mailForm,String mailFormName) {
		this.mailType = mailType;		this.mailName = mailName;		this.mailPassword = mailPassword;		this.mailPort = mailPort;		this.mailServer = mailServer;		this.mailForm = mailForm;		this.mailFormName = mailFormName;	
	}
	
	public MailEntity(String mailType,String mailName,String mailPassword,Integer mailPort,String mailServer,String mailForm,String mailFormName,Integer mailEnable) {
		this.mailType = mailType;		this.mailName = mailName;		this.mailPassword = mailPassword;		this.mailPort = mailPort;		this.mailServer = mailServer;		this.mailForm = mailForm;		this.mailFormName = mailFormName;		this.mailEnable = mailEnable;	
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
	 * 设置邮件类型
	 */
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	/**
	 * 获取邮件类型
	 */
	public String getMailType() {
		return this.mailType;
	}
	
	/**
	 * 设置账号
	 */
	public void setMailName(String mailName) {
		this.mailName = mailName;
	}

	/**
	 * 获取账号
	 */
	public String getMailName() {
		return this.mailName;
	}
	
	/**
	 * 设置
	 */
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	/**
	 * 获取
	 */
	public String getMailPassword() {
		return this.mailPassword;
	}
	
	/**
	 * 设置
	 */
	public void setMailPort(Integer mailPort) {
		this.mailPort = mailPort;
	}

	/**
	 * 获取
	 */
	public Integer getMailPort() {
		return this.mailPort;
	}
	
	/**
	 * 设置服务器
	 */
	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	/**
	 * 获取服务器
	 */
	public String getMailServer() {
		return this.mailServer;
	}
	
	/**
	 * 设置
	 */
	public void setMailForm(String mailForm) {
		this.mailForm = mailForm;
	}

	/**
	 * 获取
	 */
	public String getMailForm() {
		return this.mailForm;
	}
	
	/**
	 * 设置
	 */
	public void setMailFormName(String mailFormName) {
		this.mailFormName = mailFormName;
	}

	/**
	 * 获取
	 */
	public String getMailFormName() {
		return this.mailFormName;
	}
	
	/**
	 * 设置0启用 1禁用
	 */
	public void setMailEnable(Integer mailEnable) {
		this.mailEnable = mailEnable;
	}

	/**
	 * 获取0启用 1禁用
	 */
	public Integer getMailEnable() {
		return this.mailEnable;
	}
	
}