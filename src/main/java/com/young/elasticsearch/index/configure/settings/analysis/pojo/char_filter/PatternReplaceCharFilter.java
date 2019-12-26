package com.young.elasticsearch.index.configure.settings.analysis.pojo.char_filter;

/**
 * 正则表达式替换字符过滤器对象
 * @author YoungLu
 * @date 2018-04-16
 */
public class PatternReplaceCharFilter extends CharacterFilters{
	
	private static final long serialVersionUID = 1L;

	private String pattern = "\\W+";;//正则表达式
	
	private String replacement;//替换后的文字
	
	private String flags;//加入控制正则表达式的匹配行为的参数,管道形式。例如：“CASE_INSENSITIVE|COMMENTS”
	
	public PatternReplaceCharFilter() {
		this.type = CharFilterType.PATTERN_REPLACE;
	}
	
	/**
	 * 重写setType防止该对象的类型被改掉
	 */
	public void setType(CharFilterType type) {
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}
	
	
}
