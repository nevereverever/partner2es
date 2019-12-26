package com.young.elasticsearch.repository.search.highlight;

import com.young.elasticsearch.repository.search.highlight.pojo.Highlight;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.BoundaryScannerType;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Order;

/**
 * 高亮builder
 * @author YoungLu
 * @date 2018-05-22
 */
public class EsHightlightBuilder {
	
	/**
	 * 构造高亮builder
	 * @param highlight 高亮对象
	 * @return 高亮builder对象
	 */
	public static HighlightBuilder highlightBuilder(Highlight highlight) {
		//highlight对象为空,直接返回null
		if (highlight == null) {
			return null;
		}
		//新建HighlightBuilder对象
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		
		//边界字符
		char[] boundaryChars = highlight.getBoundaryChars();
		if (boundaryChars != null) {
			highlightBuilder.boundaryChars(boundaryChars);
		}
		//扫描边界字符的距离
		int boundaryMaxScan = highlight.getBoundaryMaxScan();
		if (boundaryMaxScan != 20) {
			highlightBuilder.boundaryMaxScan(boundaryMaxScan);
		}
		//边界扫描的类型
		BoundaryScannerType boundaryScannerType = highlight.getBoundaryScannerType();
		if (boundaryScannerType != null) {
			highlightBuilder.boundaryScannerType(boundaryScannerType);
		}
		//指定用于搜索句子和单词边界的语言环境
		String boundaryScannerLocale = highlight.getBoundaryScannerLocale();
		if (boundaryScannerLocale != null) {
			highlightBuilder.boundaryScannerLocale(boundaryScannerLocale);
		}
		//html编码
		Highlight.Encoder encoder = highlight.getEncoder();
		if (encoder != null) {
			highlightBuilder.encoder(encoder.toString());
		}
		//高亮的字段
		Field[] field = highlight.getFields();
		if (field != null) {
			for (int i = 0; i < field.length; i++) {
				highlightBuilder.field(field[i]);
			}
		}
		//单独存储强显示
		boolean forceSource = highlight.isForceSource();
		if (forceSource != false) {
			highlightBuilder.forceSource(forceSource);
		}
		//指定如何在高亮片段中分解文本
		Highlight.FragmenterType fragmenter=highlight.getFragmenter();
		if (forceSource) {
			highlightBuilder.fragmenter(fragmenter.toString());
		}
		//突出显示的片段的大小
		int fragmentSize = highlight.getFragmentSize();
		if (fragmentSize != 100) {
			highlightBuilder.fragmentSize(fragmentSize);
		}
		//高亮查询
		QueryBuilder highlightQuery = highlight.getHighlightQuery();
		if (highlightQuery != null) {
			highlightBuilder.highlightQuery(highlightQuery);
		}
		//如果没有匹配的片段需要高亮显示，从字段的开头返回的文本数量
		int noMatchSize = highlight.getNoMatchSize();
		if (noMatchSize != 0) {
			highlightBuilder.noMatchSize(noMatchSize);
		}
		//要返回的最大片段数
		int numOfFragments = highlight.getNumOfFragments();
		if (numOfFragments != 5) {
			highlightBuilder.numOfFragments(numOfFragments);
		}
		//排序方式
		Order order = highlight.getOrder();
		if (order != null) {
			highlightBuilder.order(order);
		}
		//控制一个被考虑的文档中匹配的短语大小
		int phraseLimit = highlight.getPhraseLimit();
		if (phraseLimit != 256) {
			highlightBuilder.phraseLimit(phraseLimit);
		}
		//前置标签
		String[] preTags = highlight.getPreTags();
		if (preTags != null) {
			highlightBuilder.preTags(preTags);
		}
		//后置标签
		String[] postTags = highlight.getPostTags();
		if (postTags != null) {
			highlightBuilder.postTags(postTags);
		}
		//是否只有指定的列高亮显示
		boolean requireFieldMatch = highlight.isRequireFieldMatch();
		if (!requireFieldMatch) {
			highlightBuilder.requireFieldMatch(requireFieldMatch);
		}
		//标签模式
		String schemaName = highlight.getTagsSchema();
		if (schemaName != null) {
			highlightBuilder.tagsSchema(schemaName);
		}
		//高亮的类型
		Highlight.HighlightType highlighterType = highlight.getHighlighterType();
		if (highlighterType != null) {
			highlightBuilder.highlighterType(highlighterType.toString());
		}
		//高亮过滤
		boolean highlightFilter = highlight.isHighlightFilter();
		if (highlightFilter != false) {
			highlightBuilder.highlightFilter(highlightFilter);
		}
		return highlightBuilder;
	}
}
