package com.hansam.component.interceptor;

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

		// 每次访问获取session
		User user = null;
		user = controller.getSessionAttr("sessionUser");
		
		if (null == user) {
			log.info("####非法请求####");
			controller.redirect("/");
		}else {
			ai.invoke();
		}
	}
}
