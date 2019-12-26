package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * URL EMAIL令牌解析器
 * @author YoungLu
 * @date 2018-04-23
 */
public class UAXURLEmailTokenizer extends Tokenizer {

	private static final long serialVersionUID = 1L;
	
	private int max_token_length = 255;//最大分解长度
	
	public UAXURLEmailTokenizer() {
		this.type = TokenizerType.UAX_URL_EMAIL;
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
