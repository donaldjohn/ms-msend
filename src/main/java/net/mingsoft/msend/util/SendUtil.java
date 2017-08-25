package net.mingsoft.msend.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.util.SpringUtil;
import net.mingsoft.msend.biz.IMailBiz;
import net.mingsoft.msend.biz.ITemplateBiz;
import net.mingsoft.msend.constant.e.MailEnum;
import net.mingsoft.msend.constant.e.SendEnum;
import net.mingsoft.msend.constant.e.ThridEnum;
import net.mingsoft.msend.entity.MailEntity;
import net.mingsoft.msend.entity.SmsEntity;
import net.mingsoft.msend.entity.TemplateEntity;

public class SendUtil {

	private static final Logger LOG = Logger.getLogger(SendUtil.class);

	/**
	 * 发送
	 * 
	 * @param code
	 *            模板编码
	 * @param toUser
	 *            接收用户
	 * @param values
	 *            替换的内容参数
	 * @param type
	 *            发送类型sms|mail|
	 * @return
	 */
	public static boolean send(String code, String receive, Map<String, String> values, String type) {
		ITemplateBiz templateBiz = (ITemplateBiz) SpringUtil.getBean(ITemplateBiz.class);
		TemplateEntity template = new TemplateEntity();
		template.setTemplateCode(code);
		template.setAppId(BasicUtil.getAppId());
		template = (TemplateEntity) templateBiz.getEntity(template);
		String mailContent = template.getTemplateMail();
		if (template.getTemplateId() > 0) {

			if (values != null) {

				Iterator it = values.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next() + "";
					if (values.get(key) instanceof String) {
						mailContent = mailContent.replaceAll("\\{" + key + "/\\}", values.get(key));
					}
				}
			}
			LOG.debug(code + "send  to:" + receive + " content:" + mailContent);
			// 如果实体不为空就获取邮箱模板的标题和内容一起发送指定的邮箱地址
		} else {
			LOG.error("发送模板不存在");
			return false;
		}

		if (type.equalsIgnoreCase(SendEnum.MAIL.toString())) {
			return SendUtil.sendMail(MailEnum.HTML, template.getTemplateTitle(), mailContent, receive.split(","));
		} else if (type.equalsIgnoreCase(SendEnum.SMS.toString())) {
			return SendUtil.sendSms(code, receive, values);
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
	public static boolean sendMail(MailEnum mailType, String title, String content, String[] toUser) {
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
	 * 
	 * @param code
	 *            模块编号，
	 * @param phone 接收手机号，多个手机号逗号隔开
	 * @param values
	 *            根据values.key值替换替换模版里面内容的{key/}，
	 */
	public static boolean sendSms(String code, String phone, Map<String, String> values) {
		IMailBiz mailBiz = (IMailBiz) SpringUtil.getBean(IMailBiz.class);
		SmsEntity sms = (SmsEntity) mailBiz.getEntity(BasicUtil.getAppId());
		ITemplateBiz templateBiz = (ITemplateBiz) SpringUtil.getBean(ITemplateBiz.class);
		TemplateEntity template = new TemplateEntity();
		template.setTemplateCode(code);
		template.setAppId(BasicUtil.getAppId());
		template = (TemplateEntity) templateBiz.getEntity(template);
		if (template.getTemplateId() > 0) {
			String content = template.getTemplateSms();
			if (values != null) {
				Iterator it = values.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next() + "";
					if (values.get(key) instanceof String) {
						content = content.replaceAll("\\{" + key + "/\\}", values.get(key));
					}
				}
			}
			LOG.debug(code + "send sms to:" + phone + " content:" + content);
			try {
				return SendcloudUtil.sendSms(sms.getSmsUsername(), sms.getSmsPassword(), 1, "0",phone,JSONArray.toJSONString(values));
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
