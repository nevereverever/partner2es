package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * 标准令牌解析器
 * @author YoungLu
 * @date 2018-04-17
 */
public class StandardTokenizer extends Tokenizer {
	
	private static final long serialVersionUID = 1L;
	
	private int max_token_length = 255;//最大分解长度，超过该长度的将被分成两个tokens
	//示例：The 2 QUICK Brown-Foxes jumped over the lazy dog's bone.
	//=》[ The, 2, QUICK, Brown, Foxes, jumpe, d, over, the, lazy, dog's, bone ]

	public StandardTokenizer() {
		this.type = TokenizerType.STANDARD;
	}
	
	public int getMax_token_length() {
		return max_token_length;
	}

	public void setMax_token_length(int max_token_length) {
		this.max_token_length = max_token_length;
	}

	@Override
	public void setType(TokenizerType type) {
		// TODO Auto-generated method stub
		super.setType(type);
	}

	
}
