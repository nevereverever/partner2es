package com.young.elasticsearch.repository.search.query.pojo.term.range;


import com.young.elasticsearch.repository.search.query.pojo.Query;
import com.young.elasticsearch.repository.search.query.pojo.QueryType;

/**
 * 范围查询对象
 * @author YoungLu
 * @date 2018-05-17
 */
public class Range extends Query {

	private static final long serialVersionUID = 1L;
	
	private String field;//字段名
	
	private String min;//最小值
	
	private String max;//最大值
	
	private boolean includeMin = false;//是否包含最小值
	
	private boolean includeMax = false;//是否包含最大值
	
	private String format;//日期需要使用format格式化时间 范例：yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis
	
	private String time_zone;//时区。用于转换为UTC格式，前提是日期以UTC存储
	
	private float boost = 1.0f;
	
	public Range(String field) {
		this.field = field;
		this.queryType = QueryType.RANGE;
	}
	
	@Override
	public void setQueryType(QueryType queryType) {
		this.queryType = QueryType.RANGE;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public boolean isIncludeMin() {
		return includeMin;
	}

	public void setIncludeMin(boolean includeMin) {
		this.includeMin = includeMin;
	}

	public boolean isIncludeMax() {
		return includeMax;
	}

	public void setIncludeMax(boolean includeMax) {
		this.includeMax = includeMax;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getTime_zone() {
		return time_zone;
	}

	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
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
