package com.hansam.component.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.hansam.component.jfinal.BaseController;
import com.hansam.component.util.Attr;
import com.hansam.modules.system.user.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;

/**
 * 用户认证拦截器
 * 
 */
public class UserInterceptor implements Interceptor {

	private static final Log log = Log.getLog(UserInterceptor.class);

	public void intercept(Invocation ai) {

		Controller controller = ai.getController();

		HttpServletRequest request = controller.getRequest();
		String referrer = request.getHeader("referer");
		String site = "http://" + request.getServerName();
		log.debug("####IP:" + request.getRemoteAddr() + "\t port:" + request.getRemotePort() + "\t 请求路径:"
				+ request.getRequestURI());
		if (referrer == null || !referrer.startsWith(site)) {
			log.warn("####非法的请求");
		}

		String path_tmp = ai.getActionKey();

		if (path_tmp.startsWith("/")) {
			path_tmp = path_tmp.substring(1, path_tmp.length());
		}
		if (path_tmp.endsWith("/")) {
			path_tmp = path_tmp.substring(0, path_tmp.length() - 1);
		}

		// 每次访问获取session
		User user = null;
		if (controller instanceof BaseController) {
			user = (User) ((BaseController) controller).getSessionUser();
		} else {
			user = controller.getSessionAttr(Attr.SESSION_NAME);
		}
		
		ai.invoke();
	}
}
