package com.hansam.component.excel;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jfinal.plugin.activerecord.Record;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年8月18日 上午10:00:31
 */
public class PoiKit {
	public static HSSFWorkbook saveFile(String[] s, List<Record> list) {

		// 创建工作薄
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		// sheet:一张表的简称
		// row:表里的行
		// 创建工作薄中的工作表
		HSSFSheet hssfSheet = hssfWorkbook.createSheet("test");
		// 创建行
		HSSFRow row = hssfSheet.createRow(0);
		// 创建单元格，设置表头 创建列
		HSSFCell cell = null;
		// 便利表头
		for (int i = 0; i < s.length; i++) {
			// 创建传入进来的表头的个数
			cell = row.createCell(i);
			// 表头的值就是传入进来的值
			cell.setCellValue(s[i]);

		}

		int count = 0;

		// 得到所有记录 行：列

		Record record = null;

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				// 获取所有的记录 有多少条记录就创建多少行
				row = hssfSheet.createRow(++count);
				// 得到所有的行 一个record就代表 一行
				record = list.get(i);
				// 在有所有的记录基础之上，便利传入进来的表头,再创建N行
				for (int j = 0; j < s.length; j++) {

					cell = row.createCell(j);
					// 把每一行的记录再次添加到表头下面 如果为空就为 "" 否则就为值
					cell.setCellValue(record.get(s[j]) == null ? "" : record.get(s[j]).toString());
				}
			}
		}

		return hssfWorkbook;

	}

}
