package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.object;

/*************************
 * @author: YoungLu
 * @date: 2019/12/18 11:17
 * @description: 一种特殊类型,直接继承自ObjectFields
 **************************/
public class MappingFields extends ObjectFields {
    @Override
    public String getFieldName() {
        if (super.getFieldName() == null) {
            return "_doc";
        }
        return super.getFieldName();
    }
}
