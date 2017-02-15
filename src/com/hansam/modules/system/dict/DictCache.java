package com.hansam.modules.system.dict;

import org.apache.log4j.Logger;

import com.hansam.util.cache.Cache;
import com.hansam.util.cache.CacheManager;


/**
 * 数据字典缓存
 * 
 * 2014年1月21日 下午11:23:23 flyfox 330627517@qq.com
 */
public class DictCache {

	private final static Logger log = Logger.getLogger(DictCache.class);
	private final static String cacheName = "DictCache";
	private static Cache cache;

	/**
	 * 初始化Map
	 * 
	 * @author flyfox 2013-11-15
	 */
	public static void init() {
		if (cache == null) {
			cache = CacheManager.get(cacheName);
		}
		log.info("####数据字典Cache初始化......");
	}


}
