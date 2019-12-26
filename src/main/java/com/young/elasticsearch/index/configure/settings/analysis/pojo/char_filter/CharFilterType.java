package com.young.elasticsearch.index.configure.settings.analysis.pojo.char_filter;

import java.io.Serializable;

public enum CharFilterType {
	HTML_STRIP("html_strip","html字符过滤器"),
	MAPPING("mapping","字符映射过滤器"),
	PATTERN_REPLACE("pattern_replace","正则表达式替换过滤器");
	
	
	private String value;//类型的值
    private String desc;//类型的描述
	
	CharFilterType(final String value,final String desc){
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
