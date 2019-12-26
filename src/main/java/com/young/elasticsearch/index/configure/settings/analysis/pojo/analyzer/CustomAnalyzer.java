package com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer;

import com.young.elasticsearch.index.configure.settings.analysis.pojo.char_filter.CharacterFilters;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.filter.Filters;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer.Tokenizer;

/**
 * 自定义分词器
 * @author YoungLu
 * @date 2018-04-23
 */
public class CustomAnalyzer extends Analyzer {
	
	private static final long serialVersionUID = 1L;
	
	private CharacterFilters[] characterFilters;//字符过滤器
	
	private Tokenizer tokenizer;//分解器
	
	private Filters[] filter;//过滤器数组

	private int position_increment_gap = 100;//这次分词与下次分词词条的距离
			
	public CustomAnalyzer() {
		this.type = AnalyzerType.CUSTOM;
	}

	@Override
	public void setType(AnalyzerType type) {
	}

	public CharacterFilters[] getCharacterFilters() {
		return characterFilters;
	}

	public void setCharacterFilters(CharacterFilters[] characterFilters) {
		this.characterFilters = characterFilters;
	}

	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public Filters[] getFilter() {
		return filter;
	}

	public void setFilter(Filters[] filter) {
		this.filter = filter;
	}

	public int getPosition_increment_gap() {
		return position_increment_gap;
	}

	public void setPosition_increment_gap(int position_increment_gap) {
		this.position_increment_gap = position_increment_gap;
	}
	
	
	
}
