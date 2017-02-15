package com.hansam.modules.system.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hansam.modules.system.menu.TbSysMenu;
import com.hansam.util.Md5Utils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年8月19日 下午2:30:54
 */
public class UserService {

	/**
	 * 登录校验
	 * @param name
	 * @param password
	 * @return
	 */
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
	
	/**
	 * 返回菜单权限
	 */
	public Map<Integer, List<TbSysMenu>> getAuthMap(User user) {
		String menuids = "select menuid from sys_role_menu where roleid in"
				+ " ( select roleid from sys_user_role where userid = ? ) group by menuid";
		// 管理员
		if (user.getInt("user_type") == 1) {
			menuids = " select id from sys_menu where -1 != ? "; // 所有菜单
		}

		Integer userid = user.getUserid();
		Map<Integer, List<TbSysMenu>> map = new HashMap<Integer, List<TbSysMenu>>();

		String sql = "select * from sys_menu where status = 1 and parent_id = ? " //
				+ "and id in (" + menuids + ") order by sort ";
		// 获取根目录
		List<TbSysMenu> rootList = TbSysMenu.dao.find(sql, 0, userid);
		if (rootList == null || rootList.size() == 0) {
			return null;
		}

		map.put(0, rootList);
		// 获取子目录
		for (TbSysMenu TbSysMenu : rootList) {
			List<TbSysMenu> list = TbSysMenu.find(sql, TbSysMenu.getInt("id"), userid);
			map.put(TbSysMenu.getInt("id"), list);
		}

		return map;
	}

}
