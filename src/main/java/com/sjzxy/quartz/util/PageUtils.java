package com.sjzxy.quartz.util;


import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * 分页工具类
 * 
 * @author asiainfo
 *  
 * @date 2016年11月4日 下午12:59:00
 */
/**
 * @title:修改
 * @author:姚子瑶
 * @date:2018年4月10日
 * 修改内容：修改变量名totalCount => total
 * list => data
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	//总记录数
	private int total;
	//每页记录数
	private int pageSize;
	//总页数
	private int totalPage;
	//当前页数
	private int currPage;
	//列表数据
	private List<?> data;
	
	/**
	 * 分页
	 * @param data        列表数据
	 * @param total  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 */
	public PageUtils(List<?> data, int total, int pageSize, int currPage) {
		this.data = data;
		this.total = total;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)total/pageSize);
	}
	
	

	public PageUtils(List<?> resultList, Page<Object> page) {
		this.data = resultList;
		this.total = (int) page.getTotal();
		this.pageSize = page.getPageSize();
		this.currPage = page.getPageNum();
		this.totalPage = page.getPages();
	}



	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
	
}
