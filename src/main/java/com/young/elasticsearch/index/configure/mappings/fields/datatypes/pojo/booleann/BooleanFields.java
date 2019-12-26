package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.booleann;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.Datatypes;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.Fields;

/**
 * 布尔字段类型
 * @author YoungLu
 * @date 2018-05-04
 */
public class BooleanFields extends Fields {

	private static final long serialVersionUID = 1L;
	
	private float boost = 1L;//权值
	
	private boolean doc_values = true;//以列步幅形式存储在磁盘上以用作聚合、排序和脚本调用
	
	private boolean index = true;//该列是否可被搜索
	
	private String null_value = null;//空值表示形式
	
	private boolean store = true;//在_source中存储被单独检索

	public BooleanFields() {
		this.dataType = Datatypes.BOOLEAN;
	}
	
	
	/*******************
	 * 防止类型被修改
	 ******************/
	@Override
	public void setDataType(Datatypes dataType) {
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
