package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * 边缘ngram令牌分析器
 * @author YoungLu
 * @date 2018-04-23
 */
public class EdgeNGramTokenizer extends Tokenizer {

private static final long serialVersionUID = 1L;
	
	private String[] token_chars;//分词的形式:letter,digit,whitespace,punctuation,symbol
	
	private int min_gram = 1;//最小分词大小默认值
	
	private int max_gram = 2;//最大分词大小默认值 
	
	public EdgeNGramTokenizer() {
		this.type = TokenizerType.EDGE_NGRAM;
	}

	public String[] getToken_chars() {
		return token_chars;
	}

	public void setToken_chars(String[] token_chars) {
		this.token_chars = token_chars;
	}

	public int getMin_gram() {
		return min_gram;
	}

	public void setMin_gram(int min_gram) {
		this.min_gram = min_gram;
	}

	public int getMax_gram() {
		return max_gram;
	}

	public void setMax_gram(int max_gram) {
		this.max_gram = max_gram;
	}

	@Override
	public void setType(TokenizerType type) {
	}

	
}
