package com.young.elasticsearch.repository.search.query.pojo.compound.bool;

import com.young.elasticsearch.repository.search.query.pojo.Query;

import java.io.Serializable;

/**
 * bool查询，must条件
 * @author YoungLu
 * @date 2018-05-18
 */
public class Must implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Query[] query;//查询方法

	public Query[] getQuery() {
		return query;
	}

	public void setQuery(Query[] query) {
		this.query = query;
	}
	
}
