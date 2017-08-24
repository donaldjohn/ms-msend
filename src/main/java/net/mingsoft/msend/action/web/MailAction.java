package net.mingsoft.msend.action.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.mingsoft.msend.biz.IMailBiz;
import net.mingsoft.msend.entity.MailEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import net.mingsoft.basic.util.BasicUtil;
	
/**
 * 邮件管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-24 14:41:18<br/>
 * 历史修订：<br/>
 */
@Controller("webMailAction")
@RequestMapping("/msend/mail")
public class MailAction extends net.mingsoft.msend.action.BaseAction{
	
	/**
	 * 注入邮件业务层
	 */	
	@Autowired
	private IMailBiz mailBiz;
	
	
	/**
	 * 查询邮件列表
	 * @param mail 邮件实体
	 * <i>mail参数包含字段信息参考：</i><br/>
	 * appId 应用编号<br/>
	 * mailType 邮件类型<br/>
	 * mailName 账号<br/>
	 * mailPassword <br/>
	 * mailPort <br/>
	 * mailServer 服务器<br/>
	 * mailForm <br/>
	 * mailFormName <br/>
	 * mailEnable 0启用 1禁用<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * appId: 应用编号<br/>
	 * mailType: 邮件类型<br/>
	 * mailName: 账号<br/>
	 * mailPassword: <br/>
	 * mailPort: <br/>
	 * mailServer: 服务器<br/>
	 * mailForm: <br/>
	 * mailFormName: <br/>
	 * mailEnable: 0启用 1禁用<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute MailEntity mail,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		BasicUtil.startPage();
		List mailList = mailBiz.query(mail);
		BasicUtil.endPage(mailList);
		this.outJson(response, JSONArray.toJSONStringWithDateFormat(mailList, "yyyy-MM-dd"));
	}
	
	
	/**
	 * 获取邮件
	 * @param mail 邮件实体
	 * <i>mail参数包含字段信息参考：</i><br/>
	 * appId 应用编号<br/>
	 * mailType 邮件类型<br/>
	 * mailName 账号<br/>
	 * mailPassword <br/>
	 * mailPort <br/>
	 * mailServer 服务器<br/>
	 * mailForm <br/>
	 * mailFormName <br/>
	 * mailEnable 0启用 1禁用<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * appId: 应用编号<br/>
	 * mailType: 邮件类型<br/>
	 * mailName: 账号<br/>
	 * mailPassword: <br/>
	 * mailPort: <br/>
	 * mailServer: 服务器<br/>
	 * mailForm: <br/>
	 * mailFormName: <br/>
	 * mailEnable: 0启用 1禁用<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute MailEntity mail,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(mail.getAppId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("app.id")));
			return;
		}
		MailEntity _mail = (MailEntity)mailBiz.getEntity(mail.getAppId());
		this.outJson(response, _mail);
	}
	
	/**
	 * 保存邮件实体
	 * @param mail 邮件实体
	 * <i>mail参数包含字段信息参考：</i><br/>
	 * appId 应用编号<br/>
	 * mailType 邮件类型<br/>
	 * mailName 账号<br/>
	 * mailPassword <br/>
	 * mailPort <br/>
	 * mailServer 服务器<br/>
	 * mailForm <br/>
	 * mailFormName <br/>
	 * mailEnable 0启用 1禁用<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * appId: 应用编号<br/>
	 * mailType: 邮件类型<br/>
	 * mailName: 账号<br/>
	 * mailPassword: <br/>
	 * mailPort: <br/>
	 * mailServer: 服务器<br/>
	 * mailForm: <br/>
	 * mailFormName: <br/>
	 * mailEnable: 0启用 1禁用<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute MailEntity mail, HttpServletResponse response, HttpServletRequest request) {
		//验证邮件类型的值是否合法			
		if(StringUtil.isBlank(mail.getMailType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("mail.type")));
			return;			
		}
		if(!StringUtil.checkLength(mail.getMailType()+"", 1, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("mail.type"), "1", "255"));
			return;			
		}
		//验证的值是否合法			
		if(StringUtil.isBlank(mail.getMailForm())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("mail.form")));
			return;			
		}
		if(!StringUtil.checkLength(mail.getMailForm()+"", 1, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("mail.form"), "1", "255"));
			return;			
		}
		//验证的值是否合法			
		if(StringUtil.isBlank(mail.getMailFormName())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("mail.form.name")));
			return;			
		}
		if(!StringUtil.checkLength(mail.getMailFormName()+"", 1, 0)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("mail.form.name"), "1", "0"));
			return;			
		}
		//验证0启用 1禁用的值是否合法			
		if(StringUtil.isBlank(mail.getMailEnable())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("mail.enable")));
			return;			
		}
		if(!StringUtil.checkLength(mail.getMailEnable()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("mail.enable"), "1", "10"));
			return;			
		}
		mailBiz.saveEntity(mail);
		this.outJson(response, mail);
	}

	/**
	 * @param mail 邮件实体
	 * <i>mail参数包含字段信息参考：</i><br/>
	 * appId:多个appId直接用逗号隔开,例如appId=1,2,3,4
	 * 批量删除邮件
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@ModelAttribute MailEntity mail,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = BasicUtil.getInts("appId");
		if(ids==null){
			this.outJson(response,null, false);
			return;
		}
		mailBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新邮件信息邮件
	 * @param mail 邮件实体
	 * <i>mail参数包含字段信息参考：</i><br/>
	 * appId 应用编号<br/>
	 * mailType 邮件类型<br/>
	 * mailName 账号<br/>
	 * mailPassword <br/>
	 * mailPort <br/>
	 * mailServer 服务器<br/>
	 * mailForm <br/>
	 * mailFormName <br/>
	 * mailEnable 0启用 1禁用<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * appId: 应用编号<br/>
	 * mailType: 邮件类型<br/>
	 * mailName: 账号<br/>
	 * mailPassword: <br/>
	 * mailPort: <br/>
	 * mailServer: 服务器<br/>
	 * mailForm: <br/>
	 * mailFormName: <br/>
	 * mailEnable: 0启用 1禁用<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	public void update(@ModelAttribute MailEntity mail, HttpServletResponse response,
			HttpServletRequest request) {
		//验证邮件类型的值是否合法			
		if(StringUtil.isBlank(mail.getMailType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("mail.type")));
			return;			
		}
		if(!StringUtil.checkLength(mail.getMailType()+"", 1, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("mail.type"), "1", "255"));
			return;			
		}
		//验证的值是否合法			
		if(StringUtil.isBlank(mail.getMailForm())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("mail.form")));
			return;			
		}
		if(!StringUtil.checkLength(mail.getMailForm()+"", 1, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("mail.form"), "1", "255"));
			return;			
		}
		//验证的值是否合法			
		if(StringUtil.isBlank(mail.getMailFormName())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("mail.form.name")));
			return;			
		}
		if(!StringUtil.checkLength(mail.getMailFormName()+"", 1, 0)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("mail.form.name"), "1", "0"));
			return;			
		}
		//验证0启用 1禁用的值是否合法			
		if(StringUtil.isBlank(mail.getMailEnable())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("mail.enable")));
			return;			
		}
		if(!StringUtil.checkLength(mail.getMailEnable()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("mail.enable"), "1", "10"));
			return;			
		}
		mailBiz.updateEntity(mail);
		this.outJson(response, mail);
	}
	
		
}