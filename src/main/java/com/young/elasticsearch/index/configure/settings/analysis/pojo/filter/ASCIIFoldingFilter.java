package com.young.elasticsearch.index.configure.settings.analysis.pojo.filter;

/**
 * ASCII折叠过滤器
 * @author Administrator
 *
 */
public class ASCIIFoldingFilter extends Filters{
	private static final long serialVersionUID = 1L;
	
	private boolean preserve_original = false;//保存源字符串

	public ASCIIFoldingFilter() {
		this.type = FilterType.ASCIIFOLDING;
	}
	
	public boolean isPreserve_original() {
		return preserve_original;
	}

	public void setPreserve_original(boolean preserve_original) {
		this.preserve_original = preserve_original;
	}

	@Override
	public void setType(FilterType type) {
	}
	
}
