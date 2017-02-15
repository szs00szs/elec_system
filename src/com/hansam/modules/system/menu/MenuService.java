package com.hansam.modules.system.menu;

import java.util.List;


/**
 * 数据字典service
* @author 时帅帅 945210972@qq.com
* @version 创建时间：2017年2月3日 下午3:51:44
*/
public class MenuService {
	/**
	 * 获取根目录下拉框
	 * 
	 * @param selected
	 * @return
	 */
	public String selectMenu(Integer selected) {
		List<TbSysMenu> list = TbSysMenu.dao.find("select * from sys_menu where status = 1 and parent_id = 0 order by sort ");
		StringBuffer sb = new StringBuffer();
		for (TbSysMenu menu : list) {
			sb.append("<option value=\"");
			sb.append(menu.getInt("id"));
			sb.append("\" ");
			if (selected != null) {
				sb.append(menu.getInt("id") == selected ? "selected" : "");
			}
			sb.append(">");
			sb.append(menu.getStr("name"));
			sb.append("</option>");
		}
		return sb.toString();
	}

	/**
	 * 获取父节点名称
	 * 
	 * @param model
	 * @return
	 */
	public String getParentName(TbSysMenu model) {
		Integer parentid = model.getInt("parent_id");
		if (parentid == null || parentid == 0) {
			return "根目录";
		}
		String parentName = TbSysMenu.dao.findById(model.getInt("parent_id")).getStr("name");
		return parentName;
	}

	/**
	 * 根据父节点获取List
	 * 
	 * @param parentid
	 * @return
	 */
	public List<TbSysMenu> getListByParentid(int parentid) {
		List<TbSysMenu> list = TbSysMenu.dao.find("select * from sys_menu where  status = 1 and parent_id = ? order by sort ", parentid);
		return list;
	}
}
