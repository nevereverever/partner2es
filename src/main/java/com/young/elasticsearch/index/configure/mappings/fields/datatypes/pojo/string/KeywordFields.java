package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.string;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.Datatypes;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.normalizer.Normalizer;

/**
 * 关键字字段
 * @author YoungLu
 * @date 2018-05-03
 */
public class KeywordFields extends StringFields{
	private static final long serialVersionUID = 1L;
	
	private boolean doc_values = true;//该字段是否应该以列步幅方式存储在磁盘上，以便以后可以将它用于排序，聚合或脚本
	
	private long ignore_above = new Long("2147483647");
	
	private String null_value;

	private Normalizer normalizer;

	public KeywordFields() {
		this.dataType = Datatypes.KEYWORD;
		this.norms = false;
		this.index_options = IndexOptions.docs.name();//索引时存储什么信息
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

	public long getIgnore_above() {
		return ignore_above;
	}

	public void setIgnore_above(long ignore_above) {
		this.ignore_above = ignore_above;
	}

	public String getNull_value() {
		return null_value;
	}

	public void setNull_value(String null_value) {
		this.null_value = null_value;
	}

	public Normalizer getNormalizer() {
		return normalizer;
	}

	public void setNormalizer(Normalizer normalizer) {
		this.normalizer = normalizer;
	}

	public String getIndex_options() {
		return index_options;
	}

	public void setIndex_options(String index_options) {
		this.index_options = index_options;
	}
	
	
}
