
package com.hansam.component.util;

import com.hansam.util.Config;

public class Attr {

	/**
	 * session user
	 */
	public static final String SESSION_NAME = Config.getStr("ATTR.SESSION_NAME");

	/**
	 * 是否是移动设备
	 */
	public static final String SESSION_IS_MOILE = Config.getStr("ATTR.SESSION_IS_MOILE");

	/**
	 * model前缀
	 */
	public static final String PAGE_MODEL_NAME = Config.getStr("ATTR.MODEL_NAME");
	
	/**
	 * attr前缀
	 */
	public static final String PAGE_ATTR_NAME = Config.getStr("ATTR.ATTR_NAME");
	
	/**
	 * form前缀
	 */
	public static final String PAGE_FORM_NAME = Config.getStr("ATTR.FORM_NAME");

	/**
	 * 手机用户路径前缀
	 */
	public static final String PATH_MOBILE = Config.getStr("ATTR.PATH_MOBILE");

	/**
	 * PC用户路径前缀
	 */
	public static final String PATH_PC = Config.getStr("ATTR.PATH_PC");
}
