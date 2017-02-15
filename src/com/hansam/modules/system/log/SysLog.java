package com.hansam.modules.system.log;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hansam.component.annotation.ModelBind;
import com.hansam.component.jfinal.BaseModel;
import com.hansam.util.DateUtils;
import com.jfinal.plugin.activerecord.Db;

@ModelBind(table = "sys_log")
public class SysLog extends BaseModel<SysLog> {

	private static final Logger log = Logger.getLogger(BaseModel.class);
	
	private static final long serialVersionUID = 1L;
	public static final SysLog dao = new SysLog();
	/**
	 * 表中文转换
	 */
	private static final Map<String, String> tableMap = new HashMap<String, String>();
	static {
		tableMap.put("sys_dict", "数据字典主表");
		tableMap.put("sys_dict_detail", "数据字典");
		tableMap.put("sys_menu", "菜单管理");
		tableMap.put("sys_department", "组织机构");
		tableMap.put("sys_user", "用户管理");
		tableMap.put("sys_user_role", "用户角色授权");
		tableMap.put("sys_role", "角色管理");
		tableMap.put("sys_role_folder", "角色目录授权");
		tableMap.put("sys_role_menu", "角色菜单授权");
		
		tableMap.put("levelhandsetuser", "用户等级");
		tableMap.put("sysuserhabit", "兴趣");
		tableMap.put("advert", "广告");
		tableMap.put("question", "广告问题");
		tableMap.put("questions", "广告问卷");
		tableMap.put("infocenter", "广告消息");
		tableMap.put("pictureurl", "买就送");
		tableMap.put("redbag", "红包");
		tableMap.put("message_records_log", "消息日志");
		
		tableMap.put("tb_advert", "广告");
		tableMap.put("tb_article", "文章");
		tableMap.put("tb_code_model", "产品二维码");
		tableMap.put("tb_company", "客户管理");
		tableMap.put("tb_company_contract", "客户合同管理");
		tableMap.put("tb_contract_money", "合同款项管理");	
		tableMap.put("tb_driver_auth", "车主认证");	
		tableMap.put("tb_folder", "目录");				
		tableMap.put("tb_friendlylink", "友情链接");	
		tableMap.put("tb_invitecode", "安装邀请码");	
		tableMap.put("tb_oilcard", "加油卡");	
		tableMap.put("tb_oilcard_allot", "提油划拨");	
		tableMap.put("tb_package", "软件版本");	
		tableMap.put("tb_padinfo", "PAD绑定信息");
		tableMap.put("tb_weixinpay", "微信支付");
		tableMap.put("tb_white_list", "白名单");
		tableMap.put("tb_reward", "推广新人奖励");
		
		tableMap.put("stats_advert_daily", "广告日统计");
		
		// business
		tableMap.put("logic_driver_auth", "车主认证");
		tableMap.put("logic_installment", "车猫分期");
		tableMap.put("user_keke_user", "APP用户");
		tableMap.put("logic_route_user", "用户行程信息");
		tableMap.put("logic_user_notin_rule", "规则用户");
		
		
	}

	/**
	 * 获取表中文备注
	 * 
	 * 2015年10月16日 上午11:59:45
	 * flyfox 330627517@qq.com
	 * @param tableName
	 * @return
	 */
	public static String getTableRemark(String tableName) {
		return tableMap.get(tableName);
	}

	public static final Integer TYPE_MODEL = 1;
	public static final Integer TYPE_SYSTEM = 2;

	public static final String MODEL_SAVE = "添加";
	public static final String MODEL_UPDATE = "更新";
	public static final String MODEL_DELETE = "删除";
	public static final String SYSTEM_LOGIN = "登入";
	public static final String SYSTEM_LOGOUT = "登出";

	/**
	 * 操作model日志
	 * 
	 * 2016年12月16日 下午2:21:49
	 * flyfox 330627517@qq.com
	 * @param tableName
	 * @param primaryId
	 * @param operType
	 */
	public void saveModelLog(String tableName, Integer primaryId, String operType) {
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

	/**
	 * 操作系统日志
	 * 
	 * 2016年12月16日 下午2:21:58
	 * flyfox 330627517@qq.com
	 * @param tableName
	 * @param primaryId
	 * @param operType
	 */
	public void saveSystemLog(String tableName, Integer primaryId, String operType) {
		try {
			Integer updateId = getAttrs().containsKey("update_id") ? getInt("update_id") : 0;
			String updateTime = getAttrs().containsKey("update_time") ? getStr("update_time") : DateUtils
					.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
			String sql = "INSERT INTO `sys_log` ( `log_type`, `oper_object`, `oper_table`," //
					+ " `oper_id`, `oper_type`, `oper_remark`, `create_time`, `create_id`) " //
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			Db.update(sql, SysLog.TYPE_SYSTEM, SysLog.getTableRemark(tableName), tableName, //
					primaryId, operType, "", updateTime, updateId);
		} catch (Exception e) {
			log.error("添加日志失败", e);
		}
	}
	
	// columns START
	private String ID = "id"; //
	private String LOG_TYPE = "log_type"; // 类型
	private String OPER_OBJECT = "oper_object"; // 操作对象
	private String OPER_TABLE = "oper_table"; // 操作表
	private String OPER_ID = "oper_id"; // 操作主键
	private String OPER_TYPE = "oper_type"; // 操作类型
	private String OPER_REMARK = "oper_remark"; // 操作备注
	private String CREATE_TIME = "create_time"; // 创建时间
	private String CREATE_ID = "create_id"; // 创建者

	public SysLog setId(java.lang.Integer value) {
		set(ID, value);
		return this;
	}

	public java.lang.Integer getId() {
		return get(ID);
	}

	public SysLog setLogType(java.lang.Integer value) {
		set(LOG_TYPE, value);
		return this;
	}

	public java.lang.Integer getLogType() {
		return get(LOG_TYPE);
	}

	public SysLog setOperObject(java.lang.String value) {
		set(OPER_OBJECT, value);
		return this;
	}

	public java.lang.String getOperObject() {
		return get(OPER_OBJECT);
	}

	public SysLog setOperTable(java.lang.String value) {
		set(OPER_TABLE, value);
		return this;
	}

	public java.lang.String getOperTable() {
		return get(OPER_TABLE);
	}

	public SysLog setOperId(java.lang.Integer value) {
		set(OPER_ID, value);
		return this;
	}

	public java.lang.Integer getOperId() {
		return get(OPER_ID);
	}

	public SysLog setOperType(java.lang.String value) {
		set(OPER_TYPE, value);
		return this;
	}

	public java.lang.String getOperType() {
		return get(OPER_TYPE);
	}

	public SysLog setOperRemark(java.lang.String value) {
		set(OPER_REMARK, value);
		return this;
	}

	public java.lang.String getOperRemark() {
		return get(OPER_REMARK);
	}

	public SysLog setCreateTime(java.lang.String value) {
		set(CREATE_TIME, value);
		return this;
	}

	public java.lang.String getCreateTime() {
		return get(CREATE_TIME);
	}

	public SysLog setCreateId(java.lang.Integer value) {
		set(CREATE_ID, value);
		return this;
	}

	public java.lang.Integer getCreateId() {
		return get(CREATE_ID);
	}
}