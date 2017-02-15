package com.hansam.util.cache;

import java.util.List;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2017年2月13日 下午2:41:35
 */
public interface Cache {
	/**
	 * 设置缓存名称
	 */
	public Cache name(String name);

	/**
	 * 根据key获取缓存数据
	 */
	public <T> T get(String key);

	/**
	 * 添加缓存获取
	 */
	public Cache add(String key, Object value);

	/**
	 * 移除缓存数据
	 * 
	 */
	public Object remove(String key);

	/**
	 * 清楚所有数据
	 */
	public void clear();

	/**
	 * 获取缓存数量
	 * 
	 */
	public int size();

	/**
	 * 返回缓存列表
	 */
	public <T> List<T> list();
}
