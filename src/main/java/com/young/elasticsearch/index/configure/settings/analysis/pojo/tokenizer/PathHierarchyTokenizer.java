package com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer;

/**
 * 路径令牌解析器
 * @author YoungLu
 * @date 2018-04-23
 */
public class PathHierarchyTokenizer extends Tokenizer {

	private static final long serialVersionUID = 1L;
	
	private String delimiter = "/";//用作路径分割符的字符
	
	private String replacement ;//一个可选的用于替换路径分隔符的替换字符
	
	private int buffer_size = 1024;//一次读入term缓冲区的字符数，默认1024，建议不要更改
	
	private boolean reverse = false;//如果设置为true，则以相反的顺序发出令牌
	
	private int skip = 0 ;//跳过初始令牌的数量，默认0
	
	public PathHierarchyTokenizer() {
		this.type = TokenizerType.PATH_HIRRARCHY;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	public int getBuffer_size() {
		return buffer_size;
	}

	public void setBuffer_size(int buffer_size) {
		this.buffer_size = buffer_size;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	/**
	 * 重写setType防止该对象的类型被改掉
	 */
	public void setType(TokenizerType type) {
	}
	
}
