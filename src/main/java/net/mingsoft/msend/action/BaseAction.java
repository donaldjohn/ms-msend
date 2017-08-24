package net.mingsoft.msend.action;

import java.util.MissingResourceException;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.mingsoft.base.constant.Const;
import com.mingsoft.base.constant.e.BaseEnum;
import com.mingsoft.base.entity.SessionEntity;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.util.StringUtil;

import cc.pm.bim.system.constant.e.SessionConstEnum;
import net.mingsoft.basic.util.BasicUtil;

/**
 * msend基础控制层
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-24 14:41:18<br/>
 * 历史修订：<br/>
 */
public class BaseAction extends com.mingsoft.basic.action.BaseAction{

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

	/**
	 * 读取员工session
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 返回管理员session，获取不到就返回null
	 */
	protected SessionEntity getEmployeeBySession(HttpServletRequest request) {
		// 传入用管理员请求，读取管理员的session || super,调用父类的protected属性的getSession方法
		Object obj = this.getSession(request, SessionConstEnum.EMPLOYEE_SESSION);
		if (obj != null) {
			// 返回管理员的所有信息
			return (SessionEntity) obj;
		}
		return null;
	}

	/**
	 * 将资源文件转换成list输出
	 * 
	 * @param path
	 *            资源文件路径 com/mingsoft/result/file.properties
	 * @return 转换失败返回null
	 */
	protected List<Map<String, Object>> resourcesToList(String path) {
		List list = null;
		FileInputStream fis = null;
		try {
			list = new ArrayList();
			Properties prop = new Properties();
			fis = new FileInputStream(this.getClass().getResource(Const.SEPARATOR).getPath() + Const.SEPARATOR + path);
			prop.load(fis);
			Enumeration keys = prop.keys();
			while (keys.hasMoreElements())

			{
				Map map = new HashMap();
				String id = keys.nextElement() + "";
				map.put("id", id);
				map.put("value", prop.getProperty(id));
				list.add(map);
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 枚举转list
	 * 
	 * @param cls
	 *            实现了BaseEnum的子类
	 * @return 转换失败返回null
	 */
	protected static List<Map<String, Object>> enumToList(Class cls) {
		List<Map<String, Object>> list = null;
		if (cls != null) {
			list = new ArrayList<Map<String, Object>>();
			try {
				Method method = cls.getDeclaredMethod("values");
				BaseEnum[] be = (BaseEnum[]) method.invoke(cls);

				for (BaseEnum e : be) {
					Map map = new HashMap();
					map.put("id", e.toInt());
					map.put("value", e.toString());
					list.add(map);
				}
			} catch (Exception e) {
				return null;
			}

		}
		return list;

	}

	/**
	 * 根据模块编码获取模块编号
	 * 
	 * @param modelCode
	 *            对应模块编码
	 * @param request
	 *            HttpServletRequest对象
	 * @return 返回当前模块编号，没找到返回0
	 */
	protected int getModelCodeId(HttpServletRequest request, String modelCode) {
		IModelBiz modelBiz = (IModelBiz) this.getBean("modelBiz");
		ModelEntity modelObject = modelBiz.getEntityByModelCode(modelCode);
		if (modelObject != null) {
			return modelObject.getModelId();
		}
		return 0;
	}

	private static String SORT = "sort";
	private static String ORDER = "order";
	private static String DESC = "DESC";

	protected String getSort() {
		String sort = BasicUtil.getString(SORT);
		if (StringUtil.isBlank(sort)) {
			return null;
		}
		return StringUtil.javaProperty2DatabaseCloumn(sort);
	}

	protected String getOrder() {
		return BasicUtil.getString(ORDER, DESC);
	}	

}
