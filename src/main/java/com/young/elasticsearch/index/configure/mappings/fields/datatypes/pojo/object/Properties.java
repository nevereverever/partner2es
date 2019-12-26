package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.object;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.Fields;

import java.util.List;

/**
 * Object类型--properties
 * 
 * "offer":{
        	"properties":{
                 "price":{
                    "type":"double"
                 },
                 "color":{
                    "type":"keyword"
                 }
            }
   }
 * 
 * @author YoungLu
 * @date 2018-05-02
 */
public class Properties {
	private List<Fields> fields;

	public List<Fields> getFields() {
		return fields;
	}

	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}

}
