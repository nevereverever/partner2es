package com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer;

/**
 * 标准分词器对象
 * @author YoungLu
 * @date 2018-04-23
 */
public class StandardAnalyzer extends Analyzer{
	private static final long serialVersionUID = 1L;
	
	private int max_token_length = 255;//令牌长度，超过该长度的字符串将被分为两个然后分词
	
	private String stopwords = "\\_none_";//停用词

	private String stopwords_path;//包括停用词的路径
	
	public StandardAnalyzer() {
		this.type = AnalyzerType.STANDARD;
	}

	public int getMax_token_length() {
		return max_token_length;
	}

	public void setMax_token_length(int max_token_length) {
		this.max_token_length = max_token_length;
	}

	public String getStopwords() {
		return stopwords;
	}

	public void setStopwords(String stopwords) {
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
