package com.hansam.modules.system.dict;

import com.hansam.component.annotation.ModelBind;

@ModelBind(table = "sys_dict", key = "dict_id")
public class SysDict {
	public static final SysDict dao = new SysDict();

}