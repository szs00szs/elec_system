package com.hansam.modules.system.user;

import com.hansam.component.annotation.ModelBind;
import com.hansam.util.StrUtils;
import com.jfinal.plugin.activerecord.Model;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年8月19日 下午1:52:38
 */
@ModelBind(table = "user")
public class User extends Model<User> {
	private static final long serialVersionUID = 1L;
	public static final User dao = new User();

	public Integer getUserid() {
		return getInt("userid") == null ? -1 : getInt("userid");
	}

	public String getUserName() {
		if (StrUtils.isNotEmpty(getStr("real_name"))) {
			return getStr("realname");
		}
		return getStr("username");
	}

}
