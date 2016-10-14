package com.hansam.test.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hansam.modules.admin.student.Student;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.c3p0.C3p0Plugin;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年8月17日 下午6:33:52
 */
public class ExcelExport {

	public static void main(String[] args) {
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String title = dateFormat.format(d);
		System.out.println(title);
		File file = new File(title + "_统计表" + ".xls");
		String sql = "select id,name,sex from student";
		// ider:外部传入一个表头、SQL语句、
		String s[] = new String[] { "id", "name", "sex" };

		new ExcelExport().saveFile(s, sql, file);
	}

	// 新增一行就累加
	private int count = 0;

	public void saveFile(String[] s, String sql, File file) {
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
		C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:mysql://127.0.0.1/test", "root", "root", "com.mysql.jdbc.Driver");

		c3p0Plugin.start();
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.addMapping("student", Student.class);
		arp.start();

		// 得到所有记录 行：列
		List<Record> list = Db.find(sql);
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
		try {
			FileOutputStream fileOutputStreane = new FileOutputStream(file);
			hssfWorkbook.write(fileOutputStreane);
			fileOutputStreane.flush();
			fileOutputStreane.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
