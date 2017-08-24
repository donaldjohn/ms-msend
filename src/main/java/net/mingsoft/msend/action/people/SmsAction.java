package net.mingsoft.msend.action.people;

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
import net.mingsoft.msend.biz.ISmsBiz;
import net.mingsoft.msend.entity.SmsEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import net.mingsoft.basic.util.BasicUtil;
	
/**
 * 暂无描述管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-24 14:41:18<br/>
 * 历史修订：<br/>
 */
@Controller("peopleSmsAction")
@RequestMapping("/people/msend/sms")
public class SmsAction extends net.mingsoft.people.action.BaseAction{
	
	/**
	 * 注入暂无描述业务层
	 */	
	@Autowired
	private ISmsBiz smsBiz;
	
	
	/**
	 * 查询暂无描述列表
	 * @param sms 暂无描述实体
	 * <i>sms参数包含字段信息参考：</i><br/>
	 * appId 应用编号<br/>
	 * smsType 短信接口类型<br/>
	 * smsUsername 账号<br/>
	 * smsPassword 密码<br/>
	 * smsSendUrl 发送地址<br/>
	 * smsAccountUrl <br/>
	 * smsManagerUrl 短信平台后台管理地址<br/>
	 * smsSignature 签名<br/>
	 * smsEnable 0启用 1禁用<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * appId: 应用编号<br/>
	 * smsType: 短信接口类型<br/>
	 * smsUsername: 账号<br/>
	 * smsPassword: 密码<br/>
	 * smsSendUrl: 发送地址<br/>
	 * smsAccountUrl: <br/>
	 * smsManagerUrl: 短信平台后台管理地址<br/>
	 * smsSignature: 签名<br/>
	 * smsEnable: 0启用 1禁用<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute SmsEntity sms,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		BasicUtil.startPage();
		List smsList = smsBiz.query(sms);
		BasicUtil.endPage(smsList);
		this.outJson(response, JSONArray.toJSONStringWithDateFormat(smsList, "yyyy-MM-dd"));
	}
	
	
	/**
	 * 获取暂无描述
	 * @param sms 暂无描述实体
	 * <i>sms参数包含字段信息参考：</i><br/>
	 * appId 应用编号<br/>
	 * smsType 短信接口类型<br/>
	 * smsUsername 账号<br/>
	 * smsPassword 密码<br/>
	 * smsSendUrl 发送地址<br/>
	 * smsAccountUrl <br/>
	 * smsManagerUrl 短信平台后台管理地址<br/>
	 * smsSignature 签名<br/>
	 * smsEnable 0启用 1禁用<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * appId: 应用编号<br/>
	 * smsType: 短信接口类型<br/>
	 * smsUsername: 账号<br/>
	 * smsPassword: 密码<br/>
	 * smsSendUrl: 发送地址<br/>
	 * smsAccountUrl: <br/>
	 * smsManagerUrl: 短信平台后台管理地址<br/>
	 * smsSignature: 签名<br/>
	 * smsEnable: 0启用 1禁用<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute SmsEntity sms,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(sms.getAppId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("app.id")));
			return;
		}
		SmsEntity _sms = (SmsEntity)smsBiz.getEntity(sms.getAppId());
		this.outJson(response, _sms);
	}
	
	/**
	 * 保存暂无描述实体
	 * @param sms 暂无描述实体
	 * <i>sms参数包含字段信息参考：</i><br/>
	 * appId 应用编号<br/>
	 * smsType 短信接口类型<br/>
	 * smsUsername 账号<br/>
	 * smsPassword 密码<br/>
	 * smsSendUrl 发送地址<br/>
	 * smsAccountUrl <br/>
	 * smsManagerUrl 短信平台后台管理地址<br/>
	 * smsSignature 签名<br/>
	 * smsEnable 0启用 1禁用<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * appId: 应用编号<br/>
	 * smsType: 短信接口类型<br/>
	 * smsUsername: 账号<br/>
	 * smsPassword: 密码<br/>
	 * smsSendUrl: 发送地址<br/>
	 * smsAccountUrl: <br/>
	 * smsManagerUrl: 短信平台后台管理地址<br/>
	 * smsSignature: 签名<br/>
	 * smsEnable: 0启用 1禁用<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute SmsEntity sms, HttpServletResponse response, HttpServletRequest request) {
		//验证短信接口类型的值是否合法			
		if(StringUtil.isBlank(sms.getSmsType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("sms.type")));
			return;			
		}
		if(!StringUtil.checkLength(sms.getSmsType()+"", 1, 150)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("sms.type"), "1", "150"));
			return;			
		}
		//验证短信平台后台管理地址的值是否合法			
		if(StringUtil.isBlank(sms.getSmsManagerUrl())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("sms.manager.url")));
			return;			
		}
		if(!StringUtil.checkLength(sms.getSmsManagerUrl()+"", 1, 120)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("sms.manager.url"), "1", "120"));
			return;			
		}
		//验证0启用 1禁用的值是否合法			
		if(StringUtil.isBlank(sms.getSmsEnable())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("sms.enable")));
			return;			
		}
		if(!StringUtil.checkLength(sms.getSmsEnable()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("sms.enable"), "1", "10"));
			return;			
		}
		smsBiz.saveEntity(sms);
		this.outJson(response, sms);
	}

	/**
	 * @param sms 暂无描述实体
	 * <i>sms参数包含字段信息参考：</i><br/>
	 * appId:多个appId直接用逗号隔开,例如appId=1,2,3,4
	 * 批量删除暂无描述
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@ModelAttribute SmsEntity sms,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = BasicUtil.getInts("appId");
		if(ids==null){
			this.outJson(response,null, false);
			return;
		}
		smsBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新暂无描述信息暂无描述
	 * @param sms 暂无描述实体
	 * <i>sms参数包含字段信息参考：</i><br/>
	 * appId 应用编号<br/>
	 * smsType 短信接口类型<br/>
	 * smsUsername 账号<br/>
	 * smsPassword 密码<br/>
	 * smsSendUrl 发送地址<br/>
	 * smsAccountUrl <br/>
	 * smsManagerUrl 短信平台后台管理地址<br/>
	 * smsSignature 签名<br/>
	 * smsEnable 0启用 1禁用<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * appId: 应用编号<br/>
	 * smsType: 短信接口类型<br/>
	 * smsUsername: 账号<br/>
	 * smsPassword: 密码<br/>
	 * smsSendUrl: 发送地址<br/>
	 * smsAccountUrl: <br/>
	 * smsManagerUrl: 短信平台后台管理地址<br/>
	 * smsSignature: 签名<br/>
	 * smsEnable: 0启用 1禁用<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	public void update(@ModelAttribute SmsEntity sms, HttpServletResponse response,
			HttpServletRequest request) {
		//验证短信接口类型的值是否合法			
		if(StringUtil.isBlank(sms.getSmsType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("sms.type")));
			return;			
		}
		if(!StringUtil.checkLength(sms.getSmsType()+"", 1, 150)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("sms.type"), "1", "150"));
			return;			
		}
		//验证短信平台后台管理地址的值是否合法			
		if(StringUtil.isBlank(sms.getSmsManagerUrl())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("sms.manager.url")));
			return;			
		}
		if(!StringUtil.checkLength(sms.getSmsManagerUrl()+"", 1, 120)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("sms.manager.url"), "1", "120"));
			return;			
		}
		//验证0启用 1禁用的值是否合法			
		if(StringUtil.isBlank(sms.getSmsEnable())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("sms.enable")));
			return;			
		}
		if(!StringUtil.checkLength(sms.getSmsEnable()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("sms.enable"), "1", "10"));
			return;			
		}
		smsBiz.updateEntity(sms);
		this.outJson(response, sms);
	}
	
		
}