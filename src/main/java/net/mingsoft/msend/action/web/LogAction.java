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
import net.mingsoft.msend.biz.ILogBiz;
import net.mingsoft.msend.entity.LogEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import net.mingsoft.basic.util.BasicUtil;
	
/**
 * 发送日志管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-24 14:41:18<br/>
 * 历史修订：<br/>
 */
@Controller("webLogAction")
@RequestMapping("/msend/log")
public class LogAction extends net.mingsoft.msend.action.BaseAction{
	
	/**
	 * 注入发送日志业务层
	 */	
	@Autowired
	private ILogBiz logBiz;
	
	
	/**
	 * 查询发送日志列表
	 * @param log 发送日志实体
	 * <i>log参数包含字段信息参考：</i><br/>
	 * logId <br/>
	 * appId 应用编号<br/>
	 * logDatetime 时间<br/>
	 * logContent 接收内容<br/>
	 * logReceive 接收人<br/>
	 * logType 日志类型0邮件1短信<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * logId: <br/>
	 * appId: 应用编号<br/>
	 * logDatetime: 时间<br/>
	 * logContent: 接收内容<br/>
	 * logReceive: 接收人<br/>
	 * logType: 日志类型0邮件1短信<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute LogEntity log,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		BasicUtil.startPage();
		List logList = logBiz.query(log);
		BasicUtil.endPage(logList);
		this.outJson(response, JSONArray.toJSONStringWithDateFormat(logList, "yyyy-MM-dd"));
	}
	
	
	/**
	 * 获取发送日志
	 * @param log 发送日志实体
	 * <i>log参数包含字段信息参考：</i><br/>
	 * logId <br/>
	 * appId 应用编号<br/>
	 * logDatetime 时间<br/>
	 * logContent 接收内容<br/>
	 * logReceive 接收人<br/>
	 * logType 日志类型0邮件1短信<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * logId: <br/>
	 * appId: 应用编号<br/>
	 * logDatetime: 时间<br/>
	 * logContent: 接收内容<br/>
	 * logReceive: 接收人<br/>
	 * logType: 日志类型0邮件1短信<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute LogEntity log,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(log.getLogId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("log.id")));
			return;
		}
		LogEntity _log = (LogEntity)logBiz.getEntity(log.getLogId());
		this.outJson(response, _log);
	}
	
	/**
	 * 保存发送日志实体
	 * @param log 发送日志实体
	 * <i>log参数包含字段信息参考：</i><br/>
	 * logId <br/>
	 * appId 应用编号<br/>
	 * logDatetime 时间<br/>
	 * logContent 接收内容<br/>
	 * logReceive 接收人<br/>
	 * logType 日志类型0邮件1短信<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * logId: <br/>
	 * appId: 应用编号<br/>
	 * logDatetime: 时间<br/>
	 * logContent: 接收内容<br/>
	 * logReceive: 接收人<br/>
	 * logType: 日志类型0邮件1短信<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute LogEntity log, HttpServletResponse response, HttpServletRequest request) {
		//验证时间的值是否合法			
		if(StringUtil.isBlank(log.getLogDatetime())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("log.datetime")));
			return;			
		}
		if(!StringUtil.checkLength(log.getLogDatetime()+"", 1, 19)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("log.datetime"), "1", "19"));
			return;			
		}
		//验证接收内容的值是否合法			
		if(StringUtil.isBlank(log.getLogContent())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("log.content")));
			return;			
		}
		if(!StringUtil.checkLength(log.getLogContent()+"", 1, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("log.content"), "1", "255"));
			return;			
		}
		//验证接收人的值是否合法			
		if(StringUtil.isBlank(log.getLogReceive())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("log.receive")));
			return;			
		}
		if(!StringUtil.checkLength(log.getLogReceive()+"", 1, 0)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("log.receive"), "1", "0"));
			return;			
		}
		//验证日志类型0邮件1短信的值是否合法			
		if(StringUtil.isBlank(log.getLogType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("log.type")));
			return;			
		}
		if(!StringUtil.checkLength(log.getLogType()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("log.type"), "1", "10"));
			return;			
		}
		logBiz.saveEntity(log);
		this.outJson(response, log);
	}

	/**
	 * @param log 发送日志实体
	 * <i>log参数包含字段信息参考：</i><br/>
	 * logId:多个logId直接用逗号隔开,例如logId=1,2,3,4
	 * 批量删除发送日志
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@ModelAttribute LogEntity log,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = BasicUtil.getInts("logId");
		if(ids==null){
			this.outJson(response,null, false);
			return;
		}
		logBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新发送日志信息发送日志
	 * @param log 发送日志实体
	 * <i>log参数包含字段信息参考：</i><br/>
	 * logId <br/>
	 * appId 应用编号<br/>
	 * logDatetime 时间<br/>
	 * logContent 接收内容<br/>
	 * logReceive 接收人<br/>
	 * logType 日志类型0邮件1短信<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * logId: <br/>
	 * appId: 应用编号<br/>
	 * logDatetime: 时间<br/>
	 * logContent: 接收内容<br/>
	 * logReceive: 接收人<br/>
	 * logType: 日志类型0邮件1短信<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	public void update(@ModelAttribute LogEntity log, HttpServletResponse response,
			HttpServletRequest request) {
		//验证时间的值是否合法			
		if(StringUtil.isBlank(log.getLogDatetime())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("log.datetime")));
			return;			
		}
		if(!StringUtil.checkLength(log.getLogDatetime()+"", 1, 19)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("log.datetime"), "1", "19"));
			return;			
		}
		//验证接收内容的值是否合法			
		if(StringUtil.isBlank(log.getLogContent())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("log.content")));
			return;			
		}
		if(!StringUtil.checkLength(log.getLogContent()+"", 1, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("log.content"), "1", "255"));
			return;			
		}
		//验证接收人的值是否合法			
		if(StringUtil.isBlank(log.getLogReceive())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("log.receive")));
			return;			
		}
		if(!StringUtil.checkLength(log.getLogReceive()+"", 1, 0)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("log.receive"), "1", "0"));
			return;			
		}
		//验证日志类型0邮件1短信的值是否合法			
		if(StringUtil.isBlank(log.getLogType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("log.type")));
			return;			
		}
		if(!StringUtil.checkLength(log.getLogType()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("log.type"), "1", "10"));
			return;			
		}
		logBiz.updateEntity(log);
		this.outJson(response, log);
	}
	
		
}