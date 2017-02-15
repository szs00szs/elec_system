package com.hansam.modules.system.menu;

import com.hansam.component.annotation.ControllerBind;
import com.hansam.component.jfinal.BaseController;
import com.hansam.util.SQLUtils;
import com.jfinal.plugin.activerecord.Page;

/**
* @author 时帅帅 945210972@qq.com
* @version 创建时间：2017年2月3日 下午3:41:17
*/
@ControllerBind(controllerKey = "/system/menu")
public class MenuController extends BaseController {
	
	private static final String path = "/pages/system/menu/menu_";
	MenuService service = new MenuService();
	public void list() {
		TbSysMenu model = getModelByAttr(TbSysMenu.class);

		SQLUtils sql = new SQLUtils(" from sys_menu t left join sys_menu d on d.id = t.parent_id where 1=1 ");
		if (model._getAttrValues().length != 0) {
			sql.setAlias("t");
			// 查询条件
			sql.whereLike("name", model.getStr("name"));
		}
		sql.append(" order by sort,id desc");

		Page<TbSysMenu> page = TbSysMenu.dao.paginate(getParaToInt(0, 1), 5, "select t.*,ifnull(d.name,'根目录') as parentname ", //
				sql.toString().toString());

		// 下拉框
		setAttr("parentSelect", service.selectMenu(model.getInt("parentid")));
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}

	public void add() {
		setAttr("parentSelect", service.selectMenu(0));
		render(path + "add.html");
	}

	public void view() {
		TbSysMenu model = TbSysMenu.dao.findById(getParaToInt());
		String parent = new MenuService().getParentName(model);
		model.put("parentname", parent);
		setAttr("model", model);
		render(path + "view.html");
	}

	public void delete() {
		// 日志添加
		TbSysMenu model = new TbSysMenu();
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);

		model.deleteById(getParaToInt());
		list();
	}

	public void edit() {
		TbSysMenu model = TbSysMenu.dao.findById(getParaToInt());
		setAttr("parentSelect", service.selectMenu(model.getInt("parentid")));
		setAttr("model", model);
		render(path + "edit.html");
	}

	public void save() {
		Integer pid = getParaToInt();
		TbSysMenu model = getModel(TbSysMenu.class);

		// 根目录级别为1
		Integer parentid = model.getInt("parentid");
		if (parentid != null) {
			model.set("level", parentid == 0 ? 1 : 2);
		}

		// 日志添加
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("id");
			model.put("create_id", userid);
			model.put("create_time", now);
			model.save();
		}
		renderMessage("保存成功");
	}

}
