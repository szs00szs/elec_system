package com.hansam.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年8月27日 下午5:51:19
 */
public class DateUtils {
	public static final int SECOND = 1;

	public static final int MINUTE_SECOND = 60 * SECOND;

	public static final int HOUR_SECOND = 60 * MINUTE_SECOND;

	public static final int DAY_SECOND = 24 * HOUR_SECOND;

	public static final int WEEK_SECOND = 7 * DAY_SECOND;

	/** 日期格式：yyyy-MM-dd HH:mm:ss.SSS */
	public static final String YMD_HMSSS = "yyyy-MM-dd HH:mm:ss.SSS";
	/** 日期格式：yyyyMMddHHmmssSSS */
	public static final String YMDHMSSS = "yyyyMMddHHmmssSSS";
	/** 日期格式：yyyy-MM-dd HH:mm:ss */
	public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
	/** 日期格式：yyyy-MM-dd HH:mm */
	public static final String YMD_HM = "yyyy-MM-dd HH:mm";
	/** 日期格式：yyyyMMddHHmmss */
	public static final String YMDHMS = "yyyyMMddHHmmss";
	/** 日期格式：yyyy-MM-dd */
	public static final String YMD = "yyyy-MM-dd";
	/** 时间格式：HH:mm:ss */
	public static final String HMS = "HH:mm:ss";

