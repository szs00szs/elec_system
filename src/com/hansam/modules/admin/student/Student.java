package com.hansam.modules.admin.student;

import com.hansam.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年8月15日 下午3:32:15
 */
@ModelBind(table="student")
public class Student extends Model<Student> {
	private static final long serialVersionUID = 1L;
	public static final Student dao = new Student();
	
	
	
	

}
