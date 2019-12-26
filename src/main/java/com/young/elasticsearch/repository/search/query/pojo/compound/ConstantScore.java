package com.young.elasticsearch.repository.search.query.pojo.compound;

import com.young.elasticsearch.repository.search.query.pojo.Filter;
import com.young.elasticsearch.repository.search.query.pojo.Query;

/**
 * 恒定分数查询(之所以恒定分数，因为不考虑评分的影响)
 * @author YoungLu
 * @date 2018-05-18
 */
public class ConstantScore extends Query {

	private static final long serialVersionUID = 1L;
	
	private Filter filter;//过滤条件

	private float boost = 1.0f;//权值
	
	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public float getBoost() {
		return boost;
	}

	public void setBoost(float boost) {
		this.boost = boost;
	}
	
}
