package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.Datatypes;

import java.io.Serializable;

/**
 * 字段对象
 * @author YoungLu
 * @date 2018-04-24
 */
public class Fields implements Serializable{
	private static final long serialVersionUID = 1L;

	private String fieldName;//字段名
	
	protected Datatypes dataType;//数据类型
	
	/***
	 * "name":{
				"type":"keyword",
				"fields":{
					"pinyin":{
						"type":"text",
						"store":false,
						"analyzer":"pinyin_analyzer"
					}
				}
	   }
	 */
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Datatypes getDataType() {
		return dataType;
	}

	public void setDataType(Datatypes dataType) {
		this.dataType = dataType;
	}

}
