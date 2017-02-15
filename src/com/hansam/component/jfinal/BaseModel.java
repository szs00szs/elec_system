package com.hansam.component.jfinal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hansam.modules.system.log.SysLog;
import com.hansam.util.DateUtils;
import com.hansam.util.NumberUtils;
import com.hansam.util.cache.Cache;
import com.hansam.util.cache.CacheManager;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

/**
* @author 时帅帅 945210972@qq.com
* @version 创建时间：2017年2月13日 下午5:33:25
*/
public class BaseModel<M extends Model<M>> extends Model<M>{
	private static final Logger log = Logger.getLogger(BaseModel.class);
	private static final long serialVersionUID = 1L;

	@Override
	public Integer getInt(String attr) {
		Object obj = get(attr);
		if (obj == null) {
			return null;
		} else if (obj instanceof Integer) {
			return (Integer) obj;
		} else if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).intValue();
		} else if (obj instanceof String) {
			try {
				return Integer.parseInt((String) obj);
			} catch (Exception e) {
				throw new RuntimeException("String can not cast to Integer : " + attr.toString());
			}
		}
		return null;
	}

	public Table getTable() {
		return TableMapping.me().getTable(getClass());
	}
	


	/**
	 * Find model.
	 * 
	 * @param where
	 *            an SQL statement that may contain one or more '?' IN parameter
	 *            placeholders
	 * @param paras
	 *            the parameters of sql
	 * @return the list of Model
	 */
	public List<M> findByWhere(String where, Object... paras) {
		return findByWhereAndColumns(where, "*", paras);
	}

	/**
	 * Find model.
	 * 
	 * @param where
	 *            an SQL statement that may contain one or more '?' IN parameter
	 *            placeholders
	 * @param columns
	 * @param paras
	 *            the parameters of sql
	 * @return the list of Model
	 */
	public List<M> findByWhereAndColumns(String where, String columns, Object... paras) {
		String sql = " select " + columns + " from " + getTable().getName() + " " + where;
		return find(sql, paras);
	}

	/**
	 * Find first model. I recommend add "limit 1" in your sql.
	 * 
	 * @param where
	 *            an SQL statement that may contain one or more '?' IN parameter
	 *            placeholders
	 * @param paras
	 *            the parameters of sql
	 * @return Model
	 */
	public M findFirstByWhere(String where, Object... paras) {
		return findFirstByWhereAndColumns(where, "*", paras);
	}

	/**
	 * Find first model. I recommend add "limit 1" in your sql.
	 * 
	 * @param where
	 *            an SQL statement that may contain one or more '?' IN parameter
	 *            placeholders
	 * @param columns
	 * @param paras
	 *            the parameters of sql
	 * @return Model
	 */
	public M findFirstByWhereAndColumns(String where, String columns, Object... paras) {
		String sql = " select " + columns + " from " + getTable().getName() + " " + where;
		return findFirst(sql, paras);
	}

	// ///////////////////////////////////////缓存部分/////////////////////////////////////////////////


	/**
	 * findCache是重构版findByCache，使用自己的Cache
	 * 
	 * 2015年5月7日 下午12:12:08 flyfox 330627517@qq.com
	 * 
	 * @param cacheName
	 * @param key
	 * @param sql
	 * @param paras
	 * @return
	 */
	public List<M> findCache(String cacheName, String key, String sql, Object... paras) {
		Cache cache = CacheManager.get(cacheName);
		List<M> result = cache.get(key);
		if (result == null) {
			result = find(sql, paras);
			cache.add(key, result);
		}
		return result;
	}

	/**
	 * findFirstCache是重构版findByCache，使用自己的Cache
	 * 
	 * 2015年5月7日 下午12:12:08 flyfox 330627517@qq.com
	 * 
	 * @param cacheName
	 * @param key
	 * @param sql
	 * @param paras
	 * @return
	 */
	public M findFirstCache(String cacheName, String key, String sql, Object... paras) {
		Cache cache = CacheManager.get(cacheName);
		M result = cache.get(key);
		if (result == null) {
			result = findFirst(sql, paras);
			cache.add(key, result);
		}
		return result;
	}

	/**
	 * 根据Key删除Cache
	 * 
	 * 2015年5月7日 下午12:16:43 flyfox 330627517@qq.com
	 * 
	 * @param cacheName
	 * @param key
	 */
	public void removeCache(String cacheName, String key) {
		Cache cache = CacheManager.get(cacheName);
		cache.remove(key);
	}

	/**
	 * 清理Cache所有数据
	 * 
	 * 2015年5月7日 下午12:16:52 flyfox 330627517@qq.com
	 * 
	 * @param cacheName
	 */
	public void clearCache(String cacheName) {
		Cache cache = CacheManager.get(cacheName);
		cache.clear();
	}

	/****************************************** 加入公共日志 ******************************************/
	@Override
	public boolean save() {
		boolean flag = super.save();
		String tableName = getTable().getName();
		String[] keys = getTable().getPrimaryKey();
		Object id = null;
		if (keys != null && keys.length >= 1) {
			id = get(keys[0]);
		}
		Integer primaryId = (id != null) ? NumberUtils.parseInt(id) : null;
		saveLog(tableName, primaryId, SysLog.MODEL_SAVE);
		return flag;
	}
	
	public boolean saveNoLog() {
		return super.save();
	}
	
	public boolean updateNoLog() {		
		return super.update();
	}

	@Override
	public boolean delete() {
		boolean flag = super.delete();
		String tableName = getTable().getName();
		String[] keys = getTable().getPrimaryKey();
		Object id = null;
		if (keys != null && keys.length >= 1) {
			id = get(keys[0]);
		}
		Integer primaryId = (id != null) ? NumberUtils.parseInt(id) : null;
		saveLog(tableName, primaryId, SysLog.MODEL_DELETE);
		return flag;
	}

	@Override
	public boolean deleteById(Object id) {
		boolean flag = super.deleteById(id);
		String tableName = getTable().getName();
		Integer primaryId = (id != null) ? NumberUtils.parseInt(id) : null;
		saveLog(tableName, primaryId, SysLog.MODEL_DELETE);
		return flag;
	}

	@Override
	public boolean update() {
		boolean flag = super.update();
		String tableName = getTable().getName();
		String[] keys = getTable().getPrimaryKey();
		Object id = null;
		if (keys != null && keys.length >= 1) {
			id = get(keys[0]);
		}
		Integer primaryId = (id != null) ? NumberUtils.parseInt(id) : null;
		saveLog(tableName, primaryId, SysLog.MODEL_UPDATE);
		return flag;
	}

	protected void saveLog(String tableName, Integer primaryId, String operType) {
		try {
			Integer updateId = getAttrs().containsKey("update_id") ? getInt("update_id") : 0;
			String updateTime = getAttrs().containsKey("update_time") ? getStr("update_time") : DateUtils
					.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
			String sql = "INSERT INTO `sys_log` ( `log_type`, `oper_object`, `oper_table`," //
					+ " `oper_id`, `oper_type`, `oper_remark`, `create_time`, `create_id`) " //
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			Db.update(sql, SysLog.TYPE_MODEL, SysLog.getTableRemark(tableName), tableName, //
					primaryId, operType, "", updateTime, updateId);
		} catch (Exception e) {
			log.error("添加日志失败", e);
		}
	}
	
	public Map<String, Object> getAttrs() {
		return super.getAttrs();
	}
}
