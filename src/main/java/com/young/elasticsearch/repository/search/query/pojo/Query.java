package com.young.elasticsearch.repository.search.query.pojo;

import java.io.Serializable;

/**
 * 查询的父类对象
 * @author YoungLu
 * @date 2018-05-17
 */
public class Query implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected QueryType queryType;//查询类型

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	};
	
}
