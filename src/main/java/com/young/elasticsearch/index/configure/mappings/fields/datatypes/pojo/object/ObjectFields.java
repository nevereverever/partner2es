package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.object;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.Fields;

/**
 * Object字段类型
 * @author YoungLu
 * @date 2018-05-02
 */
public class ObjectFields extends Fields {

	private static final long serialVersionUID = 1L;

	private boolean enable = true;//是否应该解析和索引给出的对象字段的json值

	private boolean dynamic = true;//是否应将新properties动态添加到现有对象
	
	private Properties properties;//对象中的字段，可以是任何数据类型，包括Object

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
}
