package com.hansam.modules.admin.device;

import com.hansam.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Model;

/**
* @author 时帅帅 945210972@qq.com
* @version 创建时间：2016年9月24日 上午11:58:55
*/
@ModelBind(table="")
public class TbDevice extends Model<TbDevice> {
	private static final long serialVersionUID = 1L;
	
	public static final TbDevice dao = new TbDevice();

}
