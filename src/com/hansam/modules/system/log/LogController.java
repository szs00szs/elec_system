package com.hansam.modules.system.log;

import com.hansam.component.annotation.ControllerBind;
import com.hansam.component.jfinal.BaseController;
import com.hansam.util.SQLUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * 操作日志
 * 
 */
@ControllerBind(controllerKey = "/system/log")
public class LogController extends BaseController {

	private static final String path = "/pages/system/log/log_";

	public void list() {
		SysLog model = getModelByAttr(SysLog.class);

		SQLUtils sql = new SQLUtils(" from sys_log t where 1=1 ");
		if (model._getAttrValues().length != 0) {
			sql.setAlias("t");
			// 查询条件
			sql.whereEquals("log_type", model.getLogType());
		}
		sql.append(" order by id desc");

		Page<SysLog> page = SysLog.dao.paginate(getParaToInt(0, 1), 10, "select t.* ", //
				sql.toString().toString());

		// 下拉框
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}

	public void add() {
		render(path + "add.html");
	}

	public void view() {
		SysLog model = SysLog.dao.findById(getParaToInt());
		setAttr("model", model);
		render(path + "view.html");
	}

	public void delete() {
		SysLog.dao.deleteById(getParaToInt());
		list();
	}

	public void edit() {
		SysLog model = SysLog.dao.findById(getParaToInt());
		setAttr("model", model);
		render(path + "edit.html");
	}

	public void save() {
		Integer pid = getParaToInt();
		SysLog model = getModel(SysLog.class);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("id");
			model.put("create_id", getSessionUser().getUserid());
			model.put("create_time", getNow());
			model.save();
		}
		renderMessage("保存成功");
	}
}
