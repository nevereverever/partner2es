package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * 字母令牌解析器，按单词分词
 * @author YoungLu
 * @date 2018-04-17
 */
public class LetterTokenizer extends Tokenizer {

	private static final long serialVersionUID = 1L;

	public LetterTokenizer() {
		this.type = TokenizerType.LETTER;
	}

	@Override
	public void setType(TokenizerType type) {
	}
	
}
