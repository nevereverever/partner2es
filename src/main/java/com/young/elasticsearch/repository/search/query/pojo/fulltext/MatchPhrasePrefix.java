package com.young.elasticsearch.repository.search.query.pojo.fulltext;

import com.young.elasticsearch.repository.search.query.pojo.QueryType;

/**
 * 前缀短语匹配
 * @author YoungLu
 * @date 2018-05-17
 */
public class MatchPhrasePrefix extends MatchPhrase{

	private static final long serialVersionUID = 1L;
	
	private int max_expansions = 50;//查询的最后一个单词允许匹配的最大大小
	
	public MatchPhrasePrefix(String field,Object query) {
		super(field, query);
		this.queryType = QueryType.MATCH_PHRASE_PREFIX;
	}

	@Override
	public void setQueryType(QueryType queryType) {
		this.queryType = QueryType.MATCH_PHRASE_PREFIX;
	}

	public int getMax_expansions() {
		return max_expansions;
	}

	public void setMax_expansions(int max_expansions) {
		this.max_expansions = max_expansions;
	}
	
}
