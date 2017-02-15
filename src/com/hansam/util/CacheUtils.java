package com.hansam.util;

import org.apache.log4j.Logger;

import com.hansam.modules.system.dict.DictCache;
import com.hansam.util.cache.Cache;
import com.hansam.util.cache.CacheManager;

/**
 * 缓存工具类
 * 
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2017年2月13日 下午3:30:56
 */
public class CacheUtils {
	private final static Logger LOG = Logger.getLogger(CacheUtils.class);
	private final static String CACHE_NAME = "CacheUtils";
	private static Cache cache = CacheManager.get(CACHE_NAME);
	public static void init() {
		LOG.info("####缓存初始化开始......");
		// 系统常量
		CacheUtils.updateCache();
		// 数据字典
		DictCache.init();
		LOG.info("####缓存初始化结束......");
	}

	/**
	 * 更新缓存
	 * 
	 * 2015年4月24日 下午3:11:40 flyfox 330627517@qq.com
	 */
	public static void updateCache() {
		cache.clear();


	}
	
	public static String getHeadTitle() {
		return cache.get("headTitle");
	}


}
