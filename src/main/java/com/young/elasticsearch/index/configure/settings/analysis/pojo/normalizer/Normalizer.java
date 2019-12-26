package com.young.elasticsearch.index.configure.settings.analysis.pojo.normalizer;

import com.young.elasticsearch.index.configure.settings.analysis.pojo.char_filter.CharacterFilters;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.filter.Filters;

import java.io.Serializable;


/***
 * 规范器。主要针对keyword类型的存储做一系列操作：比如小写化。BAR和bar存储时被认为是同一种
 * @author YoungLu
 * @date 2018-05-03
 */
public class Normalizer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;//规范器名称
	
	private final String normalizerType = "custom";//只支持自定义的规范器
	
	private CharacterFilters[] characterFilters;//字符过滤器
	
	private Filters[] filter;//过滤器数组

	public CharacterFilters[] getCharacterFilters() {
		return characterFilters;
	}

	public void setCharacterFilters(CharacterFilters[] characterFilters) {
		this.characterFilters = characterFilters;
	}

	public Filters[] getFilter() {
		return filter;
	}

	public void setFilter(Filters[] filter) {
		this.filter = filter;
	}

	public String getNormalizerType() {
		return normalizerType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
