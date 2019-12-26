package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.string;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.Fields;

/**
 * 字符串对象
 * @author YoungLu
 * @date 2018-05-02
 */
public class StringFields extends Fields {

	private static final long serialVersionUID = 1L;
	
	private float boost = 1f;//权值，尽量不要修改
	
	private boolean eager_global_ordinals = false;//为true可用于加快搜索速度，但是会影响索引的效率
	
	private boolean index = true;//该字段是否允许被搜索
	
	protected String index_options;//索引时存储什么信息
	
	protected boolean norms;//评分时是否考虑字段长度

	private boolean store = false; //是否存储到_source列供单独检索
	
	private String similarity = Similarity.BM25.name();//使用哪种评分算法计算相似性
	
	private StringFields stringFields;
	
	/**
	 * docs:只有文档号被索引，只能找出字段中的单个术语
	 * freqs:文档号和术语频率被索引
	 * positions:文档号,术语频率和术语位置被索引
	 * offset:文档号,术语频率,位置和字符偏移量被索引
	 */
	public enum IndexOptions{docs,freqs,positions,offsets};
	
	/**
	 * 为每个字段分配评分算法
	 * BM25:okapi bm25算法。es和lucene中默认的算法
	 * CLASSIC:tf/idf算法。曾经在es和lucene中使用的评分函数。
	 * BOOLEAN:一个简单的布尔相似度算法。
	 */
	public enum Similarity{
		BM25("BM25"),CLASSIC("classic"),BOOLEAN("boolean"); 
		private String value;
		Similarity(final String value){
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		} ;
		
	/**
	 * no 不存储术语向量
	 * yes 存储字段中的术语向量
	 * with_position 术语和位置被存储
	 * with_offsets 术语和字符偏移量被存储
	 * with_positions_offsets 术语,位置和字符偏移量均被存储
	 */
	public enum TermVector {
		no,yes,with_position,with_offsets,with_positions_offsets
	}
	
	public float getBoost() {
		return boost;
	}

	public void setBoost(float boost) {
		this.boost = boost;
	}

	public boolean isEager_global_ordinals() {
		return eager_global_ordinals;
	}

	public void setEager_global_ordinals(boolean eager_global_ordinals) {
		this.eager_global_ordinals = eager_global_ordinals;
	}

	public boolean isIndex() {
		return index;
	}

	public void setIndex(boolean index) {
		this.index = index;
	}

	public String getIndex_options() {
		return index_options;
	}

	public void setIndex_options(String index_options) {
		this.index_options = index_options;
	}

	public boolean isNorms() {
		return norms;
	}

	public void setNorms(boolean norms) {
		this.norms = norms;
	}

	public boolean isStore() {
		return store;
	}

	public void setStore(boolean store) {
		this.store = store;
	}
	
	public String getSimilarity() {
		return similarity;
	}

	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}

	public StringFields getStringFields() {
		return stringFields;
	}

	public void setStringFields(StringFields stringFields) {
		this.stringFields = stringFields;
	}

}
