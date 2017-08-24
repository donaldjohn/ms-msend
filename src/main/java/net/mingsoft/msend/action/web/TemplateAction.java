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
import net.mingsoft.msend.biz.ITemplateBiz;
import net.mingsoft.msend.entity.TemplateEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import net.mingsoft.basic.util.BasicUtil;
	
/**
 * 发送消息模板表管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-24 14:41:18<br/>
 * 历史修订：<br/>
 */
@Controller("webTemplateAction")
@RequestMapping("/msend/template")
public class TemplateAction extends net.mingsoft.msend.action.BaseAction{
	
	/**
	 * 注入发送消息模板表业务层
	 */	
	@Autowired
	private ITemplateBiz templateBiz;
	
	
	/**
	 * 查询发送消息模板表列表
	 * @param template 发送消息模板表实体
	 * <i>template参数包含字段信息参考：</i><br/>
	 * templateId 编号<br/>
	 * modelId 模块编号<br/>
	 * appId 应用编号<br/>
	 * templateContent 模板内容<br/>
	 * templateTitle 标题<br/>
	 * templateCode 邮件模块代码<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * templateId: 编号<br/>
	 * modelId: 模块编号<br/>
	 * appId: 应用编号<br/>
	 * templateContent: 模板内容<br/>
	 * templateTitle: 标题<br/>
	 * templateCode: 邮件模块代码<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute TemplateEntity template,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		BasicUtil.startPage();
		List templateList = templateBiz.query(template);
		BasicUtil.endPage(templateList);
		this.outJson(response, JSONArray.toJSONStringWithDateFormat(templateList, "yyyy-MM-dd"));
	}
	
	
	/**
	 * 获取发送消息模板表
	 * @param template 发送消息模板表实体
	 * <i>template参数包含字段信息参考：</i><br/>
	 * templateId 编号<br/>
	 * modelId 模块编号<br/>
	 * appId 应用编号<br/>
	 * templateContent 模板内容<br/>
	 * templateTitle 标题<br/>
	 * templateCode 邮件模块代码<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * templateId: 编号<br/>
	 * modelId: 模块编号<br/>
	 * appId: 应用编号<br/>
	 * templateContent: 模板内容<br/>
	 * templateTitle: 标题<br/>
	 * templateCode: 邮件模块代码<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute TemplateEntity template,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(template.getTemplateId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("template.id")));
			return;
		}
		TemplateEntity _template = (TemplateEntity)templateBiz.getEntity(template.getTemplateId());
		this.outJson(response, _template);
	}
	
	/**
	 * 保存发送消息模板表实体
	 * @param template 发送消息模板表实体
	 * <i>template参数包含字段信息参考：</i><br/>
	 * templateId 编号<br/>
	 * modelId 模块编号<br/>
	 * appId 应用编号<br/>
	 * templateContent 模板内容<br/>
	 * templateTitle 标题<br/>
	 * templateCode 邮件模块代码<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * templateId: 编号<br/>
	 * modelId: 模块编号<br/>
	 * appId: 应用编号<br/>
	 * templateContent: 模板内容<br/>
	 * templateTitle: 标题<br/>
	 * templateCode: 邮件模块代码<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute TemplateEntity template, HttpServletResponse response, HttpServletRequest request) {
		//验证模块编号的值是否合法			
		if(StringUtil.isBlank(template.getModelId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("model.id")));
			return;			
		}
		if(!StringUtil.checkLength(template.getModelId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("model.id"), "1", "10"));
			return;			
		}
		templateBiz.saveEntity(template);
		this.outJson(response, template);
	}

	/**
	 * @param template 发送消息模板表实体
	 * <i>template参数包含字段信息参考：</i><br/>
	 * templateId:多个templateId直接用逗号隔开,例如templateId=1,2,3,4
	 * 批量删除发送消息模板表
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@ModelAttribute TemplateEntity template,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = BasicUtil.getInts("templateId");
		if(ids==null){
			this.outJson(response,null, false);
			return;
		}
		templateBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新发送消息模板表信息发送消息模板表
	 * @param template 发送消息模板表实体
	 * <i>template参数包含字段信息参考：</i><br/>
	 * templateId 编号<br/>
	 * modelId 模块编号<br/>
	 * appId 应用编号<br/>
	 * templateContent 模板内容<br/>
	 * templateTitle 标题<br/>
	 * templateCode 邮件模块代码<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * templateId: 编号<br/>
	 * modelId: 模块编号<br/>
	 * appId: 应用编号<br/>
	 * templateContent: 模板内容<br/>
	 * templateTitle: 标题<br/>
	 * templateCode: 邮件模块代码<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	public void update(@ModelAttribute TemplateEntity template, HttpServletResponse response,
			HttpServletRequest request) {
		//验证模块编号的值是否合法			
		if(StringUtil.isBlank(template.getModelId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("model.id")));
			return;			
		}
		if(!StringUtil.checkLength(template.getModelId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("model.id"), "1", "10"));
			return;			
		}
		templateBiz.updateEntity(template);
		this.outJson(response, template);
	}
	
		
}