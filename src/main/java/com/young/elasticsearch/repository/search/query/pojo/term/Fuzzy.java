package com.young.elasticsearch.repository.search.query.pojo.term;

import com.young.elasticsearch.repository.search.query.pojo.QueryType;
import org.elasticsearch.common.unit.Fuzziness;

/**
 * 模糊匹配对象
 * @author YoungLu
 * @date 2018-05-18
 */
public class Fuzzy extends Term {
	private static final long serialVersionUID = 1L;
	
	private Fuzziness fuzziness;//模糊性，默认为AUTO.
	
	private int prefix_length = 0;//不会被模糊化初始字符的数量
	
	private int max_expansions = 50;//模糊查询将扩展到的最大条数
	
	private boolean transpositions ;//是否模糊换位

	public Fuzzy(String field,Object value) {
		super(field, value);
		this.queryType = QueryType.FUZZY;
	}
	
	public Fuzziness getFuzziness() {
		return fuzziness;
	}

	public void setFuzziness(Fuzziness fuzziness) {
		this.fuzziness = fuzziness;
	}

	public int getPrefix_length() {
		return prefix_length;
	}

	public void setPrefix_length(int prefix_length) {
		this.prefix_length = prefix_length;
	}

	public int getMax_expansions() {
		return max_expansions;
	}

	public void setMax_expansions(int max_expansions) {
		this.max_expansions = max_expansions;
	}

	public boolean isTranspositions() {
		return transpositions;
	}

	public void setTranspositions(boolean transpositions) {
		this.transpositions = transpositions;
	}
	
	
	
}	
