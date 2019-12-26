package com.young.elasticsearch.index.configure.settings.analysis.pojo.filter;

import java.io.Serializable;

public enum FilterType {
	PINYIN("pinyin","拼音过滤器"),
	STANDARD("standard","标准过滤器"),
	ASCIIFOLDING("asciifolding","ASCII码折叠过滤器"),
	FLATTEN_GRAPH("flatten_graph",""),
	LENGTH("length","长度过滤器"),
	LOWERCASE("lowercase","小写过滤器"),
	UPPERCASE("uppercase","大写过滤器"),
	NGRAM("nGram","ngram过滤器"),
	EDGENGRAM("edgeNGram","边缘ngram过滤器"),
	POTER_STEM("porter_stem","词干过滤器"),
	SHINGLE("shingle","shingle过滤器"),
	STOP("stop","停用词过滤器"),
	WORD_DELIMITER("word_delimiter","单词分隔符过滤器"),
	WORD_DELIMITER_GRAPH("word_delimiter_graph","单词分隔符图形过滤器"),
	STEMMER_OVERRIDE("stemmer_override","覆盖词干过滤器"),
	KEYWORD_MARKER("keyword_marker","关键字标记过滤器"),
	KEYWORD_REPEAT("keyword_repeat","关键字重复过滤器"),
	KSTEM("kstem","英文字母的高性能过滤器(必须已经被处理为小写)"),
	SNOWBALL("snowball",""),
	SYNONYM("synonym","同义字过滤器"),
	SYNONYM_GRAPH("synonym_graph","同义字图形过滤器"),
	REVERSE("reverse","反向过滤器"),
	ELISION("elision","省略过滤器"),
	TRUNCATE("truncate","截断过滤器"),
	UNIQUE("unique","唯一过滤器"),
	PATTERN_CAPTURE("pattern_capture","模式捕获过滤器"),
	PATTERN_REPLACE("pattern_replace","模式替换过滤器"),
	TRIM("trim","修剪空格过滤器"),
	LIMIT("limit","限制过滤器"),
	HUNSPELL("hunspell","hunspell过滤器"),
	COMMON_GRAMS("common_grams","常见词过滤器"),
	DELIMITED_PAYLOAD("delimited_payload","负载分割过滤器"),
	KEEP("keep","保留过滤器"),
	KEEP_TYPES("keep_types","保留类型过滤器"),
	CLASSIC("classic","经典过滤器"),
	APOSTROPHE("apostrophe","撇号过滤器"),
	DECIMAL_DIGIT("decimal_digit ","十进制数字过滤器"),
	FINGERPRINT("fingerprint","指纹过滤器"),
	MIN_HASH("min_hash","最小哈希过滤器"),
	;
	
	
	private String value;//类型的值
    private String desc;//类型的描述
	
	FilterType(final String value,final String desc){
		this.value = value;
		this.desc = desc;
	}

	public Serializable getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
}
