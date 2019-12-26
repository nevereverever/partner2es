package com.young.elasticsearch.repository.search.query.pojo.compound.bool;

import com.young.elasticsearch.repository.search.query.pojo.Filter;
import com.young.elasticsearch.repository.search.query.pojo.Query;
import com.young.elasticsearch.repository.search.query.pojo.QueryType;

/**
 * 布尔查询
 * @author YoungLu
 * @date 2018-05-18
 */
public class Bool extends Query {

	private static final long serialVersionUID = 1L;

	private Must must;//必须要满足的条件
	
	private MustNot must_not;//必须要不满足的条件
	
	private Should should;//可以满足的条件
	
	private Filter filter;//过滤
	
	private String minimum_should_match;//最小匹配数
	
	private float boost = 1.0f;//权值
	
	public Bool() {
		this.queryType = QueryType.BOOL;
	}
	
	@Override
	public void setQueryType(QueryType queryType) {
		this.queryType = QueryType.BOOL;
	}

	public Must getMust() {
		return must;
	}

	public void setMust(Must must) {
		this.must = must;
	}

	public MustNot getMust_not() {
		return must_not;
	}

	public void setMust_not(MustNot must_not) {
		this.must_not = must_not;
	}

	public Should getShould() {
		return should;
	}

	public void setShould(Should should) {
		this.should = should;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public String getMinimum_should_match() {
		return minimum_should_match;
	}

	public void setMinimum_should_match(String minimum_should_match) {
		this.minimum_should_match = minimum_should_match;
	}

	public float getBoost() {
		return boost;
	}

	public void setBoost(float boost) {
		this.boost = boost;
	}
	
	
	
}
