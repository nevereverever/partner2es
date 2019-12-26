package com.young.elasticsearch.repository.search.sort.pojo;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.Datatypes;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.io.stream.Writeable;
import org.elasticsearch.search.sort.SortMode;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

/**
 * 排序对象
 * @author YoungLu
 * @date 2018-05-22
 */
public class Sort implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String field;//字段名
	
	private SortOrder order;//排序方式
	
	private SortMode sortMode;//排序模式
	
	private Missing missing;//处理缺失字段的文档的方式
	
	private Datatypes type;//未被映射的字段以什么类型处理
	
	public enum Missing implements Writeable {
        _LAST, _FIRST;

        public static Missing readFromStream(StreamInput in) throws IOException {
            return in.readEnum(Missing.class);
        }

        @Override
        public void writeTo(StreamOutput out) throws IOException {
            out.writeEnum(this);
        }

        public static Missing fromString(String missing) {
            return valueOf(missing.toUpperCase(Locale.ROOT));
        }

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

	public SortOrder getOrder() {
		return order;
	}

	public void setOrder(SortOrder order) {
		this.order = order;
	}

	public SortMode getSortMode() {
		return sortMode;
	}

	public void setSortMode(SortMode sortMode) {
		this.sortMode = sortMode;
	}

	public Missing getMissing() {
		return missing;
	}

	public void setMissing(Missing missing) {
		this.missing = missing;
	}

	public Datatypes getType() {
		return type;
	}

	public void setType(Datatypes type) {
		this.type = type;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
}
