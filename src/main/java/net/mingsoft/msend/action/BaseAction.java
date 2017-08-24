package net.mingsoft.msend.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.util.SpringUtil;
import net.mingsoft.msend.biz.IMailBiz;
import net.mingsoft.msend.biz.ISmsBiz;
import net.mingsoft.msend.biz.ITemplateBiz;
import net.mingsoft.msend.constant.e.MailEnum;
import net.mingsoft.msend.constant.e.SendEnum;
import net.mingsoft.msend.constant.e.ThridEnum;
import net.mingsoft.msend.entity.MailEntity;
import net.mingsoft.msend.entity.SmsEntity;
import net.mingsoft.msend.entity.TemplateEntity;
import net.mingsoft.msend.util.MailUtil;
import net.mingsoft.msend.util.SendcloudUtil;

/**
 * msend基础控制层
 * 
 * @author 伍晶晶
 * @version 版本号：0.0<br/>
 *          创建日期：2017-8-24 14:41:18<br/>
 *          历史修订：<br/>
 */
public class BaseAction extends com.mingsoft.basic.action.BaseAction {

	@Override
	protected String getResString(String key) {
		// TODO Auto-generated method stub
		String str = "";
		try {
			str = super.getResString(key);
		} catch (MissingResourceException e) {
			str = net.mingsoft.msend.constant.Const.RESOURCES.getString(key);
		}

		return str;
	}

	protected boolean send(String code, String[] toUser, Map<String, String> values, String type) {
		ITemplateBiz templateBiz = (ITemplateBiz) SpringUtil.getBean(ITemplateBiz.class);
		TemplateEntity template = new TemplateEntity();
		template.setTemplateCode(code);
		template.setAppId(BasicUtil.getAppId());
		template = (TemplateEntity) templateBiz.getEntity(template);
		String content = template.getTemplateContent();
		if (template.getTemplateId() > 0) {
			
			if (values != null) {

				Iterator it = values.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next() + "";
					if (values.get(key) instanceof String) {
						content = content.replaceAll("\\{" + key + "/\\}", values.get(key));
					}
				}
			}
			LOG.debug(code + "send  to:" + toUser + " content:" + content);
			// 如果实体不为空就获取邮箱模板的标题和内容一起发送指定的邮箱地址
		} else {
			LOG.error("发送模板不存在");
			return false;
		}
		
		if (type.equalsIgnoreCase(SendEnum.MAIL.toString())) {
			 return this.sendMail(MailEnum.HTML, template.getTemplateTitle(), content, toUser);
		} else if (type.equalsIgnoreCase(SendEnum.SMS.toString())) {
			this.sendSms(code, toUser, values);
		} else {

		}
		return true;
	}

	/**
	 * 发送邮件
	 * 
	 * @param mailType
	 *            邮件类型(MailEntity.TEXT MailEntity.HTML)
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param toUser
	 *            接收用户
	 */
	protected boolean sendMail(MailEnum mailType, String title, String content, String[] toUser) {
		IMailBiz mailBiz = (IMailBiz) SpringUtil.getBean(IMailBiz.class);
		MailEntity mail = (MailEntity) mailBiz.getEntity(BasicUtil.getAppId());
		if (mail == null) {
			LOG.error("没有配置邮件服务器");
			return false;
		}
		if (mail.getMailType().equals(ThridEnum.SENDCLOUD.toString())) {
			try {
				String _toUser = "";
				for (int i = 0; i < toUser.length; i++) {
					if (StringUtil.isEmail(toUser[i])) {
						_toUser += toUser[i];
						if (i < toUser.length) {
							_toUser += ";";
						}
					}
				}
				return SendcloudUtil.sendMail(mail.getMailName(), mail.getMailPassword(), mail.getMailForm(),
						mail.getMailFormName(), _toUser, title, content);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (mailType.toInt() == MailEnum.TEXT.toInt()) {
				MailUtil.sendText(mail.getMailServer(), mail.getMailPort(), mail.getMailName(), mail.getMailPassword(),
						title, content, toUser);
			} else if (mailType.toInt() == MailEnum.HTML.toInt()) {
				MailUtil.sendHtml(mail.getMailServer(), mail.getMailPort(), mail.getMailName(), mail.getMailPassword(),
						title, content, toUser);
			}
		}
		return false;

	}

	/**
	 * 发送html邮件内容,谨慎使用
	 * 
	 * @param code
	 *            模块编号，
	 * @param toUser
	 *            接收用户邮件地址
	 * @param values
	 *            根据values.key值替换替换模版里面内容的{key/}，
	 */
	protected boolean sendMail(String code, String[] toUser, Map<String, String> values) {
		ITemplateBiz templateBiz = (ITemplateBiz) SpringUtil.getBean(ITemplateBiz.class);
		TemplateEntity template = new TemplateEntity();
		template.setTemplateCode(code);
		template.setAppId(BasicUtil.getAppId());
		template = (TemplateEntity) templateBiz.getEntity(template);
		if (template.getTemplateId() > 0) {
			String content = template.getTemplateContent();
			if (values != null) {

				Iterator it = values.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next() + "";
					if (values.get(key) instanceof String) {
						content = content.replaceAll("\\{" + key + "/\\}", values.get(key));
					}
				}
			}
			LOG.debug(code + "send mail to:" + toUser + " content:" + content);
			// 如果实体不为空就获取邮箱模板的标题和内容一起发送指定的邮箱地址
			this.sendMail(MailEnum.HTML, template.getTemplateTitle(), content, toUser);
			
			return true;
		} else {
			LOG.debug("code is no exists");
			return false;
		}
	}
	/**
	 * 
	 * @param code
	 *            模块编号，
	 * @param toUser
	 *            接收用户邮件地址
	 * @param values
	 *            根据values.key值替换替换模版里面内容的{key/}，
	 */
	protected boolean sendSms(String code, String[] toUser, Map<String, String> values) {
		IMailBiz mailBiz = (IMailBiz) SpringUtil.getBean(IMailBiz.class);
		SmsEntity sms = (SmsEntity) mailBiz.getEntity(BasicUtil.getAppId());
		ITemplateBiz templateBiz = (ITemplateBiz) SpringUtil.getBean(ITemplateBiz.class);
		TemplateEntity template = new TemplateEntity();
		template.setTemplateCode(code);
		template.setAppId(BasicUtil.getAppId());
		template = (TemplateEntity) templateBiz.getEntity(template);
		if (template.getTemplateId() > 0) {
			String content = template.getTemplateContent();
			if (values != null) {
				Iterator it = values.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next() + "";
					if (values.get(key) instanceof String) {
						content = content.replaceAll("\\{" + key + "/\\}", values.get(key));
					}
				}
			}
			LOG.debug(code + "send sms to:" + toUser + " content:" + content);
			try {
				return SendcloudUtil.sendSms(sms.getSmsUsername(), sms.getSmsPassword(), 1, "0", toUser.toString(),JSONArray.toJSONString(values));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		} else {
			LOG.debug("code is no exists");
			return false;
		}
	}
}
