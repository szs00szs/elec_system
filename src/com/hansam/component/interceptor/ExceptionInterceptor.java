package com.hansam.component.interceptor;

import com.hansam.util.Config;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年10月13日 下午4:23:09
 */
public class ExceptionInterceptor implements Interceptor {

	private final static Log log = Log.getLog(ExceptionInterceptor.class);

	public void intercept(Invocation ai) {

		try {
			ai.invoke();
		} catch (Exception e) {
			log.error("异常：", e);
			Controller controller = ai.getController();
			controller.setAttr("error", e.toString());
			controller.render(Config.getStr("PAGES.500"));
		}

	}
}
