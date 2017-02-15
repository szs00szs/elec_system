package com.hansam.modules.admin.person;

import com.alibaba.fastjson.JSONObject;
import com.hansam.component.annotation.ControllerBind;
import com.hansam.component.jfinal.BaseController;
import com.hansam.modules.system.user.User;
import com.hansam.util.Md5Utils;
import com.hansam.util.StrUtils;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2017年2月3日 上午10:07:10
 */
@ControllerBind(controllerKey = "/admin/person")
public class PersonController extends BaseController {
	public static final String path = "/pages/admin/person/";

	public void index() {
		User user = getSessionUser();
		if (user == null) {
			return;
		}
		setAttr("model", user);
		render(path + "show_person.html");
	}

	/**
	 * 个人信息保存
	 */
	public void save() {
		JSONObject json = new JSONObject();
		json.put("status", 2);// 失败

		User user = getSessionUser();
		int userid = user.getInt("userid");
		User model = getModel(User.class);
		if (userid != model.getInt("userid")) {
			json.put("msg", "提交数据错误！");
			renderJson(json.toJSONString());
			return;
		}

		String oldPassword = getPara("old_password");
		String newPassword = getPara("new_password");
		String newPassword2 = getPara("new_password2");
		if (!user.getStr("password").equals(new Md5Utils().getMD5(oldPassword))) {
			json.put("msg", "密码错误！");
			renderJson(json.toJSONString());
			return;
		}
		if (StrUtils.isNotEmpty(newPassword) && !newPassword.equals(newPassword2)) {
			json.put("msg", "两次新密码不一致！");
			renderJson(json.toJSONString());
			return;
		} else if (StrUtils.isNotEmpty(newPassword)) { // 输入密码并且一直
			model.set("password", new Md5Utils().getMD5(newPassword));
		}

		// 日志添加
		model.set("update_id", getSessionUser().getUserid());
		model.set("update_time", getNow());
		model.update();

		User newUser = User.dao.findFirst("select * from user where userid="+userid);
		setSessionAttr("sessionUser", newUser); // 设置session
		json.put("status", 1);// 成功

		renderJson(json.toJSONString());
	}
}
