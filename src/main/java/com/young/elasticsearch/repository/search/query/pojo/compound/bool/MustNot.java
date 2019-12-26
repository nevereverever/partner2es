package com.young.elasticsearch.repository.search.query.pojo.compound.bool;

import com.young.elasticsearch.repository.search.query.pojo.Query;

import java.io.Serializable;

/**
 * bool查询must_not条件
 * @author YoungLu
 * @date 2018-05-18
 */
public class MustNot implements Serializable{
private static final long serialVersionUID = 1L;
	
	private Query[] query;//查询方法

	public Query[] getQuery() {
		return query;
	}

	public void setQuery(Query[] query) {
		this.query = query;
	}
}
