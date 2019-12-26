package com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.suggester;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.Datatypes;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer.Analyzer;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer.StandardAnalyzer;

/**
 * 搜索即用建议
 * @author YoungLu
 * @date 2018-05-16
 */
public class CompletionSuggester extends Suggester {

	private static final long serialVersionUID = 1L;
	
	private Analyzer analyzer = new StandardAnalyzer();//分析器。默认为简单分词器
	
	private Analyzer search_analyzer = this.analyzer;//查询分析器，默认与analyzer相同
	
	private boolean preserve_separators = true;//保留分隔符
	
	private boolean preserve_position_increments = true;//启用位置增量
	
	private long max_input_length = 50;//限制单个输入的长度

	public CompletionSuggester() {
		this.dataType = Datatypes.COMPLETION;
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

	public Analyzer getSearch_analyzer() {
		return search_analyzer;
	}

	public void setSearch_analyzer(Analyzer search_analyzer) {
		this.search_analyzer = search_analyzer;
	}

	public boolean isPreserve_separators() {
		return preserve_separators;
	}

	public void setPreserve_separators(boolean preserve_separators) {
		this.preserve_separators = preserve_separators;
	}

	public boolean isPreserve_position_increments() {
		return preserve_position_increments;
	}

	public void setPreserve_position_increments(boolean preserve_position_increments) {
		this.preserve_position_increments = preserve_position_increments;
	}

	public long getMax_input_length() {
		return max_input_length;
	}

	public void setMax_input_length(long max_input_length) {
		this.max_input_length = max_input_length;
	}
	
}
