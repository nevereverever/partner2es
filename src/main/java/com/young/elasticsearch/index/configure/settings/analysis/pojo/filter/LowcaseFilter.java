package com.young.elasticsearch.index.configure.settings.analysis.pojo.filter;

/**
 * 小写过滤器
 * @author 路杨
 * @date 2018-05-03
 */
public class LowcaseFilter extends Filters{
	private static final long serialVersionUID = 1L;
	
	public LowcaseFilter() {
		this.type = FilterType.LOWERCASE;
	}

	@Override
	public void setType(FilterType type) {
	}
	
	
}
