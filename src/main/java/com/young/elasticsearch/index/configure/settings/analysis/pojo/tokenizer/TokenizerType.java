package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

import java.io.Serializable;

public enum TokenizerType {
	STANDARD("standard","标准令牌解析器"),
	LETTER("letter","字母令牌解析器"),
	LOWERCASE("lowercase","小写令牌解析器"),
	WHITESPACE("whitespace","空格令牌解析器"),
	UAX_URL_EMAIL("uax_url_email","邮件令牌解析器"),
	CLASSIC("classic","经典令牌解析器"),
	THAI("thai","泰语令牌解析器"),
	NGRAM("ngram","ngram令牌解析器"),
	EDGE_NGRAM("edge_ngram","边界ngram令牌解析器"),
	KEYWORD("keyword","关键字令牌解析器"),
	PATTERN("pattern","模式令牌解析器"),
	SIMPLE_PATTERN("simple_pattern","简单模式令牌解析器"),
	SIMPLE_PATTERN_SPLIT("simple_pattern_split","简单模式分解令牌解析器"),
	PATH_HIRRARCHY("path_hierarchy","路径令牌解析器"),
	IK("ik","ik中文令牌解析器");
	
	
	
	private String value;//类型的值
    private String desc;//类型的描述
	
	TokenizerType(final String value,final String desc){
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
