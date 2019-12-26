package com.young.elasticsearch.repository.search.sort;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.Datatypes;
import com.young.elasticsearch.repository.search.sort.pojo.Sort;
import org.elasticsearch.search.sort.*;

/**
 * 排序builder
 * @author YoungLu
 * @date 2018-05-22
 */
public class EsSortBuilder {
	public static SortBuilder<?> sortBuilder(Sort sort){
		//排序对象为空,直接返回空值
		if (sort == null) {
			return null;
		}
		
		//字段名
		String field = sort.getField();
		//字段名为空,直接返回空值
		if (field == null) {
			return null;
		}
		
		FieldSortBuilder sortBuilder = SortBuilders.fieldSort(field);
		
		//排序方式
		SortOrder order = sort.getOrder();
		if (order != null) {
			sortBuilder = sortBuilder.order(order);
		}
		//排序模式
		SortMode sortMode = sort.getSortMode();
		if (sortMode != null) {
			sortBuilder = sortBuilder.sortMode(sortMode);
		}
		//处理缺失字段的方式
		Sort.Missing missing = sort.getMissing();
		if (missing != null) {
			sortBuilder = sortBuilder.missing(missing.toString());
		}
		//未被映射的处理类型
		Datatypes type = sort.getType();
		if (type != null) {
			sortBuilder = sortBuilder.unmappedType(type.getValue());
		}
		
		return sortBuilder;
	}
}
