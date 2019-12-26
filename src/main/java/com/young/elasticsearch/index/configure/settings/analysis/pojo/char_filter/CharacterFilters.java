package com.young.elasticsearch.index.configure.settings.analysis.pojo.char_filter;

import java.io.Serializable;

public class CharacterFilters implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;//字符集过滤器名称
	
	protected CharFilterType type;//字符集过滤器类型

	public String getName() {
		//字符过滤器名称如果为空,取type的值(最小化)
		if (this.name == null) {
			this.name = this.type.name().toLowerCase();
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CharFilterType getType() {
		return type;
	}

	public void setType(CharFilterType type) {
		this.type = type;
	}
	
}
