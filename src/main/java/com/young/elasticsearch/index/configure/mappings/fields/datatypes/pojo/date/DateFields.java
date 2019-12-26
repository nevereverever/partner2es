package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.date;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.Datatypes;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.Fields;

/**
 * 时间字段类型
 * @author YoungLu
 * @date 2018-05-02
 */
public class DateFields extends Fields {
	private static final long serialVersionUID = 1L;
	
	private float boost = 1L;//权值
	
	private boolean doc_values = true;//以列步幅的形式存储在磁盘上可供排序,聚合和脚本执行
	
	private String format = "strict_date_optional_time||epoch_millis";
	
	private boolean ignore_malformed = false;//是否忽略错误类型的数据。比如本来某个字段是整形，而丢进去的数据类型为字符型
	
	private boolean index = true;
	
	private String null_value = "NULL";//一般来说，空值不能被索引和检索，有了该选项空值可被检索
	
	private boolean store = false;//字段值是否被存储在_source中供单独检索

	public DateFields() {
		this.dataType = Datatypes.DATE;
	}
	
	@Override
	public void setDataType(Datatypes dataType) {
		this.dataType = Datatypes.DATE;
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

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
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
