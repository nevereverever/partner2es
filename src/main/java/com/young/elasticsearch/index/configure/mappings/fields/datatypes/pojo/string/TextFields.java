package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.string;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.Datatypes;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer.Analyzer;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer.StandardAnalyzer;

/**
 * 文本字段对象
 * @author YoungLu
 * @date 2018-05-03
 */
public class TextFields extends StringFields{
	
	private static final long serialVersionUID = 1L;
	
	private Analyzer analyzer = new StandardAnalyzer();//分析器。默认为标准分词器
	
	private boolean fielddata = false;//使用内存中的fielddata进行聚合、排序或者执行脚本
	
	private int position_increment_gap = 100;//两个被索引的词分解的令牌之间的间隔
	
	private Analyzer search_analyzer = this.analyzer;//查询时的分词器
	
	private Analyzer search_quote_analyzer = this.search_analyzer;//查询时遇到短语使用的分词器

	private String term_vector = TermVector.no.name();//是否为被分析的字段储存
	
	public TextFields() {
		this.dataType = Datatypes.TEXT;
		this.norms = true;
		this.index_options = IndexOptions.positions.name();
	}
	
	@Override
	public void setDataType(Datatypes dataType) {
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		//如果是同一个对象，那么修改为同一个分析器
		if ((this.search_analyzer) == (this.analyzer)) {
			setSearch_analyzer(analyzer);//修改查询分析器为同一个
		}
		this.analyzer = analyzer;
	}
	
	public boolean isFielddata() {
		return fielddata;
	}

	public void setFielddata(boolean fielddata) {
		this.fielddata = fielddata;
	}
	
	public int getPosition_increment_gap() {
		return position_increment_gap;
	}

	public void setPosition_increment_gap(int position_increment_gap) {
		this.position_increment_gap = position_increment_gap;
	}
	
	public Analyzer getSearch_analyzer() {
		return search_analyzer;
	}

	public void setSearch_analyzer(Analyzer search_analyzer) {
		if (this.search_analyzer == this.search_quote_analyzer) {
			setSearch_quote_analyzer(search_analyzer);
		}
		this.search_analyzer = search_analyzer;
	}

	public Analyzer getSearch_quote_analyzer() {
		return search_quote_analyzer;
	}

	public void setSearch_quote_analyzer(Analyzer search_quote_analyzer) {
		this.search_quote_analyzer = search_quote_analyzer;
	}
	
	public String getTerm_vector() {
		return term_vector;
	}

	public void setTerm_vector(String term_vector) {
		this.term_vector = term_vector;
	}
	
}
