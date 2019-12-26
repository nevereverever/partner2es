package com.young.elasticsearch.index.configure.settings.analysis.pojo.filter;

/**
 * 大写过滤器
 * @author YoungLu
 * @date 2018-05-03
 */
public class UppercaseFilter extends Filters{
	private static final long serialVersionUID = 1L;
	
	public UppercaseFilter() {
		this.type = FilterType.UPPERCASE;
	}

	@Override
	public void setType(FilterType type) {
	}
	
	
}
