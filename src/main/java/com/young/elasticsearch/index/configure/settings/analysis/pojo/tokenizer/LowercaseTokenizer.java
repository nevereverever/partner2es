package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * 小写化令牌解析器，将英文字母变为小写
 * @author YoungLu
 * @date 2018-04-23
 */
public class LowercaseTokenizer extends Tokenizer {
	private static final long serialVersionUID = 1L;

	public LowercaseTokenizer() {
		this.type = TokenizerType.LOWERCASE;
	}
	
	@Override
	public void setType(TokenizerType type) {
	}
}
