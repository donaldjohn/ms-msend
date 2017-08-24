package net.mingsoft.msend.entity;

import com.mingsoft.base.entity.BaseEntity;
import java.util.Date;

 /**
 * 短信实体
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-24 17:52:29<br/>
 * 历史修订：<br/>
 */
public class SmsEntity extends BaseEntity {

	private static final long serialVersionUID = 1503568349405L;
	
	/**
	 * 应用编号
	 */
	private Integer appId; 
	/**
	 * 短信接口类型
	 */
	private String smsType; 
	/**
	 * 账号
	 */
	private String smsUsername; 
	/**
	 * 密码
	 */
	private String smsPassword; 
	/**
	 * 发送地址
	 */
	private String smsSendUrl; 
	/**
	 * 
	 */
	private String smsAccountUrl; 
	/**
	 * 短信平台后台管理地址
	 */
	private String smsManagerUrl; 
	/**
	 * 签名
	 */
	private String smsSignature; 
	/**
	 * 0启用 1禁用
	 */
	private Integer smsEnable; 
	
		
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
	 * 设置短信接口类型
	 */
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	/**
	 * 获取短信接口类型
	 */
	public String getSmsType() {
		return this.smsType;
	}
	
	/**
	 * 设置账号
	 */
	public void setSmsUsername(String smsUsername) {
		this.smsUsername = smsUsername;
	}

	/**
	 * 获取账号
	 */
	public String getSmsUsername() {
		return this.smsUsername;
	}
	
	/**
	 * 设置密码
	 */
	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	/**
	 * 获取密码
	 */
	public String getSmsPassword() {
		return this.smsPassword;
	}
	
	/**
	 * 设置发送地址
	 */
	public void setSmsSendUrl(String smsSendUrl) {
		this.smsSendUrl = smsSendUrl;
	}

	/**
	 * 获取发送地址
	 */
	public String getSmsSendUrl() {
		return this.smsSendUrl;
	}
	
	/**
	 * 设置
	 */
	public void setSmsAccountUrl(String smsAccountUrl) {
		this.smsAccountUrl = smsAccountUrl;
	}

	/**
	 * 获取
	 */
	public String getSmsAccountUrl() {
		return this.smsAccountUrl;
	}
	
	/**
	 * 设置短信平台后台管理地址
	 */
	public void setSmsManagerUrl(String smsManagerUrl) {
		this.smsManagerUrl = smsManagerUrl;
	}

	/**
	 * 获取短信平台后台管理地址
	 */
	public String getSmsManagerUrl() {
		return this.smsManagerUrl;
	}
	
	/**
	 * 设置签名
	 */
	public void setSmsSignature(String smsSignature) {
		this.smsSignature = smsSignature;
	}

	/**
	 * 获取签名
	 */
	public String getSmsSignature() {
		return this.smsSignature;
	}
	
	/**
	 * 设置0启用 1禁用
	 */
	public void setSmsEnable(Integer smsEnable) {
		this.smsEnable = smsEnable;
	}

	/**
	 * 获取0启用 1禁用
	 */
	public Integer getSmsEnable() {
		return this.smsEnable;
	}
	
}