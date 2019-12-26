package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * 泰语令牌解析器
 * @author YoungLu
 * @date 2018-04-23
 */
public class ThaiTokenizer extends Tokenizer {

	private static final long serialVersionUID = 1L;
	
	public ThaiTokenizer() {
		this.type = TokenizerType.THAI;
	}
	
	@Override
	public void setType(TokenizerType type) {
	}
}
