package com.young.elasticsearch.index.configure.mappings;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.Datatypes;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.Fields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.binary.BinaryFields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.booleann.BooleanFields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.date.DateFields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.numeric.NumericFields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.object.MappingFields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.object.ObjectFields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.range.RangeFields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.string.KeywordFields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.string.StringFields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.string.TextFields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.suggester.CompletionSuggester;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.suggester.Suggester;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 用于构建mapping的工具类
 *
 * @author YoungLu
 * @date 2019-12-18
 * @date version 3.0
 */
public class EsMappings {
    //官方mapping的名称从6.x后固定为"_doc"
    public static final String OFFICIAL_MAPPING_NAME = "_doc";

    /**
     * 对于同一字段可能有多个分词形式，该方法负责处理String字段
     * @param mappingBuilder 未完成的mapping映射
     * @param field 字段
     * @param isMappingSon 是否是亲儿子
     * @return
     * @throws IOException
     */
    private static void getStringFields(XContentBuilder mappingBuilder, Fields field, boolean isMappingSon) throws IOException {
        StringFields stringFields = (StringFields) field;
        mappingBuilder.startObject(stringFields.getFieldName());
        mappingBuilder.field("type", stringFields.getDataType().getValue());
        //开始定义String类型的参数
        mappingBuilder.field("boost", stringFields.getBoost());
        mappingBuilder.field("eager_global_ordinals", stringFields.isEager_global_ordinals());
        mappingBuilder.field("index", stringFields.isIndex());
        mappingBuilder.field("index_options", stringFields.getIndex_options());
        mappingBuilder.field("norms", stringFields.isNorms());
        mappingBuilder.field("store", stringFields.isStore());
        mappingBuilder.field("similarity", stringFields.getSimilarity());

        //判断具体字符类型是text还是keyword,添加相应的参数
        if (Datatypes.TEXT.equals(field.getDataType())) {
            TextFields textFields = (TextFields) field;
            //定义text类型字符串的参数
            mappingBuilder.field("analyzer", textFields.getAnalyzer().getName());
            mappingBuilder.field("fielddata", textFields.isFielddata());
            mappingBuilder.field("position_increment_gap", textFields.getPosition_increment_gap());
            mappingBuilder.field("search_analyzer", textFields.getSearch_analyzer().getName());
            mappingBuilder.field("search_quote_analyzer", textFields.getSearch_quote_analyzer().getName());
            mappingBuilder.field("term_vector", textFields.getTerm_vector());
        } else if (Datatypes.KEYWORD.equals(field.getDataType())) {
            KeywordFields keywordFields = (KeywordFields) field;
            //定义keyword类型字符串的参数
            mappingBuilder.field("doc_values", keywordFields.isDoc_values());
            mappingBuilder.field("ignore_above", keywordFields.getIgnore_above());

            if (keywordFields.getNull_value() != null) {
                mappingBuilder.field("null_value", keywordFields.getNull_value());
            }
            if (keywordFields.getNormalizer() != null) {
                mappingBuilder.field("normalizer", keywordFields.getNormalizer().getName());
            }
        }

        //如果不是第一层,需要添加fields,作用是给这个字段添加一个不同类型的值到es
        /***
         * {
         *   "mappings": {
         *     "properties": {
         *       "city": {
         *         "type": "text",
         *         "fields": {
         *           "raw": {
         *             "type":  "keyword"
         *           }
         *         }
         *       }
         *     }
         *   }
         * }
         */
        if (!isMappingSon) {
            if (stringFields.getStringFields() != null) {
                mappingBuilder.startObject("fields");
                //递归调用
                getStringFields(mappingBuilder, stringFields.getStringFields(), false);
                mappingBuilder.endObject();
            }
        }
        mappingBuilder.endObject();
    }

