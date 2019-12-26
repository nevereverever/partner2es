package com.young.elasticsearch.repository.search.query.pojo.term;

import com.young.elasticsearch.repository.search.query.pojo.QueryType;

/**
 * 前缀查询
 * @author YoungLu
 * @date 2018-05-18
 */
public class Prefix extends Term {

	private static final long serialVersionUID = 1L;
	
	public Prefix(String field,Object value) {
		super(field,value);
		this.queryType = QueryType.PREFIX;
	}

	@Override
	public void setQueryType(QueryType queryType) {
		this.queryType = QueryType.PREFIX;
	}
	
}
