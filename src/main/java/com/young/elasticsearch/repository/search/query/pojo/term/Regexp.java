package com.young.elasticsearch.repository.search.query.pojo.term;

import com.young.elasticsearch.repository.search.query.pojo.QueryType;
import org.elasticsearch.index.query.RegexpFlag;

/**
 * 正则表达式匹配对象
 * @author YoungLu
 * @date 2018-05-18
 */
public class Regexp extends Term {

	private static final long serialVersionUID = 1L;
	
	private RegexpFlag[] flags;//正则表达式标志
	
	private int max_determinized_states = 10000;//修改值变大可支持更复杂的正则表达式

	public Regexp(String field,Object value) {
		super(field, value);
		this.queryType = QueryType.REGEXP;
	}
	
	public RegexpFlag[] getFlags() {
		return flags;
	}

	public void setFlags(RegexpFlag... flags) {
		this.flags = flags;
	}

	public int getMax_determinized_states() {
		return max_determinized_states;
	}

	public void setMax_determinized_states(int max_determinized_states) {
		this.max_determinized_states = max_determinized_states;
	}
	
	
}	
