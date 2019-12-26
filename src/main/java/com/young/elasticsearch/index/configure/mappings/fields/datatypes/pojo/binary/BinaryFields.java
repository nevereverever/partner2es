package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.binary;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.Datatypes;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.Fields;

/**
 * 二进制数据类型
 * @author YoungLu
 * @date 2018-05-04
 */
public class BinaryFields extends Fields {
	private static final long serialVersionUID = 1L;
	
	private boolean doc_values = false;//以列步幅方式放入磁盘供排序、聚合和脚本使用
	
	private boolean store = false;//字段值是否存储到_source字段被单独检索

	public BinaryFields() {
		this.dataType = Datatypes.BINARY;
	}
	
	@Override
	public void setDataType(Datatypes dataType) {
	}

	public boolean isDoc_values() {
		return doc_values;
	}

	public void setDoc_values(boolean doc_values) {
		this.doc_values = doc_values;
	}

	public boolean isStore() {
		return store;
	}

	public void setStore(boolean store) {
		this.store = store;
	}
	
}
