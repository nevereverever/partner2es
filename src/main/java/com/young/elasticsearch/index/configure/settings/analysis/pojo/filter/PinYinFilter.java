package com.young.elasticsearch.index.configure.settings.analysis.pojo.filter;

public class PinYinFilter extends Filters{
	
	private static final long serialVersionUID = 1L;
	
	private boolean keep_first_letter = true;//是否将词分词为首拼，例如刘德华：ldh
	
	private boolean keep_separate_first_letter = false;//是否将词分开，例如刘德华：l，d，h
	
	private int limit_first_letter_length = 16; //首拼字符串最大长度
	
	private boolean keep_full_pinyin = true; //设置为true时，刘德华: [liu,de,hua]
	
	private boolean keep_joined_full_pinyin = false;//设置是否保存连拼，刘德华：liudehua
	
	private boolean keep_none_chinese = true;//设置是否将字母或者数字保存
	
	private boolean keep_none_chinese_together = true; //设置是否将非中文字符放一起，例如DJ音乐家（true）：[DJ,yin,yue,jia];DJ音乐家（false）：[D,J,yin,yue,jia]
	
	private boolean keep_none_chinese_in_first_letter = true;//刘德华 in hometown(true) :ldhinhometown ,刘德华 in hometown(false) :刘德华 in hometown 
	
	private boolean keep_none_chinese_in_joined_full_pinyin = false;//让非中文加入满拼
	
	private boolean none_chinese_pinyin_tokenize = true;//本就是拼音的字符拆分为单个拼音
	
	private boolean keep_original = false;//保留原本输入的字符串
	
	private boolean lowercase = true;//将非中文字符转换为小写
	
	private boolean trim_whitespace = true; //去空格
	
	private boolean remove_duplicated_term = false; //删除相同的分词，例如的de（true）:de
	
	private boolean ignore_pinyin_offset = true;//忽略拼音偏移量

	public PinYinFilter () {
		this.type = FilterType.PINYIN;
	}
	
	public boolean isKeep_first_letter() {
		return keep_first_letter;
	}

	public void setKeep_first_letter(boolean keep_first_letter) {
		this.keep_first_letter = keep_first_letter;
	}

	public boolean isKeep_separate_first_letter() {
		return keep_separate_first_letter;
	}

	public void setKeep_separate_first_letter(boolean keep_separate_first_letter) {
		this.keep_separate_first_letter = keep_separate_first_letter;
	}

	public int getLimit_first_letter_length() {
		return limit_first_letter_length;
	}

	public void setLimit_first_letter_length(int limit_first_letter_length) {
		this.limit_first_letter_length = limit_first_letter_length;
	}

	public boolean isKeep_full_pinyin() {
		return keep_full_pinyin;
	}

	public void setKeep_full_pinyin(boolean keep_full_pinyin) {
		this.keep_full_pinyin = keep_full_pinyin;
	}

	public boolean isKeep_joined_full_pinyin() {
		return keep_joined_full_pinyin;
	}

	public void setKeep_joined_full_pinyin(boolean keep_joined_full_pinyin) {
		this.keep_joined_full_pinyin = keep_joined_full_pinyin;
	}

	public boolean isKeep_none_chinese() {
		return keep_none_chinese;
	}

	public void setKeep_none_chinese(boolean keep_none_chinese) {
		this.keep_none_chinese = keep_none_chinese;
	}

	public boolean isKeep_none_chinese_together() {
		return keep_none_chinese_together;
	}

	public void setKeep_none_chinese_together(boolean keep_none_chinese_together) {
		this.keep_none_chinese_together = keep_none_chinese_together;
	}

	public boolean isKeep_none_chinese_in_first_letter() {
		return keep_none_chinese_in_first_letter;
	}

	public void setKeep_none_chinese_in_first_letter(boolean keep_none_chinese_in_first_letter) {
		this.keep_none_chinese_in_first_letter = keep_none_chinese_in_first_letter;
	}

	public boolean isKeep_none_chinese_in_joined_full_pinyin() {
		return keep_none_chinese_in_joined_full_pinyin;
	}

	public void setKeep_none_chinese_in_joined_full_pinyin(boolean keep_none_chinese_in_joined_full_pinyin) {
		this.keep_none_chinese_in_joined_full_pinyin = keep_none_chinese_in_joined_full_pinyin;
	}

	public boolean isNone_chinese_pinyin_tokenize() {
		return none_chinese_pinyin_tokenize;
	}

	public void setNone_chinese_pinyin_tokenize(boolean none_chinese_pinyin_tokenize) {
		this.none_chinese_pinyin_tokenize = none_chinese_pinyin_tokenize;
	}

	public boolean isKeep_original() {
		return keep_original;
	}

	public void setKeep_original(boolean keep_original) {
		this.keep_original = keep_original;
	}

	public boolean isLowercase() {
		return lowercase;
	}

	public void setLowercase(boolean lowercase) {
		this.lowercase = lowercase;
	}

	public boolean isTrim_whitespace() {
		return trim_whitespace;
	}

	public void setTrim_whitespace(boolean trim_whitespace) {
		this.trim_whitespace = trim_whitespace;
	}

	public boolean isRemove_duplicated_term() {
		return remove_duplicated_term;
	}

	public void setRemove_duplicated_term(boolean remove_duplicated_term) {
		this.remove_duplicated_term = remove_duplicated_term;
	}

	public boolean isIgnore_pinyin_offset() {
		return ignore_pinyin_offset;
	}

	public void setIgnore_pinyin_offset(boolean ignore_pinyin_offset) {
		this.ignore_pinyin_offset = ignore_pinyin_offset;
	}

	@Override
	public void setType(FilterType type) {
	}
	
	

}
