package com.young.elasticsearch.repository.search.query.pojo.fulltext;

import com.young.elasticsearch.repository.search.query.pojo.QueryType;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.Operator;

/**
 * 多字段查询
 * @author Administrator
 * @date 2018-05-17
 */
public class MultiMatch extends Match {

	private static final long serialVersionUID = 1L;
	
	private Type type;//多字段查询的类型
	
	private String[] fieldNames;//指定的字段
	
	private Operator operator;//操作符

	public MultiMatch(String[] fieldNames ,Object query) {
		super(null, query);
		this.fieldNames = fieldNames;
		this.queryType = QueryType.MULTI_MATCH;
	}
	
	@Override
	public void setQueryType(QueryType queryType) {
		this.queryType = QueryType.MULTI_MATCH;
	}
	
	@Override
	public QueryType getQueryType() {
		return QueryType.MULTI_MATCH;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Operator getOperator() {
		return operator;
	}
	
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String[] getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String... fieldNames) {
		this.fieldNames = fieldNames;
	}

}
