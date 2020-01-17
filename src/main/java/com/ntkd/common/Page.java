package com.ntkd.common;

import java.util.List;

public class Page {
	
	//总数
	private long total;
	//当前分页数据集合
	private List<?> list;
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "Page [total=" + total + ", list=" + list
				+ "]";
	}
	
	

}
