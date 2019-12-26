package com.young.elasticsearch.repository.search.query.pojo;

import java.io.Serializable;

/**
 * 查询过滤对象,不参与评分
 * @author YoungLu
 * @date 2018-05-18
 */
public class Filter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Query[] query;

	public Query[] getQuery() {
		return query;
	}
	
	/**
	 * 得到一个查询过滤条件
	 * @return
	 */
	public Query getQueryOne() {
		return query[0];
	}

	public void setQuery(Query... query) {
		this.query = query;
	}
	
}
