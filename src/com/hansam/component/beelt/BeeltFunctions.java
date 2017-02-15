package com.hansam.component.beelt;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hansam.util.DateUtils;
import com.hansam.util.StrUtils;

public class BeeltFunctions {

	
	// //////////////////////自定义方法///////////////////////////
	/**
	 * 字符串截取
	 * 
	 * 2015年5月25日 下午3:58:45 flyfox 330627517@qq.com
	 * 
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String substr(String str, int start, int end) {
		return str == null ? null : str.substring(start, end);
	}

	public static String getNow() {
		return DateUtils.getNow();
	}

	public static String getNow(String regex) {
		return DateUtils.getNow(regex);
	}

	public static String suojin(String str, int length) {
		return StrUtils.suojin(str, length);
	}
	
	
	
	/**
	 * 计算据当前日期相差的天数
	 */
	public static int getRemainDays(Date date)  {
		String now = DateUtils.getNow("yyyy-MM-dd");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		try {
			d1 = format.parse(now);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d2 = date;
		long diff = d2.getTime() - d1.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		return Integer.parseInt(String.valueOf(days));
	}



	/**
	 * 判断date距当前时间是否相差before天
	 * 
	 * 2015年5月11日 下午2:07:40 flyfox 330627517@qq.com
	 * 
	 * @param date
	 * @param before
	 * @return
	 */
	public static boolean isNew(String date, int before) {
		DateUtils.parseByAll(date).getTime();
		Date d1 = new Date();
		Date d2 = DateUtils.parse(date, DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
		long diff = d1.getTime() - d2.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		return days - 7 <= 0;
	}

	}
	
