package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.numeric;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.Fields;

public class NumericFields extends Fields {
	private static final long serialVersionUID = 1L;
	
	private boolean coerce = true;//尝试将字符串转换为数字并截断整数的分数
	
	private float boost = 1L;//权值
	
	private boolean doc_values = true;//以列步幅存入磁盘供排序、聚合和脚本调用
	
	private boolean ignore_malformed = false;//异常数字是否被忽略
	
	private boolean index = true;//是否可被搜索
	
	private String null_value ;//空值的表示
	
	private boolean store = false;//是否被单独存储到_source被单独检索

	public boolean isCoerce() {
		return coerce;
	}

	public void setCoerce(boolean coerce) {
		this.coerce = coerce;
	}

	public float getBoost() {
		return boost;
	}

	public void setBoost(float boost) {
		this.boost = boost;
	}

	public boolean isDoc_values() {
		return doc_values;
	}

	public void setDoc_values(boolean doc_values) {
		this.doc_values = doc_values;
	}

	public boolean isIgnore_malformed() {
		return ignore_malformed;
	}

	public void setIgnore_malformed(boolean ignore_malformed) {
		this.ignore_malformed = ignore_malformed;
	}

	public boolean isIndex() {
		return index;
	}

	public void setIndex(boolean index) {
		this.index = index;
	}

	public String getNull_value() {
		return null_value;
	}

	public void setNull_value(String null_value) {
		this.null_value = null_value;
	}

	public boolean isStore() {
		return store;
	}

	public void setStore(boolean store) {
		this.store = store;
	}
	
}
