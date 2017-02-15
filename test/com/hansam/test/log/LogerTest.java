package com.hansam.test.log;

import com.jfinal.log.Log;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年12月27日 下午4:41:52
 */
public class LogerTest {
	private final static Log log = Log.getLog(LogerTest.class);
	
	public static void main(String[] args) {
		log.error("this is error ");
		
		log.info("this is info ");
		
		log.debug("this is debug ");
	}
}
