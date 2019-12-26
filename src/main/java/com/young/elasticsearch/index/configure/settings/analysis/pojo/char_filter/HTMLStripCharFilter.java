package com.young.elasticsearch.index.configure.settings.analysis.pojo.char_filter;

/**
 * html字符过滤器对象
 * @author YoungLu
 * @date 2018-04-16
 */
public class HTMLStripCharFilter extends CharacterFilters {

	private static final long serialVersionUID = 1L;
	
	private String[] escaped_tags;//不被过滤的标签，例如保存到es中的文字保留b标签，只需要定义String[] s = {"b"}

	public HTMLStripCharFilter() {
		this.type = CharFilterType.HTML_STRIP;
	}
	
	public String[] getEscaped_tags() {
		return escaped_tags;
	}

	public void setEscaped_tags(String[] escaped_tags) {
		this.escaped_tags = escaped_tags;
	}

	/**
	 * 重写setType防止该对象的类型被改掉
	 */
	public void setType(CharFilterType type) {
	}
	
}
