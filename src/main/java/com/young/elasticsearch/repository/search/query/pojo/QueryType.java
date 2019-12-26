package com.young.elasticsearch.repository.search.query.pojo;

public enum QueryType {
	MATCH("match","分析提供的文本，并根据提供的文本构建布尔查询"),
	MATCH_ALL("match_all","查询所有"),
	MATCH_PHRASE("match_phrase","短语查询"),
	MATCH_PHRASE_PREFIX("match_phrase_prefix","短语前缀查询"),
	MULTI_MATCH("multi_match","多个字段查询"),
	TERM("term","术语匹配"),
	RANGE("range","范围匹配"),
	PREFIX("prefix","前缀匹配"),
	WILDCARD("wildcard","通配符匹配"),
	REGEXP("regexp","正则表达式匹配"),
	FUZZY("fuzzy","模糊匹配"),
	BOOL("bool","bool联合查询"),
	TERMS("terms","多术语查询,字段满足多个查询条件中的任意一个")
	;
	
	private String value;
	private String desc;
	
	QueryType(final String value,final String desc){
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
