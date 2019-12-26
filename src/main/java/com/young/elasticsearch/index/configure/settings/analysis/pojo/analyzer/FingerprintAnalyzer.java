package com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer;

/***
 * 指纹分词器
 * @author YoungLu
 * @date 2018-04-23
 */
public class FingerprintAnalyzer extends Analyzer{
	private static final long serialVersionUID = 1L;
	
	private String separator = " ";//分开令牌的字符串，默认为空格
	
	private int max_output_size = 255;//最大输出大小
	
	private String[] stopwords = {"\\_none_"};//停用词
	
	private String stopwords_path ;//停用词路径

	public FingerprintAnalyzer() {
		this.type = AnalyzerType.FINGERPRINT;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public int getMax_output_size() {
		return max_output_size;
	}

	public void setMax_output_size(int max_output_size) {
		this.max_output_size = max_output_size;
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
