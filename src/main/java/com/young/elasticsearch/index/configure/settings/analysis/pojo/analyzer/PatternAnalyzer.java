package com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer;

public class PatternAnalyzer extends Analyzer{
	private static final long serialVersionUID = 1L;
	
	private String pattern = "\\W+";
	
	private String flags ;//java正则表达式标志。eg "CASE_INSENSITIVE|COMMENTS".
	
	private boolean lowercase = true;//是否小写化
	
	private String[] stopwords = {"\\_none_"};
	
	private String stopwords_path ;
	
	public PatternAnalyzer() {
		this.type = AnalyzerType.PATTERN;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public boolean isLowercase() {
		return lowercase;
	}

	public void setLowercase(boolean lowercase) {
		this.lowercase = lowercase;
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
