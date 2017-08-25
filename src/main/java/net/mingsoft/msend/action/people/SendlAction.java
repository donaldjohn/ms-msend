package net.mingsoft.msend.action.people;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mingsoft.util.JsonUtil;
import com.mingsoft.util.StringUtil;

import net.mingsoft.msend.biz.IMailBiz;
import net.mingsoft.msend.constant.ModelCode;
import net.mingsoft.msend.util.SendUtil;

/**
 * 邮件管理控制层
 * 
 * @author 伍晶晶
 * @version 版本号：0.0<br/>
 *          创建日期：2017-8-24 14:41:18<br/>
 *          历史修订：<br/>
 */
@Controller("peopleMailAction")
@RequestMapping("/msend/people/mail")
public class SendlAction extends net.mingsoft.msend.action.BaseAction {

	/**
	 * 注入邮件业务层
	 */
	@Autowired
	private IMailBiz mailBiz;

	/**
	 * 自由调用邮箱
	 * 
	 * @param peopleMail
	 *            邮件地址
	 * @param modelCode
	 *            模块编码（AES加密过的）
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "send", method = RequestMethod.POST)
	public void send(HttpServletRequest request, HttpServletResponse response) {
		String receive = request.getParameter("receive");
		String modelCode = request.getParameter("modelCode");
		String content = request.getParameter("content");
		String type = request.getParameter("type");
		
		// 验证模块编码是否为空
		if (StringUtil.isBlank(modelCode)) {
			this.outJson(response, ModelCode.SEND, false,
					this.getResString("err.error", this.getResString("model.code")));
			return;
		}

		String _modelCode = this.decryptByAES(request, modelCode);
		// 将邮箱地址压如String数组
		if (_modelCode == null) {
			this.outJson(response, ModelCode.SEND, false,
					this.getResString("err.error", this.getResString("model.code")));
			return;
		}
		Map params = JsonUtil.getJsonToObject(content, Map.class);
		// 发送邮箱
		boolean status = SendUtil.send(_modelCode, receive, params, type);
		this.outJson(response, null, status);
	}
}