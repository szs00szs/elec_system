package com.hansam.component.jfinal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hansam.modules.system.user.User;
import com.hansam.util.Attr;
import com.hansam.util.Config;
import com.hansam.util.DateUtils;
import com.hansam.util.StrUtils;
import com.hansam.util.encrypt.DESUtils;
import com.jfinal.core.Controller;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年9月21日 下午8:56:50
 */
public class BaseController extends Controller {

	protected static final String page_message = Config.getStr("PAGES.MESSAGE");
	private static final Logger log = Logger.getLogger(BaseController.class);

	protected void renderMessage(String message) {
		renderMessage(message, "closeIframe();");
	}

	protected void renderMessageByFailed(String message) {
		renderMessage(message, "history.back();");
	}

	protected void renderMessage(String message, String obj) {
		String script = "";
		if (StrUtils.isEmpty(obj)) { // 关闭页面

			script = "closeIframe();";
		} else if (script.endsWith(".jsp")) { // 页面跳转

			script = "window.location.href = \"" + obj + "\"";
		} else { // 直接执行JS

			script = obj;
		}
		setAttr("msg", message);
		setAttr("script", script);
		render(page_message);
	}

	protected void render500(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/pages/error/500.jsp").forward(request, response);
		} catch (Exception e) {
			log.error("500 page -->", e);
		}
	}

	/**
	 * 获取前一个页面
	 */
	protected String getPrePage() {
		return getRequest().getHeader("Referer");
	}

	/**
	 * 获取当前时间，保存创建时间使用
	 */
	protected String getNow() {
		return DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
	}

	protected String getToday() {
		return DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYYMMDD);
	}


	public User getSessionUser() {
		User user = getSessionAttr("sessionUser");
		return user;
	}
	
	/**
	 * 自定义分页方法
	 * @return
	 */
	public Paginator getPaginator() {
		Paginator paginator = new Paginator();
		Integer pageNo = getParaToInt("pageNo");
		if (pageNo != null && pageNo > 0) {
			paginator.setPageNo(pageNo);
		}
		Integer pageSize = getParaToInt("recordsperpage");
		if (pageSize != null && pageSize > 0) {
			paginator.setPageSize(pageSize);
		}
		return paginator;
	}
	
	/**
	 * 
	 * 覆盖原始方法，采用PAGE_MODEL_NAME做为前缀
	 */
	@Override
	public <T> T getModel(Class<T> modelClass) {
		return super.getModel(modelClass, Attr.PAGE_MODEL_NAME);
	}
	
	/**
	 * 
	 * 新GetModel方法，采用PAGE_ATTR_NAME，作为前缀
	 */
	public <T> T getModelByAttr(Class<T> modelClass) {
		return super.getModel(modelClass, Attr.PAGE_ATTR_NAME);
	}

}
