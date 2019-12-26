package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

public class IKTokenizer extends Tokenizer{
	private static final long serialVersionUID = 1L;
	
	private IkType ikType = IkType.ik_max_word;
	
	public enum IkType{ik_max_word,ik_smart};//两种ik类型
	
	public IKTokenizer() {
		this.type = TokenizerType.IK;
	}

	@Override
	public void setType(TokenizerType type) {
	}

	public IkType getIkType() {
		return ikType;
	}

	public void setIkType(IkType ikType) {
		this.ikType = ikType;
	}
	
}
