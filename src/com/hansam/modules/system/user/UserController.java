package com.hansam.modules.system.user;

import com.hansam.component.annotation.ControllerBind;
import com.hansam.component.jfinal.BaseController;
import com.hansam.util.Md5Utils;
import com.hansam.util.StrUtils;
import com.jfinal.aop.Clear;
import com.jfinal.ext.render.CaptchaRender;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年8月19日 下午1:53:25
 */
@SuppressWarnings("deprecation")
@Clear
@ControllerBind(controllerKey = "/")
public class UserController extends BaseController{
	public static final String loginPage = "/pages/system/user/login.html";

	public static final String mainPage = "/admin/device/list";

	public void index() {
		if (getUser() != null) {
			// 如果session存在，不再验证
			// redirect(mainPage);
			renderText("登录成功");
		} else {
			render(loginPage);
		}
		render("/pages/system/user/login.html");
	}

	private static final String RANDOM_CODE_KEY = "1";

	public void img() {
		CaptchaRender img = new CaptchaRender(RANDOM_CODE_KEY);
		render(img);
	}

	/**
	 * 登录
	 */
	public void login() {
		// 获取验证码
		// 接收文本框值，toUpperCase()忽略验证码大小写
		String imageCode = getPara("imageCode");
		boolean loginSuccess = CaptchaRender.validate(this, imageCode.toUpperCase(), RANDOM_CODE_KEY);

		if (StrUtils.isEmpty(imageCode) || !loginSuccess)

		{
			setAttr("msg", "验证码错误！");
			render(loginPage);
			return;
		}

		// 初始化数据字典Map
		String username = getPara("username");
		String password = getPara("password");

		String encryptPassword = new Md5Utils().getMD5(password); // 加密
		User user = User.dao.findFirst(
				"select * from user where username ='" + username + "' and password ='" + encryptPassword + "'");
		if (user == null) {
			setAttr("msg", "认证失败，请您重新输入。");
			render(loginPage);
			return;
		}
		setUser(user);
		redirect(mainPage);

	}

	/**
	 * 登出
	 */
	public void logout() {
		User user = getUser();
		if (user != null) {
			removeSessionAttr("sessionUser");
		}
		setAttr("msg", "您已退出");
		render(loginPage);
	}

	// 获取Session中的user
	public User getUser() {
		User user = getSessionAttr("sessionUser");
		return user;
	}

	public void setUser(User user) {
		setSessionAttr("sessionUser", user);
	}

}
