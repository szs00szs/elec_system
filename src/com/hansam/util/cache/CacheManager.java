package com.hansam.util.cache;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.hansam.util.cache.impl.MemorySerializeCache;
import com.hansam.util.serializable.SerializerManage;

/**
 * 缓存管理
 * 
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2017年2月13日 下午2:44:29
 */
public class CacheManager {
	private static ConcurrentHashMap<String, Cache> map = new ConcurrentHashMap<>();
	static ICacheManager _CreateCache;
	
	protected CacheManager() {
		
	}
	
	static {
		_CreateCache = new ICacheManager() {
			
			@Override
			public Cache getCache() {
				return new MemorySerializeCache(SerializerManage.get("java"));
			}
		};
	}
	
	public static void setCache(ICacheManager thisCache){
		_CreateCache = thisCache;
	}
	
	public static Cache get(String name) {
		Cache cache = map.get(name);
		if (cache == null) {
			synchronized (map) {
				cache = map.get(name);
				if (cache == null) {
					cache = _CreateCache.getCache();
					cache.name(name);
					map.put(name, cache);
				}
			}
		}
		return cache;
	}
	
	public static int size() {
		return map.size();
	}

	public static Collection<Cache> values() {
		return map.values();
	}

	public static Set<String> keys() {
		return map.keySet();
	}


}
