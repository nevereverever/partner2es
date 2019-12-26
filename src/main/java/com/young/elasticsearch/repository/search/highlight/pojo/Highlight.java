package com.young.elasticsearch.repository.search.highlight.pojo;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.io.stream.Writeable;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.BoundaryScannerType;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Order;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

/**
 * 高亮对象
 * @author YoungLu
 * @date 2018-05-22
 */
public class Highlight implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private char[] boundaryChars;//一个包含边界字符的字符数组
	
	private int boundaryMaxScan = 20;//扫描边界字符的距离
	
	private BoundaryScannerType boundaryScannerType;//指定如何拆分高亮片段
	
	private String boundaryScannerLocale;//指定用于搜索句子和单词边界的语言环境
	
	private Encoder encoder;//是否是html编码
	
	private Field[] fields;//需要高亮的字段
	
	private boolean forceSource = false;//即使该字段单独存储，也会根据来源突出显示
	
	private FragmenterType fragmenter;//指定如何在高亮片段中分解文本
	
	private int fragmentSize = 100;//突出显示的片段的大小
	
	private QueryBuilder highlightQuery;//高亮查询。此查询覆盖query中的查询
	
	private int noMatchSize = 0;//如果没有匹配的片段需要高亮显示，从字段的开头返回的文本数量,0表示什么都不返回
	
	private int numOfFragments = 5;//要返回的最大片段数
	
	private Order order;//排序方式。设置为score时，按高分对高亮片段进行排序
	
	private int phraseLimit = 256;//控制一个被考虑的文档中匹配的短语大小
	
	private String[] preTags = {"<b>"};//与post_tags一起使用来定义用于突出显示的文本的HTML标记
	
	private String[] postTags= {"</b>"};//同上
	
	private boolean requireFieldMatch = true;//默认情况下，只有包含查询匹配的字段才会突出显示
	
	private String tagsSchema;//styled设置前置符号为<em>后置为</em>
	
	private HighlightType highlighterType;//高亮的类型
	
	private boolean highlightFilter = false;//高亮过滤
	
	public enum Encoder implements Writeable {
        SIMPLE, SPAN;

        public static Encoder readFromStream(StreamInput in) throws IOException {
            return in.readEnum(Encoder.class);
        }

        @Override
        public void writeTo(StreamOutput out) throws IOException {
            out.writeEnum(this);
        }

        public static Encoder fromString(String encoder) {
            return valueOf(encoder.toUpperCase(Locale.ROOT));
        }

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
	
	public enum FragmenterType implements Writeable {
        SIMPLE, SPAN;

        public static FragmenterType readFromStream(StreamInput in) throws IOException {
            return in.readEnum(FragmenterType.class);
        }

        @Override
        public void writeTo(StreamOutput out) throws IOException {
            out.writeEnum(this);
        }

        public static FragmenterType fromString(String fragmenterType) {
            return valueOf(fragmenterType.toUpperCase(Locale.ROOT));
        }

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
	
	public enum HighlightType implements Writeable {
		UNIFIED, PLAIN,FVH;

        public static HighlightType readFromStream(StreamInput in) throws IOException {
            return in.readEnum(HighlightType.class);
        }

        @Override
        public void writeTo(StreamOutput out) throws IOException {
            out.writeEnum(this);
        }

        public static HighlightType fromString(String highlightType) {
            return valueOf(highlightType.toUpperCase(Locale.ROOT));
        }

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

	public char[] getBoundaryChars() {
		return boundaryChars;
	}

	public void setBoundaryChars(char[] boundaryChars) {
		this.boundaryChars = boundaryChars;
	}

	public int getBoundaryMaxScan() {
		return boundaryMaxScan;
	}

	public void setBoundaryMaxScan(int boundaryMaxScan) {
		this.boundaryMaxScan = boundaryMaxScan;
	}

	public BoundaryScannerType getBoundaryScannerType() {
		return boundaryScannerType;
	}

	public void setBoundaryScannerType(BoundaryScannerType boundaryScannerType) {
		this.boundaryScannerType = boundaryScannerType;
	}

	public String getBoundaryScannerLocale() {
		return boundaryScannerLocale;
	}

	public void setBoundaryScannerLocale(String boundaryScannerLocale) {
		this.boundaryScannerLocale = boundaryScannerLocale;
	}

	public Encoder getEncoder() {
		return encoder;
	}

	public void setEncoder(Encoder encoder) {
		this.encoder = encoder;
	}

	public Field[] getFields() {
		return fields;
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	public boolean isForceSource() {
		return forceSource;
	}

	public void setForceSource(boolean forceSource) {
		this.forceSource = forceSource;
	}

	public FragmenterType getFragmenter() {
		return fragmenter;
	}

	public void setFragmenter(FragmenterType fragmenter) {
		this.fragmenter = fragmenter;
	}

	public int getFragmentSize() {
		return fragmentSize;
	}

	public void setFragmentSize(int fragmentSize) {
		this.fragmentSize = fragmentSize;
	}

	public QueryBuilder getHighlightQuery() {
		return highlightQuery;
	}

	public void setHighlightQuery(QueryBuilder highlightQuery) {
		this.highlightQuery = highlightQuery;
	}

	public int getNoMatchSize() {
		return noMatchSize;
	}

	public void setNoMatchSize(int noMatchSize) {
		this.noMatchSize = noMatchSize;
	}

	public int getNumOfFragments() {
		return numOfFragments;
	}

	public void setNumOfFragments(int numOfFragments) {
		this.numOfFragments = numOfFragments;
	}

	public int getPhraseLimit() {
		return phraseLimit;
	}

	public void setPhraseLimit(int phraseLimit) {
		this.phraseLimit = phraseLimit;
	}

	public String[] getPreTags() {
		return preTags;
	}

	public void setPreTags(String... preTags) {
		this.preTags = preTags;
	}

	public String[] getPostTags() {
		return postTags;
	}

	public void setPostTags(String... postTags) {
		this.postTags = postTags;
	}

	public boolean isRequireFieldMatch() {
		return requireFieldMatch;
	}

	public void setRequireFieldMatch(boolean requireFieldMatch) {
		this.requireFieldMatch = requireFieldMatch;
	}

	public String getTagsSchema() {
		return tagsSchema;
	}

	public void setTagsSchema(String tagsSchema) {
		this.tagsSchema = tagsSchema;
	}

	public HighlightType getHighlighterType() {
		return highlighterType;
	}

	public void setHighlighterType(HighlightType highlighterType) {
		this.highlighterType = highlighterType;
	}

	public boolean isHighlightFilter() {
		return highlightFilter;
	}

	public void setHighlightFilter(boolean highlightFilter) {
		this.highlightFilter = highlightFilter;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
