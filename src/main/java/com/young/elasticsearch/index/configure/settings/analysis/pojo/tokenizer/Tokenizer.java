package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

import java.io.Serializable;

/**
 * 令牌解析器对象
 * @author YoungLu
 * @date 2017-09-15
 * {@value name 令牌解析器名称}
 * {@value type 令牌解析器类型（nGram,pattern...)}
 */
public class Tokenizer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;//令牌解析器名称,如果使用非自定义类型,请保持该值为空
	
	protected TokenizerType type;//令牌解析器类型
	
	public String getName() {
		return name;
	}

	/**
	 * 令牌解析器名称,如果使用非自定义类型,请保持该值为空
	 * @param name 令牌解析器名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	public TokenizerType getType() {
		return type;
	}

	public void setType(TokenizerType type) {
		this.type = type;
	}

}
