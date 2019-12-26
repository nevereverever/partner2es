package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * 简单模式令牌解析器
 * @author YoungLu
 * @date 2018-04-23
 */
public class SimplePatternTokenizer extends Tokenizer{

	private static final long serialVersionUID = 1L;
	
	private String pattern = "";//模式

	public SimplePatternTokenizer() {
		this.type = TokenizerType.SIMPLE_PATTERN;
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
