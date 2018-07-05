package com.iscas.component.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author adams 分页查询的封装
 */
public class PagerModel implements Serializable {
	private int			total;							// 总数
	@SuppressWarnings("rawtypes")
	private List		list		= new ArrayList();	// 分页集合列表
	private int			pageSize	= 65536;			// 每页显示记录数
	private int			offset;						// 偏移量
	private int			pagerSize;						// 总页数
	protected String	pagerUrl;						// 分页标签需要访问的ACTION地址
	private String		currentPage;					// 当前第几页
	private String		pagerId;
	private int			recordsTotal;
	private int			recordsFiltered;
	private int			draw;
	private String id ;
	public String getPagerUrl() {
		return pagerUrl;
	}



	public String getCurrentPage() {
		return currentPage;
	}



	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}



	public String getPagerId() {
		return pagerId;
	}



	public void setPagerId(String pagerId) {
		this.pagerId = pagerId;
	}



	public void setPagerUrl(String pagerUrl) {
		this.pagerUrl = pagerUrl;
	}

	public int getPagerSize() {
		return pagerSize;
	}

	public void setPagerSize(int pagerSize) {
		this.pagerSize = pagerSize;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getList() {
		return list == null ? new LinkedList() : list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public String trim(String str) {
		if (str == null) {
			return null;
		}
		return str.trim();
	}

	public void clearList(List<String> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		list.clear();
		list = null;
	}

	public void clearSet(Set<String> set) {
		if (set == null || set.size() == 0) {
			return;
		}
		set.clear();
		set = null;
	}

	public void clearArray(String[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			arr[i] = null;
		}
		arr = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "total:" + total + ",list:" + list + ",offset:" + offset;
	}
}
