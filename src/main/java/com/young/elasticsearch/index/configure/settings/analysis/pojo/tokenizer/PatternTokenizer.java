package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * 模式令牌解析器
 * @author YoungLu
 * @date 2018-04-23
 */
public class PatternTokenizer extends Tokenizer {

	private static final long serialVersionUID = 1L;
	
	private String pattern = "\\W+";//模式
	
	private String flags;////加入控制正则表达式的匹配行为的参数,管道形式。例如：“CASE_INSENSITIVE|COMMENTS”
	
	private int group = -1;
	
	public PatternTokenizer() {
		this.type = TokenizerType.PATTERN;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	@Override
	public void setType(TokenizerType type) {
	}

	
}

