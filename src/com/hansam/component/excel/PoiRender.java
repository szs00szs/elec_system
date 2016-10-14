package com.hansam.component.excel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jfinal.render.Render;

public class PoiRender extends Render {
	// private final static String CONTENT_TYPE = "application/msexcel;charset="
	// + getEncoding();

	private HSSFWorkbook wb;
	private String name;

	public PoiRender(HSSFWorkbook wb, String name) {
		this.wb = wb;
		this.name = name;

	}

	@Override
	public void render() {

		String filename = null;
		try {
			filename = new String(name.getBytes("utf-8"), "iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		response.addHeader("Content-disposition", "attachment; filename=" + filename);
		response.setContentType("application/x-msdownload");

		try {
			wb.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}