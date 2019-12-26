package com.young.elasticsearch.repository.search.query.pojo.term;

import com.young.elasticsearch.repository.search.query.pojo.Query;
import com.young.elasticsearch.repository.search.query.pojo.QueryType;

/**
 * term术语查询.术语查询查找包含倒排索引中指定的确切术语的文档。
 * @author YoungLu
 * @date 2018-05-17
 */
public class Term extends Query {

	private static final long serialVersionUID = 1L;
	
	private String field;//参与查询的字段.对于多条件无需设置该参数
	
	Object value;//参与查询的字符串
	
	private float boost = 1.0f ;//权值.默认1.0

	public Term(String field,Object value) {
		this.field = field;
		this.value = value;
		this.queryType = QueryType.TERM;
	}
	
	@Override
	public void setQueryType(QueryType queryType) {
		this.queryType = QueryType.TERM;
	}
	
	public float getBoost() {
		return boost;
	}

	public void setBoost(float boost) {
		this.boost = boost;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
