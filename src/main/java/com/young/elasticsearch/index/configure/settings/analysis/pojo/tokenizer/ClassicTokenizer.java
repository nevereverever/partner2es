package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * 经典令牌解析器,基于语法的令牌解析器，对英文（仅仅）有很好的适用性
 * @author YoungLu
 * @date 2018-04-23
 */
public class ClassicTokenizer extends Tokenizer {

	private static final long serialVersionUID = 1L;
	
	private int max_token_length = 255;//最大令牌长度
	
	public ClassicTokenizer() {
		this.type = TokenizerType.CLASSIC;
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
