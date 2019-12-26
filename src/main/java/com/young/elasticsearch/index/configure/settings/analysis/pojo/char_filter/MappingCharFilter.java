package com.young.elasticsearch.index.configure.settings.analysis.pojo.char_filter;

/**
 * 映射字符过滤器对象
 * @author YoungLu
 * @date 2018-04-16
 */
public class MappingCharFilter extends CharacterFilters {

	private static final long serialVersionUID = 1L;
	
	private String[] mappings;//映射，形如[":) => _happy_",":( => _sad_"]
	
	private String mappings_path;//映射路径，放在elasticsearch安装目录下的config目录中的文件，文件内容如上
	
	public MappingCharFilter() {
		this.type = CharFilterType.MAPPING;
	}
	
	/**
	 * 重写setType防止该对象的类型被改掉
	 */
	public void setType(CharFilterType type) {
	}

	public String[] getMappings() {
		return mappings;
	}

	public void setMappings(String[] mappings) {
		this.mappings = mappings;
	}

	public String getMappings_path() {
		return mappings_path;
	}

	public void setMappings_path(String mappings_path) {
		this.mappings_path = mappings_path;
	}
	
	
}
