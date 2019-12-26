package com.young.elasticsearch.annotation;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ESMetaData {
    /**
     * 检索时的索引名称，如果不配置则默认为和indexName一致，该注解项仅支持搜索
     * 不建议这么做，建议通过特定方法来做跨索引查询
     */
    String[] searchIndexNames() default {};

    /**
     * 索引名称
     */
    String indexName() default "";

    /**
     * 索引前缀
     */
    String indexPrefix() default "";


    String indexSuffixDateFormat() default "yyyyMMdd";

    /**
     * 索引类型，可以不配置，不配置默认_doc
     */
    String indexType() default "_doc";
    /**
     * 主分片数量
     * @return
     */
    int numberOfShards() default 1;
    /**
     * 备份分片数量
     * @return
     */
    int numberOfReplicas() default 0;

    /**
     * 是否打印日志
     * @return
     */
    boolean printLog() default false;
}
