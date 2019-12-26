package com.young.elasticsearch.repository.search.query.pojo.fulltext;

import com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer.Analyzer;
import com.young.elasticsearch.repository.search.query.pojo.Query;
import com.young.elasticsearch.repository.search.query.pojo.QueryType;

/**
 * MatchPhrase类型查询
 * @author YoungLu
 * @date 2018-05-17
 */
public class MatchPhrase extends Query {
	
	private static final long serialVersionUID = 1L;
	
	private float boost = 1.0f;

	private int slop = 0;//间隔的大小
	
	private Analyzer analyzer;//分析器
	
	private String field;//参与查询的字段
	
	private Object query;//参与查询的字符串
	
	public MatchPhrase(String field,Object query) {
		this.field = field;
		this.query = query;
		this.queryType = QueryType.MATCH_PHRASE;
	}

	public int getSlop() {
		return slop;
	}

	public void setSlop(int slop) {
		this.slop = slop;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	@Override
	public void setQueryType(QueryType queryType) {
		this.queryType = QueryType.MATCH_PHRASE;
	}

	public Object getQuery() {
		return query;
	}

	public void setQuery(Object query) {
		this.query = query;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public float getBoost() {
		return boost;
	}

	public void setBoost(float boost) {
		this.boost = boost;
	}
	
}
