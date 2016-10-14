package com.hansam.component.config;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.hansam.component.annotation.AutoBindRoutes;
import com.hansam.component.interceptor.ExceptionInterceptor;
import com.hansam.component.interceptor.UserInterceptor;
import com.hansam.modules.admin.student.Student;
import com.hansam.modules.system.user.User;
import com.hansam.util.StrUtils;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.log.Log4jLogFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.cache.EhCache;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年8月12日 下午4:30:33
 */
public class BaseConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setViewType(ViewType.JSP);

		me.setError401View("/pages/error/401.html");
		me.setError403View("/pages/error/403.html");
		me.setLogFactory(new Log4jLogFactory());
		me.setError404View("/pages/error/404.html");
		me.setError500View("/pages/error/500.html");

		// 使用beetle模板
		me.setMainRenderFactory(new BeetlRenderFactory());
		@SuppressWarnings("unused")
		GroupTemplate groupTemplate = BeetlRenderFactory.groupTemplate;
		groupTemplate.registerFunctionPackage("strutil", StrUtils.class);
	}

	@Override
	public void configRoute(Routes me) {

		// 自动绑定
		// 1.如果没用加入注解，必须以Controller结尾,自动截取前半部分为key
		// 2.加入ControllerBind的 获取 key
		me.add(new AutoBindRoutes());

	}

	@Override
	public void configPlugin(Plugins me) {
		// mysql 数据源
		C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://localhost:3306/elec_system", "root", "root");
		me.add(cp);

		// mysql ActiveRecrodPlugin 实例，并指定configName为 mysql
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp);
		arp.setCache(new EhCache());
		arp.addMapping("student", Student.class);
		arp.addMapping("user", User.class);
	}

	@Override
	public void configInterceptor(Interceptors me) {

		// 异常拦截器，跳转500
		me.add(new ExceptionInterceptor());

		// 用户认证
		me.add(new UserInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {

	}

	public static void main(String[] args) throws Exception {

		JFinal.start("WebRoot", 8080, "/", 5);
	}

	/**
	 * 初始化
	 */
	@Override
	public void afterJFinalStart() {
		System.out.println("##################################");
		System.out.println("############系统启动完成##########");
		System.out.println("##################################");
		super.afterJFinalStart();
	}

	@Override
	public void beforeJFinalStop() {
		// TODO Auto-generated method stub
		super.beforeJFinalStop();
	}

}
