package com.young.elasticsearch.index.configure.settings.analysis.pojo.filter;

import java.io.Serializable;

/**
 * 过滤器父类
 * @author YoungLu
 * @date 2018-04-16
 * { @value filterName 过滤器名称}
 * { @value type 过滤器类型}
 */
public class Filters implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name; //过滤器名称
	
	protected FilterType type;//过滤器类型

	public String getName() {
		//过滤器名称如果为空,取类型名(最小化)
		if (this.name == null) {
			this.name = this.type.name().toLowerCase();
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FilterType getType() {
		return type;
	}

	public void setType(FilterType type) {
		this.type = type;
	}

}
