package com.hansam.modules.admin.student;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hansam.component.annotation.ControllerBind;
import com.hansam.component.excel.PoiKit;
import com.hansam.component.excel.PoiRender;
import com.hansam.component.jfinal.BaseController;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author 时帅帅 945210972@qq.com
 * @version 创建时间：2016年8月12日 下午4:39:16
 */
@ControllerBind(controllerKey="admin/student")
public class StudentController extends BaseController {

	public static final String path = "/pages/admin/student/student_";

	public void index() {
		renderText("Hello JFinal World");
	}

	public void login() throws UnsupportedEncodingException {
		// render("login.html");

		renderText("this is :");
	}

	public void list() {
		// List<Student> list = Student.dao.find("select * from student");
		// renderText("id:"+student.getInt("id") + " \nname:" +
		// student.getStr("name"));

		Page<Student> page = Student.dao.paginate(getParaToInt(0, 1), 5, "select *", " from student");

		List<Student> list = page.getList();

		setAttr("page", page);
		setAttr("malenum", getMale());
		setAttr("femalenum", getAllStudent()-getMale());
		render(path + "list.html");
	}

	public void listAll() {
		String select = "SELECT s.id sid,s.name sname,s.sex ssex,c.id cid,c.name cname";

		String from = " FROM student s,course c";
		Page<Record> page = Db.paginate(getParaToInt(0, 1), 5, select, from);

		setAttr("page", page);
		render(path + "listAll.html");
	}

	// 学生总数
	public Long getAllStudent() {
		String sql = "select count(*) as allnum from student";
		Record record = Db.findFirst(sql);
		Long allnum = record.getLong("allnum");
		return allnum;
	}

	// 男生总数
	public Long getMale() {
		String sql = "select count(*) as malenum from student where sex='male'";
		Record record = Db.findFirst(sql);
		Long malenum = record.getLong("malenum");
		return malenum;
	}

	public void export() {

		String sql = "select * from student";

		List<Record> list = Db.find(sql);

		String filename = "测试导出";

		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String title = dateFormat.format(d);

		String s[] = { "id", "name", "sex" };
		render(new PoiRender(PoiKit.saveFile(s,s, list), title + filename + "_统计表" + ".xls"));

	}

}
