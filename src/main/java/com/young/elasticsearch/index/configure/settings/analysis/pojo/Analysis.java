package com.young.elasticsearch.index.configure.settings.analysis.pojo;

import com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer.Analyzer;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.char_filter.CharacterFilters;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.filter.Filters;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.normalizer.Normalizer;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.tokenizer.Tokenizer;

import java.io.Serializable;
import java.util.List;

/***************************
 * 分析器对象
 * @author YoungLu
 * @date 2018-05-25
 **************************/
public class Analysis implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<Analyzer> analyzerList;//分词器集合
	private List<Normalizer> normalizerList;//规范器集合
	private List<CharacterFilters> characterFiltersList;//字符过滤器集合
	private List<Filters> filterList;//过滤器集合
	private List<Tokenizer> tokenizerList;//令牌解析器集合
	
	public List<Analyzer> getAnalyzerList() {
		return analyzerList;
	}
	public void setAnalyzerList(List<Analyzer> analyzerList) {
		this.analyzerList = analyzerList;
	}
	public List<Normalizer> getNormalizerList() {
		return normalizerList;
	}
	public void setNormalizerList(List<Normalizer> normalizerList) {
		this.normalizerList = normalizerList;
	}
	public List<CharacterFilters> getCharacterFiltersList() {
		return characterFiltersList;
	}
	public void setCharacterFiltersList(List<CharacterFilters> characterFiltersList) {
		this.characterFiltersList = characterFiltersList;
	}
	public List<Tokenizer> getTokenizerList() {
		return tokenizerList;
	}
	public void setTokenizerList(List<Tokenizer> tokenizerList) {
		this.tokenizerList = tokenizerList;
	}
	public List<Filters> getFilterList() {
		return filterList;
	}
	public void setFilterList(List<Filters> filterList) {
		this.filterList = filterList;
	}
	
}
