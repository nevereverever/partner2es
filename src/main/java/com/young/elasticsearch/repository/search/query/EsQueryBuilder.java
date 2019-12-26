package com.young.elasticsearch.repository.search.query;

import com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer.Analyzer;
import com.young.elasticsearch.repository.search.query.pojo.Filter;
import com.young.elasticsearch.repository.search.query.pojo.Query;
import com.young.elasticsearch.repository.search.query.pojo.QueryType;
import com.young.elasticsearch.repository.search.query.pojo.compound.ConstantScore;
import com.young.elasticsearch.repository.search.query.pojo.compound.bool.Bool;
import com.young.elasticsearch.repository.search.query.pojo.compound.bool.Must;
import com.young.elasticsearch.repository.search.query.pojo.compound.bool.MustNot;
import com.young.elasticsearch.repository.search.query.pojo.compound.bool.Should;
import com.young.elasticsearch.repository.search.query.pojo.fulltext.*;
import com.young.elasticsearch.repository.search.query.pojo.term.*;
import com.young.elasticsearch.repository.search.query.pojo.term.range.Range;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Map;
import java.util.Map.Entry;

public class EsQueryBuilder {
	
	/**
	 * 构建排序查询builder
	 * @param order 正序，倒序，
	 * @return
	 */
	public static SortBuilder<?> sortBuilder(String fieldSort,SortOrder order){
		return SortBuilders.fieldSort(fieldSort).order(order);
	}
	
	
	/**
	 * 构建含ik和ngram的bool查询builder
	 * @param ikFields
	 * @param ngFields
	 * @return
	 */
	public static BoolQueryBuilder boolQueryBuilder(Map<String,Object> ikFields,Map<String,Object> ngFields){
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		// 加入ik检索
		if (ikFields != null && ikFields.size() != 0) {
			for (Entry<String, Object> entry : ikFields.entrySet()) {
				if (entry.getValue() != null && !"".equals(entry.getValue())) {
					queryBuilder.must(
							QueryBuilders.matchPhraseQuery(entry.getKey(), entry.getValue()).analyzer("ik_max_word"));
				}
			}
		}

		// 加入ng检索
		if (ngFields != null && ngFields.size() != 0) {
			for (Entry<String, Object> entry : ngFields.entrySet()) {
				if (entry.getValue() != null && !"".equals(entry.getValue())) {
					queryBuilder.must(QueryBuilders.matchPhrasePrefixQuery(entry.getKey(), entry.getValue())
							.analyzer("ngram").maxExpansions(1).slop(0));
				}
			}
		}
		
		return queryBuilder;
	}
	
