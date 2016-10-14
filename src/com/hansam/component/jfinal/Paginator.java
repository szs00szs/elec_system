package com.hansam.component.jfinal;

/**
* @author 时帅帅 945210972@qq.com
* @version 创建时间：2016年8月27日 下午4:13:29
*/
public class Paginator {
	private int pageNo;
	private int pageSize;
	private int totalRecords;

	public Paginator() {
		this.pageNo = 1;
		// 默认10
		this.pageSize =10;
	}

	public Paginator(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getFirstResult() {
		if (pageNo <= 0) {
			return 0;
		}
		return (pageNo - 1) * pageSize;
	}

	public int getMaxResults() {
		return pageSize;
	}
}

