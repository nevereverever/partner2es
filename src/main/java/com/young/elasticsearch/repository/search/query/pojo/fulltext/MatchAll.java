package com.young.elasticsearch.repository.search.query.pojo.fulltext;

import com.young.elasticsearch.repository.search.query.pojo.Query;
import com.young.elasticsearch.repository.search.query.pojo.QueryType;

/**
 * 查询全部
 * @author YoungLu
 * @date 2018-07-06
 */
public class MatchAll extends Query {
	private static final long serialVersionUID = 1L;
	
	private float boost = 1.0f;//权值 
	
	public MatchAll() {
		this.queryType = QueryType.MATCH_ALL;
	}
	@Override
	public void setQueryType(QueryType queryType) {
	}
	public float getBoost() {
		return boost;
	}
	public void setBoost(float boost) {
		this.boost = boost;
	}
	
}
