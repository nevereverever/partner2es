package com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer;

/**
 * 关键词分词器
 * @author YoungLu
 * @date 2018-04-23
 */
public class KeywordAnalyzer extends Analyzer{
	
	private static final long serialVersionUID = 1L;

	public KeywordAnalyzer() {
		this.type = AnalyzerType.KEYWORD;
	}

	@Override
	public void setType(AnalyzerType type) {
	}
	
}
