package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * 关键词令牌解析器
 * @author YoungLu
 * @date 2018-04-23
 */
public class KeywordTokenizer extends Tokenizer {
	
	private static final long serialVersionUID = 1L;
	
	private int buffer_size = 256;
	
	public KeywordTokenizer() {
		this.type = TokenizerType.KEYWORD;
	}

	@Override
	public void setType(TokenizerType type) {
	}

	public int getBuffer_size() {
		return buffer_size;
	}

	public void setBuffer_size(int buffer_size) {
		this.buffer_size = buffer_size;
	}

	
}