    /**
     * 主要用于构建mapping映射
     *
     * @param mappingBuilder
     * @param fields
     * @return
     * @throws IOException
     */
    private static void getMappingFields(XContentBuilder mappingBuilder, ObjectFields fields) throws IOException {
        //是否归属于mapping子层
        boolean isMappingSon = false;

        //字段名称
        String fieldName = fields.getFieldName();
        if (StringUtils.isEmpty(fieldName)) {
            //如果是mapping字段类型,为空也没关系,因为字段名默认用_doc
            if ( fields instanceof MappingFields ) {
                fieldName = OFFICIAL_MAPPING_NAME;
            }else {
                throw new NullPointerException("字段名称不能为空！");
            }
        }

        //如果字段类型为Mapping类型,则定义mapping
        if (fields instanceof MappingFields) {
            //是隶属于mapping的下一层
            isMappingSon = true;
            mappingBuilder.startObject();
            mappingBuilder.startObject(fieldName);
        } else {
            //字段是object,则定义object,名字为fields的名字
            mappingBuilder.startObject(fieldName);
        }

        mappingBuilder.startObject("properties");

        for (Fields field : fields.getProperties().getFields()) {
            //字段类型
            Datatypes fieldDatatypes = field.getDataType();

            if (field instanceof StringFields) {
                getStringFields(mappingBuilder, field, isMappingSon);
            } else if (field instanceof BooleanFields) {
                BooleanFields booleanFields = (BooleanFields) field;
                mappingBuilder.startObject(booleanFields.getFieldName());
                mappingBuilder.field("type", booleanFields.getDataType().getValue());
                //定义boolean类型参数
                mappingBuilder.field("boost", booleanFields.getBoost());
                mappingBuilder.field("doc_values", booleanFields.isDoc_values());
                mappingBuilder.field("index", booleanFields.isIndex());
                mappingBuilder.field("null_value", booleanFields.getNull_value());
                mappingBuilder.field("store", booleanFields.isStore());
                //结束定义
                mappingBuilder.endObject();
            } else if (field instanceof DateFields) {
                DateFields dateFields = (DateFields) field;
                mappingBuilder.startObject(dateFields.getFieldName());
                mappingBuilder.field("type", dateFields.getDataType().getValue());
                //定义data类型参数
                mappingBuilder.field("boost", dateFields.getBoost());
                mappingBuilder.field("doc_values", dateFields.isDoc_values());
                mappingBuilder.field("format", dateFields.getFormat());
                mappingBuilder.field("ignore_malformed", dateFields.isIgnore_malformed());
                mappingBuilder.field("index", dateFields.isIndex());
                mappingBuilder.field("null_value", dateFields.getNull_value());
                mappingBuilder.field("store", dateFields.isStore());
                //结束定义
                mappingBuilder.endObject();
//				}else if(EsNumeric.DOUBLE.equals(field.getDataType()) || EsNumeric.INTEGER.equals(field.getDataType()) ||EsNumeric.FLOAT.equals(field.getDataType()) || EsNumeric.LONG.equals(field.getDataType()) || EsNumeric.SHORT.equals(field.getDataType()) || EsNumeric.BYTE.equals(field.getDataType()) || EsNumeric.HALF_FLOAT.equals(field.getDataType()) || EsNumeric.SCALED_FLOAT.equals(field.getDataType())) {
            } else if (field instanceof NumericFields) {
                NumericFields numericFields = (NumericFields) field;
                mappingBuilder.startObject(numericFields.getFieldName());
                mappingBuilder.field("type", numericFields.getDataType().getValue());
                //numeric特有参数定义
                mappingBuilder.field("coerce", numericFields.isCoerce());
                mappingBuilder.field("boost", numericFields.getBoost());
                mappingBuilder.field("doc_values", numericFields.isDoc_values());
                mappingBuilder.field("ignore_malformed", numericFields.isIgnore_malformed());
                mappingBuilder.field("index", numericFields.isIndex());
                if (numericFields.getNull_value() != null) {
                    mappingBuilder.field("null_value", numericFields.getNull_value());
                }
                mappingBuilder.field("store", numericFields.isStore());
                //结束定义
                mappingBuilder.endObject();
            } else if (field instanceof RangeFields) {
                RangeFields rangeFields = (RangeFields) field;
                mappingBuilder.startObject(rangeFields.getFieldName());
                mappingBuilder.field("type", rangeFields.getDataType().getValue());
                //定义range参数
                mappingBuilder.field("coerce", rangeFields.isCoerce());
                mappingBuilder.field("boost", rangeFields.getBoost());
                mappingBuilder.field("index", rangeFields.isIndex());
                mappingBuilder.field("store", rangeFields.isStore());
                //结束定义
                mappingBuilder.endObject();
            } else if (field instanceof BinaryFields) {
                BinaryFields binaryFields = (BinaryFields) field;
                mappingBuilder.startObject(binaryFields.getFieldName());
                mappingBuilder.field("type", binaryFields.getDataType().getValue());
                //定义binary参数
                mappingBuilder.field("doc_values", binaryFields.isDoc_values());
                mappingBuilder.field("store", binaryFields.isStore());
            } else if (field instanceof RangeFields) {
                RangeFields rangeFields = (RangeFields) field;
                mappingBuilder.startObject(rangeFields.getFieldName());
                mappingBuilder.field("type", rangeFields.getDataType().getValue());
                //定义range参数
                mappingBuilder.field("coerce", rangeFields.isCoerce());
                mappingBuilder.field("boost", rangeFields.getBoost());
                mappingBuilder.field("index", rangeFields.isIndex());
                mappingBuilder.field("store", rangeFields.isStore());
                //结束定义
                mappingBuilder.endObject();
            } else if (field instanceof ObjectFields) {
                getMappingFields(mappingBuilder, (ObjectFields) field);
            } else if (field instanceof Suggester) {
                if (Datatypes.COMPLETION.equals(fieldDatatypes)) {
                    CompletionSuggester completionSuggester = (CompletionSuggester) field;
                    mappingBuilder.startObject(completionSuggester.getFieldName());
                    mappingBuilder.field("type", completionSuggester.getDataType().getValue());
                    //定义completion参数
                    mappingBuilder.field("analyzer", completionSuggester.getAnalyzer());
                    mappingBuilder.field("search_analyzer", completionSuggester.getSearch_analyzer());
                    mappingBuilder.field("preserve_separators", completionSuggester.isPreserve_separators());
                    mappingBuilder.field("preserve_position_increments", completionSuggester.isPreserve_position_increments());
                    mappingBuilder.field("max_input_length", completionSuggester.getMax_input_length());
                    //结束定义
                    mappingBuilder.endObject();
                }
            }
        }
        //结束properties定义
        mappingBuilder.endObject();

        //如果字段类型为Mapping类型,则结束定义mapping
        if (fields instanceof MappingFields) {
            mappingBuilder.endObject();
            mappingBuilder.endObject();
        } else {
            mappingBuilder.endObject();
        }
    }

    /**
     * 解析mapping对象，构建mappingBuilder返回
     *
     * @param mappingFields mapping对象
     * @return 指定结构的mappingBuilder
     * @throws IOException
     */
    public static XContentBuilder mappingBuilder(MappingFields mappingFields) throws IOException {
        XContentBuilder mappingBuilder = XContentFactory.jsonBuilder();
        getMappingFields(mappingBuilder, mappingFields);
        return mappingBuilder;
    }

}
