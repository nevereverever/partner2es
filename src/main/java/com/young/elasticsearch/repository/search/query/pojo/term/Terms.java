package com.young.elasticsearch.repository.search.query.pojo.term;


import com.young.elasticsearch.repository.search.query.pojo.Query;
import com.young.elasticsearch.repository.search.query.pojo.QueryType;

public class Terms extends Query {
	private static final long serialVersionUID = 1L;
	
	private String field;//参与查询的字段.对于多条件无需设置该参数
	
	private Object[] value;//参与查询的字符串

	public Terms(String field,Object[] value) {
		this.field = field;
		this.value = value;
		this.queryType = QueryType.TERMS;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object[] getValue() {
		return value;
	}

	public void setValue(Object[] value) {
		this.value = value;
	}
	
}