	/**
	 * 默认的日期格式 .
	 */
	public static final String DEFAULT_REGEX = "yyyy-MM-dd";
	/**
	 * 默认的日期格式 .
	 */
	public static final String DEFAULT_REGEX_YYYYMMDD = "yyyyMMdd";
	/**
	 * 默认的日期格式 .
	 */
	public static final String DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 默认的DateFormat 实例
	 */
	private static final EPNDateFormat DEFAULT_FORMAT = new EPNDateFormat(DEFAULT_REGEX);
	/**
	 * 默认的DateFormat 实例
	 */
	private static final EPNDateFormat DEFAULT_FORMAT_YYYY_MM_DD_HH_MIN_SS = new EPNDateFormat(
			DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
	/**
	 * 默认的DateFormat 实例
	 */
	private static final EPNDateFormat DEFAULT_FORMAT_YYYYMMDD = new EPNDateFormat(DEFAULT_REGEX_YYYYMMDD);
	private static Map<String, EPNDateFormat> formatMap = new HashMap<String, EPNDateFormat>();
	static {
		formatMap.put(DEFAULT_REGEX, DEFAULT_FORMAT);
		formatMap.put(DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS, DEFAULT_FORMAT_YYYY_MM_DD_HH_MIN_SS);
		formatMap.put(DEFAULT_REGEX_YYYYMMDD, DEFAULT_FORMAT_YYYYMMDD);
	}

	private DateUtils() {

	}

	/**
	 * 时间对象格式化成String ,等同于java.text.DateFormat.format();
	 * 
	 * @param date
	 *            需要格式化的时间对象
	 * 
	 *            2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
	 * @return 转化结果
	 */
	public static String format(java.util.Date date) {
		return DEFAULT_FORMAT.format(date);
	}

	/**
	 * 时间对象格式化成String ,等同于java.text.SimpleDateFormat.format();
	 * 
	 * @param date
	 *            需要格式化的时间对象
	 * @param regex
	 *            定义格式的字符串
	 * 
	 *            2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
	 * @return 转化结果
	 */
	public static String format(java.util.Date date, String regex) {
		return getDateFormat(regex).format(date);
	}

	private static EPNDateFormat getDateFormat(String regex) {
		EPNDateFormat fmt = formatMap.get(regex);
		if (fmt == null) {
			fmt = new EPNDateFormat(regex);
			formatMap.put(regex, fmt);
		}
		return fmt;
	}

	/**
	 * 尝试解析时间字符串 ,if failed return null;
	 * 
	 * @author wangp
	 * @since 2008.12.20
	 * @param time
	 * 
	 *            2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
	 * @return
	 */
	public static Date parseByAll(String time) {
		Date stamp = null;
		if (time == null || "".equals(time))
			return null;
		Pattern p3 = Pattern.compile("\\b\\d{2}[.-]\\d{1,2}([.-]\\d{1,2}){0,1}\\b");
		if (p3.matcher(time).matches()) {
			time = (time.charAt(0) == '1' || time.charAt(0) == '0' ? "20" : "19") + time;
		}

		stamp = DateUtils.parse(time, "yyyy-MM-ddHH:mm:ss");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM.dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yy-MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM.dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy-MM.dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy.MM-dd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyyMMdd");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy年MM月dd日");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyyMM");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy年MM月");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy");
		if (stamp == null)
			stamp = DateUtils.parse(time, "yyyy年");
		return stamp;
	}

	/**
	 * 解析字符串成时间 ,遇到错误返回null不抛异常
	 * 
	 * @param source
	 * 
	 *            2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
	 * @return 解析结果
	 */
	public static java.util.Date parse(String source) {
		try {
			return DEFAULT_FORMAT.parse(source);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 解析字符串成时间 ,遇到错误返回null不抛异常
	 * 
	 * @param source
	 * @param 格式表达式
	 * 
	 *            2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
	 * @return 解析结果
	 */
	public static java.util.Date parse(String source, String regex) {
		try {
			EPNDateFormat fmt = getDateFormat(regex);
			return fmt.parse(source);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 取得当前时间的Date对象 ;
	 * 
	 * 2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
	 * 
	 * @return
	 */
	public static Date getNowDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间字符串
	 * 
	 * 2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
	 * 
	 * @return
	 */
	public static String getNow() {
		return getNow(DEFAULT_REGEX);
	}

	/**
	 * 获取当前时间字符串
	 * 
	 * 2014年7月4日 下午11:47:10 flyfox 330627517@qq.com
	 * 
	 * @param regex
	 *            格式表达式
	 * @return
	 */
	public static String getNow(String regex) {
		return format(getNowDate(), regex);
	}

	/**
	 * 获取当前时间加/减以后的时间
	 * 
	 * @param n天数
	 *            format 时间格式
	 * @return
	 */
	public static String getOldTime(int n, String format) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, n);
		Date date = cal.getTime();
		if (format == "") {
			return DateUtils.format(date, "yyyy-MM-dd HH:mm:ss");
		} else {
			return DateUtils.format(date, format);
		}
	}

	/**
	 * 获取某时间加/减以后的时间
	 * 
	 * @param n天数
	 *            format 时间格式
	 * @return
	 * @throws ParseException
	 */
	public static String getTimeByDate(int n, String format, String date) throws ParseException {
		if (date == null) {
			return DateUtils.format(new Date(), "yyyy-MM-dd 00:00:00");
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date(f.parse(date).getTime() + (n * 24 * 3600 * 1000));
		if (format == "") {
			return DateUtils.format(d, "yyyy-MM-dd HH:mm:ss");
		} else {
			return DateUtils.format(d, format);
		}
	}

	/**
	 * 给某个时间退后或向前移动n个月
	 * 
	 * @param time
	 * @param addMonth
	 */
	public static Date addMonths(Date time, int addMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + addMonth);
		return calendar.getTime();
	}

	/**
	 * 给某个时间退后或向前移动n个小时
	 * 
	 * @param time
	 * @param addMonth
	 */
	public static Date addHours(Date time, int addHours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + addHours);
		return calendar.getTime();
	}

	/**
	 * 给某个时间退后或向前移动n秒
	 * 
	 * @param time
	 * @param addSeconds
	 */
	public static Date addSeconds(Date time, int addSeconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + addSeconds);
		return calendar.getTime();
	}

	/***
	 * 获取指定时间所在天的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getCurrenDayBeginTime(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		return getDateTime(ca.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/***
	 * 获取指定时间所在天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getCurrenDayEndTime(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		return getDateTime(ca.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	public static String getDateTime(Date date, String p) {
		SimpleDateFormat format = new SimpleDateFormat(p);
		return format.format(date);
	}

	/***
	 * 获取两个日期间的所有日期集合
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Date> getBetweenTwoDayDates(Date startDay, Date endDay) {
		List<Date> dates = new ArrayList<>();
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(startDay);
		end.setTime(endDay);

		dates.add(startDay);
		// 给出的日期开始日比终了日大则不执行
		if (startDay.compareTo(endDay) > 0) {
			return dates;
		}
		// 现在打印中的日期
		Calendar currentPrintDay = start;
		while (true) {
			// 日期加一
			currentPrintDay.add(Calendar.DATE, 1);
			// 日期加一后判断是否达到终了日，达到则终止
			if (currentPrintDay.compareTo(end) > 0) {
				break;
			}
			dates.add(currentPrintDay.getTime());
		}

		return dates;
	}
}

class EPNDateFormat {
	private SimpleDateFormat instance;

	EPNDateFormat() {
		instance = new SimpleDateFormat(DateUtils.DEFAULT_REGEX);
		instance.setLenient(false);
	}

	EPNDateFormat(String regex) {
		instance = new SimpleDateFormat(regex);
		instance.setLenient(false);
	}

	synchronized String format(java.util.Date date) {
		if (date == null)
			return "";
		return instance.format(date);
	}

	synchronized java.util.Date parse(String source) throws ParseException {
		return instance.parse(source);
	}

}
