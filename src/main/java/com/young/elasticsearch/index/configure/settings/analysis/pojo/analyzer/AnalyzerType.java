package com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer;

import java.io.Serializable;

/**
 * elasticsearch中分词器类型
 * @author YoungLu
 * @date 2018-04-16
 */
public enum AnalyzerType {
	CUSTOM("custom","自定义分词器"),
	STANDARD("standard","标准分词器"),
	SIMPLE("simple","简易分词器，无配置"),
	WHITESPACE("whitespace","空格分词器,无配置"),
	STOP("stop","停词分词器"),
	KEYWORD("keyword","关键词分词器"),
	PATTERN("pattern","模式分词器"),
	FINGERPRINT("fingerprint","指纹分词器")
	;
	
	
	private String value;
    private String desc;
	
	AnalyzerType(final String value,final String desc){
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
