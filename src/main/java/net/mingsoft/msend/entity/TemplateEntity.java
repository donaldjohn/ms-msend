package net.mingsoft.msend.entity;

import com.mingsoft.base.entity.BaseEntity;
import java.util.Date;

 /**
 * 发送消息模板表实体
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-24 14:41:18<br/>
 * 历史修订：<br/>
 */
public class TemplateEntity extends BaseEntity {

	private static final long serialVersionUID = 1503556878950L;
	
	/**
	 * 编号
	 */
	private Integer templateId; 
	/**
	 * 模块编号
	 */
	private Integer modelId; 
	/**
	 * 应用编号
	 */
	private Integer appId; 
	/**
	 * 模板内容
	 */
	private String templateContent; 
	/**
	 * 标题
	 */
	private String templateTitle; 
	/**
	 * 邮件模块代码
	 */
	private String templateCode; 
	
		
	/**
	 * 设置编号
	 */
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	/**
	 * 获取编号
	 */
	public Integer getTemplateId() {
		return this.templateId;
	}
	
	/**
	 * 设置模块编号
	 */
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	/**
	 * 获取模块编号
	 */
	public Integer getModelId() {
		return this.modelId;
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
	 * 设置模板内容
	 */
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	/**
	 * 获取模板内容
	 */
	public String getTemplateContent() {
		return this.templateContent;
	}
	
	/**
	 * 设置标题
	 */
	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	/**
	 * 获取标题
	 */
	public String getTemplateTitle() {
		return this.templateTitle;
	}
	
	/**
	 * 设置邮件模块代码
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	/**
	 * 获取邮件模块代码
	 */
	public String getTemplateCode() {
		return this.templateCode;
	}
	
}