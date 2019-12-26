package com.young.elasticsearch.annotation;

import java.lang.annotation.*;

/*************************
 * @author: YoungLu
 * @date: 2019/12/17 15:53
 * @description: 字段映射注解
 **************************/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ESMapping {
    /**
     * 数据类型
     */
    String datatype() default "text";
    /**
     * 源数据是否保存
     */
    boolean store() default true;
    /**
     * 关键字忽略字数
     */
    int ignore_above() default 256;
    /**
     * 是否支持ngram，高效全文搜索提示
     */
    boolean ngram() default false;
    /**
     * 是否支持suggest，高效前缀搜索提示
     */
    boolean suggest() default false;
    /**
     * 索引分词器设置（研究类型）
     */
    String analyzer() default "standard";
    /**
     * 搜索内容分词器设置
     */
    String search_analyzer() default "standard";

    /**
     * 是否允许被搜索
     */
    boolean allow_search() default true;

    /**
     * 拷贝到哪个字段，代替_all
     */
    String copy_to() default "";

    /**
     * null_value指定，默认空字符串不会为mapping添加null_value
     * 对于值是null的进行处理，当值为null是按照注解指定的‘null_value’值进行查询可以查到
     * 需要注意的是要与根本没有某字段区分（没有某字段需要用Exists Query进行查询）
     * 建议设置值为NULL_VALUE
     * @return
     */
    String null_value() default "";
}