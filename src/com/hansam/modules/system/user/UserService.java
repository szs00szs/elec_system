package com.hansam.modules.system.user;

import com.hansam.util.Md5Utils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年8月19日 下午2:30:54
 */
public class UserService {

	public boolean judge(String name, String password) {

		if (name != null & password != null) {

			String sql = "select password from user where loginname ='" + name + "'";
			Record record = Db.findFirst(sql);
			String db_pass = record.getStr("password");
			String md5 = new Md5Utils().getMD5(password);
			if (md5.equals(db_pass)) {
				return true;
			}
		}
		return false;
	}

}
