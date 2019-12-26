package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.range;


import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.Fields;

/**
 * 范围字段类型
 * @author YoungLu
 * @date 2018-05-04
 */
public class RangeFields extends Fields {
	private static final long serialVersionUID = 1L;

	private boolean coerce = false;//将字符串转换为数字（去掉分数）
	
	private float boost = 1L;//权值
	
	private boolean index = true;//可被检索
	
	private boolean store = false;//存储并可从_source中被检索

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

	public boolean isIndex() {
		return index;
	}

	public void setIndex(boolean index) {
		this.index = index;
	}

	public boolean isStore() {
		return store;
	}

	public void setStore(boolean store) {
		this.store = store;
	}
	
	
	
}
