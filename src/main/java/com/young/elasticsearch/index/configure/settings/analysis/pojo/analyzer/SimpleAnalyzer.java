package com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer;

public class SimpleAnalyzer extends Analyzer{
	
	private static final long serialVersionUID = 1L;
	
	public SimpleAnalyzer() {
		this.type = AnalyzerType.SIMPLE;
	}

	
}
