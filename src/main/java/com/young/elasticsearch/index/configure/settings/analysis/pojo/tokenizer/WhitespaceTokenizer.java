package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * 空格分词器
 * @author YoungLu
 * @date 2018-04-23
 */
public class WhitespaceTokenizer extends Tokenizer {
	
	private static final long serialVersionUID = 1L;
	
	private int max_token_length = 255;//最大分解长度
	
	public WhitespaceTokenizer() {
		this.type = TokenizerType.WHITESPACE;
	}

	public int getMax_token_length() {
		return max_token_length;
	}

	public void setMax_token_length(int max_token_length) {
		this.max_token_length = max_token_length;
	}

	@Override
	public void setType(TokenizerType type) {
	}
	
	
}