	/**
	 * 得到过滤条件builder
	 * @param multiConMap 多条件map
	 * @param rangeField 范围字段
	 * @param minAndMax 范围字段的最小值和最大值。例如{"null,20170823000000","20170830000000,20170918000000"}
	 * @param includeMin 是否包含最小值，与minAndMax对应： {true,false} 
	 * @param includeMax 是否包含最大值，与minAndMax对应：{true,true}
	 * @return
	 */
	public static BoolQueryBuilder boolQueryBuilderFilter(Map<String,Object> multiConMap,String[] rangeField,String minAndMax[], boolean includeMin[], boolean includeMax[]){
		BoolQueryBuilder boolQueryBuilderFilter = QueryBuilders.boolQuery();

		// 加入多条件查询
		if (multiConMap != null) {
			for (Entry<String, Object> entry : multiConMap.entrySet()) {
				// 关键字不能为空，否则抛出value cannot be null异常
				if (entry.getValue() != null && !"".equals(entry.getValue())) {
					boolQueryBuilderFilter.must(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
				}
			}
		}

		if (rangeField != null && minAndMax != null) {
			for (int i = 0; i < minAndMax.length; i++) {
				String[] minMax = minAndMax[i].split(",");
				String min = minMax[0];
				String max = minMax[1];
				RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(rangeField[i]);
				if (!min.equals("null")) {
					// 若min中包含"."，表示是double型
					if (min.contains(".")) {
						rangeQueryBuilder.from(Double.parseDouble(min)).includeLower(includeMin[i]);
					} else {
						// 否则为字符型
						rangeQueryBuilder.from(min).includeLower(includeMin[i]);
					}
				}
				if (!max.equals("null")) {
					// 若max中包含"."，表示是double型
					if (max.contains(".")) {
						rangeQueryBuilder.to(Double.parseDouble(max)).includeUpper(includeMax[i]);
					} else {
						// 否则为字符型
						rangeQueryBuilder.to(max).includeUpper(includeMax[i]);
					}
				}
				boolQueryBuilderFilter.must(rangeQueryBuilder);

			}
		}
		
		return boolQueryBuilderFilter;
	}
	
	/**
	 * 构建查询builder对象
	 * @return querybuilder
	 */
	public static QueryBuilder queryBuilder(Query query) {
		if (query == null) {
			return QueryBuilders.matchAllQuery();
		}
		
		QueryBuilder queryBuilder = null;
		
		/***
		 * 首先,获取查询类型.
		 */
		QueryType queryType = query.getQueryType();
		
		/**************************
		 * 1.术语查询类型(精准匹配)
		 **************************/
		if (query instanceof Term) {
			//普通术语
			if (QueryType.TERM.equals(queryType)) {
				Term term = (Term) query;
				//构建普通术语查询
				queryBuilder = QueryBuilders.termQuery(term.getField(), term.getValue());
			//前缀查询	
			} else if (QueryType.PREFIX.equals(queryType)) {
				Prefix prefix = (Prefix) query;
				//构建前缀查询
				queryBuilder = QueryBuilders.prefixQuery(prefix.getField(), (String)prefix.getValue());
			//通配符查询	
			} else if (QueryType.WILDCARD.equals(queryType)) {
				Wildcard wildcard = (Wildcard) query;
				//权重
				float boost = wildcard.getBoost();
				WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery(wildcard.getField(), (String)wildcard.getValue());
				if (boost != 1.0f) {
					wildcardQueryBuilder = wildcardQueryBuilder.boost(boost);
				}
				queryBuilder = wildcardQueryBuilder;
				//模糊查询
			} else if (QueryType.FUZZY.equals(queryType)) {
				Fuzzy fuzzy = (Fuzzy) query;
				float boost = fuzzy.getBoost();//权重
				Fuzziness fuzziness = fuzzy.getFuzziness();//模糊级别
				int maxExpansions = fuzzy.getMax_expansions();//最大范围
				boolean transpositions = fuzzy.isTranspositions();
				FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue());
				if (boost != 1.0f) {
					fuzzyQueryBuilder = fuzzyQueryBuilder.boost(boost);
				}
				//模糊级别不为空，加入到搜索条件
				if (fuzziness != null) {
					fuzzyQueryBuilder = fuzzyQueryBuilder.fuzziness(fuzziness);
				}
				//模糊拓展的词根不为50，加入到搜索条件
				if (maxExpansions != 50) {
					fuzzyQueryBuilder = fuzzyQueryBuilder.maxExpansions(maxExpansions);
				}
				if (transpositions != false) {
					fuzzyQueryBuilder = fuzzyQueryBuilder.transpositions(transpositions);
				}
				
				queryBuilder = fuzzyQueryBuilder;
			//正则表达式查询
			} else if (QueryType.REGEXP.equals(queryType)) {
				Regexp regexp = (Regexp) query;
				RegexpQueryBuilder regexpQueryBuilder = QueryBuilders.regexpQuery(regexp.getField(), (String)regexp.getValue());
				float boost = regexp.getBoost();//权值
				int maxDeterminizedStates = regexp.getMax_determinized_states();
				RegexpFlag[] flags = regexp.getFlags();//正则表达式标志
				
				if (boost != 1.0f) {
					regexpQueryBuilder = regexpQueryBuilder.boost(boost);
				}
				if (maxDeterminizedStates != 10000) {
					regexpQueryBuilder = regexpQueryBuilder.maxDeterminizedStates(maxDeterminizedStates);
				}
				if (flags != null) {
					regexpQueryBuilder = regexpQueryBuilder.flags(flags);
				}
				queryBuilder =  regexpQueryBuilder;
			}
		/*****************************
		 * 2.范围查询类型
		 ****************************/
		}else if (QueryType.RANGE.equals(queryType)) {
			Range range = (Range) query;
			String min = range.getMin();//最小值
			String max = range.getMax();//最大值
			float boost = range.getBoost();//权重
			String format = range.getFormat();//格式化(一般只有日期才有格式)
			
			RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(range.getField());
			if (min != null) {
				rangeQueryBuilder = rangeQueryBuilder.from(min, range.isIncludeMin());
			}
			if (max != null) {
				rangeQueryBuilder = rangeQueryBuilder.to(max, range.isIncludeMax());
			}
			if (boost != 1.0f) {
				rangeQueryBuilder = rangeQueryBuilder.boost(boost);
			}
			//时间类型有format字段，不为空则定义
			if (format != null) {
				rangeQueryBuilder = rangeQueryBuilder.format(format);
			}
			queryBuilder = rangeQueryBuilder;
		/*************************************************
		 * 3.多术语匹配查询(字段满足多个查询条件中的任意一个)
		 **************************************************/
		}else if(query instanceof Terms){
			Terms terms = (Terms) query;
			queryBuilder = QueryBuilders.termsQuery(terms.getField(), terms.getValue());
		/************************************
		 * 	4.全文检索
		 ************************************/
		}else if(query instanceof Match){
			//用于执行全文查询的标准查询，包括模糊匹配和短语或邻近查询。
			if (QueryType.MATCH.equals(queryType)) {
				Match match = (Match) query;
				Analyzer analyzer = match.getAnalyzer();//分词器
				float boost = match.getBoost();//权重
				String minimum_should_match = match.getMinimum_should_match();//最小匹配数
				Operator operator = match.getOperator();//操作符,是and还是or?
				boolean lenient = match.isLenient();//是否接收错误的类型
				MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(match.getField(), match.getQuery());
				if (boost != 1.0f) {
					matchQueryBuilder = matchQueryBuilder.boost(boost);
				}
				if (analyzer != null) {
					matchQueryBuilder = matchQueryBuilder.analyzer(analyzer.getName());
				}
				if (minimum_should_match != null) {
					matchQueryBuilder = matchQueryBuilder.minimumShouldMatch(minimum_should_match);
				}
				if (operator != null) {
					matchQueryBuilder = matchQueryBuilder.operator(operator);
				}
				if (lenient != false) {
					matchQueryBuilder = matchQueryBuilder.lenient(lenient);
				}
				queryBuilder = matchQueryBuilder;
			//匹配查询的多字段版本(一个查询条件,在多个字段检索)	
			}else if (QueryType.MULTI_MATCH.equals(queryType)) {
				MultiMatch multiMatch = (MultiMatch) query;
				//未给出多个字段的情况下,检查下field字段是否为空,如果为空直接返回null
				if (multiMatch.getFieldNames().length == 0) {
					if (multiMatch.getField() != null) {
						String[] fieldNames = {multiMatch.getField()};
						multiMatch.setFieldNames(fieldNames);
					}else {
						return null;
					}
				}
				
				float boost = multiMatch.getBoost();//权值
				Analyzer analyzer = multiMatch.getAnalyzer();//分词器
				String minimumShouldMatch = multiMatch.getMinimum_should_match();//最小匹配大小
				Operator operator = multiMatch.getOperator();//操作符是and还是or?
				Type type = multiMatch.getType();//类型
				boolean lenient = multiMatch.isLenient();//是否接受错误的类型
				
				MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(multiMatch.getQuery(), multiMatch.getFieldNames());
				if (analyzer != null) {
					multiMatchQueryBuilder = multiMatchQueryBuilder.analyzer(analyzer.getName());
				}
				if (boost != 1.0f) {
					multiMatchQueryBuilder = multiMatchQueryBuilder.boost(boost);
				}
				if (operator != null) {
					multiMatchQueryBuilder = multiMatchQueryBuilder.operator(operator);
				}
				if (minimumShouldMatch != null) {
					multiMatchQueryBuilder = multiMatchQueryBuilder.minimumShouldMatch(minimumShouldMatch);
				}
				if (type != null) {
					multiMatchQueryBuilder = multiMatchQueryBuilder.type(type);
				}
				if (lenient != false) {
					multiMatchQueryBuilder = multiMatchQueryBuilder.lenient(lenient);
				}
				queryBuilder = multiMatchQueryBuilder;
			}
			
		/****************************************************
		 * 4.match_phrase查询分析文本并从分析的文本中创建短语查询
		 *****************************************************/
		}else if (query instanceof MatchPhrase) {
			if (QueryType.MATCH_PHRASE.equals(queryType)) {
				MatchPhrase matchPhrase = (MatchPhrase) query;
				Analyzer analyzer = matchPhrase.getAnalyzer();//分词器
				int slop = matchPhrase.getSlop();//词条间隔大小
				float boost = matchPhrase.getBoost();//权值
				MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery(matchPhrase.getField(), matchPhrase.getQuery());
				if (analyzer != null) {
					matchPhraseQueryBuilder = matchPhraseQueryBuilder.analyzer(analyzer.getName());
				}
				if (boost != 1.0f) {
					matchPhraseQueryBuilder = matchPhraseQueryBuilder.boost(boost);
				}
				if (slop != 0) {
					matchPhraseQueryBuilder = matchPhraseQueryBuilder.slop(slop);
				}
				queryBuilder = matchPhraseQueryBuilder;
			}else if (QueryType.MATCH_PHRASE_PREFIX.equals(queryType)) {
				MatchPhrasePrefix matchPhrasePrefix = (MatchPhrasePrefix) query;
				Analyzer analyzer  = matchPhrasePrefix.getAnalyzer();//分词器
				float boost = matchPhrasePrefix.getBoost();//权值
				int maxExpansions = matchPhrasePrefix.getMax_expansions();//控制最后一个术语将要扩展的后缀数量。
				int slop = matchPhrasePrefix.getSlop();//词条间隔大小
				MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder = QueryBuilders.matchPhrasePrefixQuery(matchPhrasePrefix.getField(),matchPhrasePrefix.getQuery());
				if (analyzer != null) {
					matchPhrasePrefixQueryBuilder = matchPhrasePrefixQueryBuilder.analyzer(analyzer.getName());
				}
				if (boost != 1.0) {
					matchPhrasePrefixQueryBuilder = matchPhrasePrefixQueryBuilder.boost(boost);
				}
				if (slop != 0) {
					matchPhrasePrefixQueryBuilder = matchPhrasePrefixQueryBuilder.slop(slop);
				}
				if (maxExpansions != 50) {
					matchPhrasePrefixQueryBuilder = matchPhrasePrefixQueryBuilder.maxExpansions(maxExpansions);
				}
				queryBuilder = matchPhrasePrefixQueryBuilder;
			}
		/****************************************
		 * 5.查询全部
		 **************************************/
		}else if (query instanceof MatchAll) {
			MatchAll matchAll = (MatchAll) query;
			queryBuilder = QueryBuilders.matchAllQuery();
			float boost = matchAll.getBoost();
			if (boost != 1.0) {
				queryBuilder = queryBuilder.boost(boost);
			}
		}
		/********************************
		 * bool复合查询
		 **********************************/
		else if (query instanceof Bool) {
			Bool bool = (Bool) query;
			Must must =  bool.getMust();//必须满足的条件
			Should should = bool.getShould();//可以满足的条件
			MustNot mustNot = bool.getMust_not();//一定不能满足的条件
			Filter filter = bool.getFilter();//过滤条件
			float boost = bool.getBoost();//权值
			String minimumShouldMatch = bool.getMinimum_should_match();//最小should匹配数目
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			if (boost != 1.0f) {
				boolQueryBuilder.boost(boost);
			}
			if (minimumShouldMatch != null) {
				boolQueryBuilder = boolQueryBuilder.minimumShouldMatch(minimumShouldMatch);
			}
			//遍历复合条件，构建子查询
			if (must != null) {
				for (Query mustQuery : must.getQuery() ) {
					QueryBuilder mustQueryBuilder = queryBuilder(mustQuery);

					//添加子查询到父查询
					if (mustQueryBuilder != null) {
						boolQueryBuilder = boolQueryBuilder.must(mustQueryBuilder);
					}
				}
			}
			if (should != null) {
				for (Query shouldQuery : should.getQuery()) {
					QueryBuilder shouldQueryBuilder = queryBuilder(shouldQuery);
					if (shouldQueryBuilder != null) {
						boolQueryBuilder = boolQueryBuilder.should(shouldQueryBuilder);
					}
					
				}
			}
			if (mustNot != null) {
				for (Query mustNotQuery : mustNot.getQuery()) {
					QueryBuilder mustNotQueryBuilder = queryBuilder(mustNotQuery);
					if (mustNotQueryBuilder != null) {
						boolQueryBuilder = boolQueryBuilder.mustNot(mustNotQueryBuilder);
					}
				}
			}
			if (filter != null) {
				for (Query filterQuery : filter.getQuery()) {
					QueryBuilder filterBuilder = queryBuilder(filterQuery);
					if (filterBuilder != null) {
						boolQueryBuilder = boolQueryBuilder.filter(filterBuilder);
					}
				}
			}
			queryBuilder = boolQueryBuilder;
		}else if (query instanceof ConstantScore) {
			ConstantScore constantScore = (ConstantScore) query;
			Filter filter = constantScore.getFilter();//过滤条件
			float boost = constantScore.getBoost();//权值
			QueryBuilder filterBuilder = null;
			for (Query filterQuery : filter.getQuery()) {
				filterBuilder = queryBuilder(filterQuery);
			}
			ConstantScoreQueryBuilder constantScoreQueryBuilder = null;
			if (filterBuilder != null) {
				constantScoreQueryBuilder = QueryBuilders.constantScoreQuery(filterBuilder);
				if (boost != 1.0f) {
					constantScoreQueryBuilder = constantScoreQueryBuilder.boost(boost);
				}
			}
			queryBuilder = constantScoreQueryBuilder;
		}
		return queryBuilder;
	}
	
}
