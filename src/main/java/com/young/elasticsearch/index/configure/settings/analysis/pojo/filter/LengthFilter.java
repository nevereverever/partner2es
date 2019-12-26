package com.young.elasticsearch.index.configure.settings.analysis.pojo.filter;

/**
 * 长度过滤器.用于删除流中太长或者太短的单词
 * @author YoungLu
 * @date 2018-05-03
 */
public class LengthFilter extends Filters{

	private static final long serialVersionUID = 1L;
	
	private int min = 0;//最小值

	private int max = Integer.MAX_VALUE;//最大值
	
	public LengthFilter() {
		this.type = FilterType.LENGTH;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
	@Override
	public void setType(FilterType type) {
	}
	
	
	
}
