package com.hansam.modules.system.menu;

import com.hansam.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Model;

/**
* @author 时帅帅 945210972@qq.com
* @version 创建时间：2017年2月3日 下午3:31:25
*/
@ModelBind(table = "sys_menu")
public class TbSysMenu extends Model<TbSysMenu>{
	private static final long serialVersionUID = 1L;
	public static final TbSysMenu dao = new TbSysMenu();

}
