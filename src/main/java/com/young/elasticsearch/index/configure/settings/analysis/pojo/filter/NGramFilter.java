package com.young.elasticsearch.index.configure.settings.analysis.pojo.filter;

/***
 * ngram过滤器
 * @author YoungLu
 * @date 2018-05-03
 */
public class NGramFilter extends Filters{

	private static final long serialVersionUID = 1L;
	
	private int min_gram = 1;
	
	private int max_gram = 2;
	
	
	public NGramFilter() {
		this.type = FilterType.NGRAM;
	}

	@Override
	public void setType(FilterType type) {
	}

	public int getMin_gram() {
		return min_gram;
	}

	public void setMin_gram(int min_gram) {
		this.min_gram = min_gram;
	}

	public int getMax_gram() {
		return max_gram;
	}

	public void setMax_gram(int max_gram) {
		this.max_gram = max_gram;
	}
	
}
