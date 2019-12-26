package com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer;

import java.io.Serializable;

/**
 * 分词器对象
 * @author YoungLu
 * @date 2018-04-16
 */
public class Analyzer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;//分词器名称
	
	protected AnalyzerType type;//分词器类型
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AnalyzerType getType() {
		return type;
	}

	public void setType(AnalyzerType type) {
		this.type = type;
	}

}
