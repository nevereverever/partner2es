package com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer;

public class StopAnalyzer extends Analyzer{
	private static final long serialVersionUID = 1L;

	private String[] stopwords = {"_english_"};//停用词
	
	private String stopwords_path ;//停用词路径

	public StopAnalyzer() {
		this.type = AnalyzerType.STOP;
	}

	public String[] getStopwords() {
		return stopwords;
	}

	public void setStopwords(String[] stopwords) {
		this.stopwords = stopwords;
	}

	public String getStopwords_path() {
		return stopwords_path;
	}

	public void setStopwords_path(String stopwords_path) {
		this.stopwords_path = stopwords_path;
	}

	@Override
	public void setType(AnalyzerType type) {
	}

}
