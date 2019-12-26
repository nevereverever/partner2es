package com.young.elasticsearch.index.configure.mappings.fields.datatypes;

public enum Datatypes {
	BINARY("banary","二进制类型"),
	BOOLEAN("boolean","布尔型"),
	DATE("date","日期类型，默认 strict_date_optional_time||epoch_millis"),
	NESTED("nested","嵌套类型"),
	LONG("long","带符号的64位整型,最小值-2^63,最大值2^63-1"),
	INTEGER("integer","带符号的32位整型,最小值-2^31,最大值2^31-1"),
	SHORT("short","带符号的32位整型,最小值-32,768,最大值32,767"),
	BYTE("byte","一个带符号的8位整数，最小值为-128，最大值为127"),
	DOUBLE("double","一个双精度64位IEEE 754浮点数，仅限于有限值"),
	FLOAT("float","一个单精度32位IEEE 754浮点数，仅限于有限值"),
	HALF_FLOAT("half_float","一个半精度的16位ieee 754浮点数，仅限于有限值"),
	SCALED_FLOAT("scaled_float","长度固定的缩放因子支持的浮点数。"),
	OBJECT("object","对象类型"),
	MAPPING("mapping","映射（为完成功能定义的特殊类型）"),
	INTEGER_RANGE("integer_range","带符号的32位整数范围值，最小值为-2^31，最大值为2^31-1"),
	FLOAT_RANGE("float_range","单精度32位ieee 754浮点值范围值"),
	LONG_RANGE("long_range","带符号的64位整数范围值，最小值为-2^63，最大值为2^63-1"),
	DOUBLE_RANGE("double_range","64位双精度ieee 754浮点数范围值，最小值为-2^31，最大值为2^31-1"),
	DATE_RANGE("date_range","日期范围值"),
	IP_RANGE("ip_range","支持IPV4和IPV6的范围值"),
	TEXT("text","文本类型"),
	KEYWORD("keyword","关键字类型(不会被分词)"),
	COMPLETION("completion","搜索即用");
	
	
	
	
	
	
	private String value;
	private String desc;
	
	Datatypes(final String value,final String desc){
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
