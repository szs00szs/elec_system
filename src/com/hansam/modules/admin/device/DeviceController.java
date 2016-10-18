package com.hansam.modules.admin.device;

import java.util.List;

import com.hansam.component.annotation.ControllerBind;
import com.hansam.component.excel.PoiKit;
import com.hansam.component.excel.PoiRender;
import com.hansam.component.jfinal.BaseController;
import com.hansam.util.SQLUtils;
import com.hansam.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年8月22日 下午4:56:47
 */
@ControllerBind(controllerKey = "/admin/device")
public class DeviceController extends BaseController {
	public static final String path = "/pages/admin/device/device_";

	public void list() {
	
		System.err.println(getSessionUser());

		String starttime = getPara("starttime");
		String endtime = getPara("endtime");

		SQLUtils sql = new SQLUtils("FROM device o1, area o2 WHERE o1.area_id = o2.id ");

		if (StrUtils.isNotEmpty(starttime)) {
			sql.append(" and o1.create_time >= '").append(starttime).append("' ");
		}
		if (StrUtils.isNotEmpty(endtime)) {
			sql.append(" and o1.create_time <= '").append(endtime).append("' ");
		}

		Page<Record> page = Db.paginate(getParaToInt(0, 1), 5,
				"SELECT " + "o1.id AS device_id,o1.`name` AS device_name, o1.run_status AS run_status, "
						+ "o1.health_status AS health_status, o1.create_time create_time, "
						+ "o1.last_time last_time, o2.area_name AS area_name",
				sql.toString());

		setAttr("page", page);
		setAttr("starttime", starttime);
		setAttr("endtime", endtime);

		render(path + "list.html");

	}
	
	
	public void view() {
		TbDevice model = TbDevice.dao.findById(getParaToInt());
		
		String sql = "SELECT area_name FROM area WHERE id ='" +model.getInt("area_id")+"'";
		Record record = Db.findFirst(sql);
		
		model.put("area_name",record.getStr("area_name"));
		setAttr("model", model);
		render(path+"view.html");
		
		
	}
	
	public void add(){
		
	}
	
	public void delete(){
		System.err.println("jjjj");		
	}
	
	public void export(){
		
		String starttime = getPara("starttime");
		String endtime = getPara("endtime");

		SQLUtils sql = new SQLUtils("SELECT " 
						+ "o1.id AS device_id, "
						+ "o1.`name` AS device_name, "
						+ "o1.run_status AS run_status, "
						+ "o1.health_status AS health_status, "
						+ "o1.create_time create_time, "
						+ "o1.last_time last_time, "
						+ "o2.area_name AS area_name "
						+ "FROM device o1, area o2 "
						+ "WHERE o1.area_id = o2.id ");

		if (StrUtils.isNotEmpty(starttime)) {
			sql.append(" and o1.create_time >= '").append(starttime).append("' ");
		}
		if (StrUtils.isNotEmpty(endtime)) {
			sql.append(" and o1.create_time <= '").append(endtime).append("' ");
		}
		
		List<Record> list = Db.find(sql.toString());
		
		String filename = "设备导出表";
		String columns[] = { "device_id", "device_name", "run_status","health_status","create_time" ,"last_time" ,"area_name" };
		String headers[] = { "设备ID", "设备名", "运行状态","健康状态","安装时间" ,"更新时间" ,"所属部门" };
		
		render(new PoiRender(PoiKit.saveFile(columns,headers, list), filename + ".xls"));
		

		
	}

}
