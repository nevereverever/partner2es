package com.young.elasticsearch.repository.search.query;

import java.io.Serializable;

public class Page implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int pageSize = 10; //每页显示记录数
	private int totalPage;		//总页数
	private int totalResult;	//总记录数
	private long totalRealResult;//真实总记录数
	private int currentPage;	//当前页
	private int currentResult;	//当前记录起始索引
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		if( pageSize > 0 ){
			this.pageSize = pageSize;
		}else{
			System.out.println("error: can't set Page.pageSize to 0 ");
		}
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(int totalResult) {
		//查询总数不能超过100000
		this.totalResult = (totalResult > 100000) ? 100000:totalResult;
		if(totalResult%pageSize==0)
			totalPage = totalResult/pageSize;
		else
			totalPage = totalResult/pageSize+1;	
	}
	public long getTotalRealResult() {
		return totalRealResult;
	}
	public void setTotalRealResult(long totalRealResult) {
		this.totalRealResult = totalRealResult;
	}
	public int getCurrentPage() {
		if(currentPage<=0)
			currentPage = 1;
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		
	}
	public int getCurrentResult() {
		currentResult = (getCurrentPage()-1)*getPageSize();
		if(currentResult<0)
			currentResult = 0;
		return currentResult;
	}
	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}
	
}
