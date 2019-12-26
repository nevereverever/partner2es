package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

public class SimplePatternSplitTokenizer extends Tokenizer {

	private static final long serialVersionUID = 1L;

	private String pattern = "";
	
	public SimplePatternSplitTokenizer() {
		this.type = TokenizerType.SIMPLE_PATTERN_SPLIT;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public void setType(TokenizerType type) {
	}

}
