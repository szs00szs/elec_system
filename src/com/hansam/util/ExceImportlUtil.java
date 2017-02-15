package com.hansam.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
* @author 时帅帅 945210972@qq.com
* @version 创建时间：2016年11月16日 下午5:17:35
*/
public class ExceImportlUtil {
	/**
	 * 分析excel的内容
	 * @param path
	 * @return
	 */
	public static List<Map<Integer, Object>> dealDataByPath(String path) {
		List<Map<Integer,Object>> list = new ArrayList<Map<Integer,Object>>();
		// 工作簿
		HSSFWorkbook hwb = null;
		try {
			hwb = new HSSFWorkbook(new FileInputStream(new File(path)));			
			HSSFSheet sheet = hwb.getSheetAt(0);	// 获取到第一个sheet中数据
			int rows = sheet.getPhysicalNumberOfRows();
			for(int i = 1; i<rows; i++) {// 第二行开始取值，第一行为标题行				
				HSSFRow row = sheet.getRow(i);		// 获取到第i列的行数据(表格行)	
				if (row != null) {
					Map<Integer, Object> map = new HashMap<Integer, Object>();
					int cells = row.getPhysicalNumberOfCells();
					for(int j=0; j<cells; j++) {					
						HSSFCell cell = row.getCell(j);	// 获取到第j行的数据(单元格)
						 if (cell != null) {
							switch (cell.getCellType()) {                       
                            case HSSFCell.CELL_TYPE_NUMERIC:
                            	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                map.put(j, cell.getStringCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_STRING:
                            	map.put(j, cell.getStringCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                break;
                            case HSSFCell.CELL_TYPE_ERROR :
                                break;
                            default:
                                break;
                            }
						 }
					}				
					list.add(map);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}				
		return list;
	}

}
