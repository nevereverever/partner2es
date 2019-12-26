package com.young.elasticsearch.repository.search.query.pojo.term;

import com.young.elasticsearch.repository.search.query.pojo.QueryType;

/**
 * 通配符匹配
 * @author YoungLu
 * @date 2018-05-18
 */
public class Wildcard extends Term{

	private static final long serialVersionUID = 1L;
	
	public Wildcard(String field,Object value) {
		super(field, value);
		this.queryType = QueryType.WILDCARD;
	}

	@Override
	public void setQueryType(QueryType queryType) {
		this.queryType = QueryType.WILDCARD;
	}
	
}
