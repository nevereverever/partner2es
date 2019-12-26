package com.young.elasticsearch.repository.search.query.pojo.fulltext;

import com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer.Analyzer;
import com.young.elasticsearch.repository.search.query.pojo.Query;
import com.young.elasticsearch.repository.search.query.pojo.QueryType;
import org.elasticsearch.index.query.Operator;

/**
 * Match类型查询,分析提供的文本,并根据提供的文本构建布尔查询
 * 只使用了部分成员变量表示部分配置
 * @author YoungLu
 * @date 2018-05-17
 */
public class Match extends Query {
	private static final long serialVersionUID = 1L;

	private String field;//参与查询的字段.对于多条件无需设置该参数
	
	private Object query;//参与查询的对象
	
	private Operator operator;//操作符，默认为or
	
	private String minimum_should_match;//最小匹配度,接受整型、百分比以及"2<-25% "形式
	
	private Analyzer analyzer;//使用何种分析器解析字符串
	
	private boolean lenient;//是否接受错误的类型

	private float boost = 1.0f;//权值
	
	public Match(String field,Object query) {
		this.field = field;
		this.query = query;
		this.queryType = QueryType.MATCH;
	}
	
	@Override
	public void setQueryType(QueryType queryType) {
		this.queryType = QueryType.MATCH;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getMinimum_should_match() {
		return minimum_should_match;
	}

	public void setMinimum_should_match(String minimum_should_match){
		this.minimum_should_match = minimum_should_match;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public boolean isLenient() {
		return lenient;
	}

	public void setLenient(boolean lenient) {
		this.lenient = lenient;
	}

	public Object getQuery() {
		return query;
	}

	public void setQuery(Object query) {
		this.query = query;
	}

	public float getBoost() {
		return boost;
	}

	public void setBoost(float boost) {
		this.boost = boost;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
}
