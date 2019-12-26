package com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer;

/**
 * 空格分词器对象
 * @author YoungLu
 * @date 2018-04-23
 */
public class WhitespaceAnalyzer extends Analyzer{
	
	private static final long serialVersionUID = 1L;
	
	public WhitespaceAnalyzer() {
		this.type = AnalyzerType.WHITESPACE;
	}

	@Override
	public void setType(AnalyzerType type) {
	}

	
	
}
