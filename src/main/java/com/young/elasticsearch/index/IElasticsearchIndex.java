package com.young.elasticsearch.index;

import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.object.MappingFields;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.Analysis;
import com.young.elasticsearch.index.pojo.index.MetaData;

/*************************
 * @author: YoungLu
 * @date: 2019/12/16 16:52
 * @description: elasticsearch index operator
 **************************/
public interface IElasticsearchIndex<T> {
    /**
     * 创建索引
     * @param clazz
     * @throws Exception
     */
    public void createIndex(Class<T> clazz) throws Exception;

    /**
     * 创建索引
     * @param indexName 索引名称
     * @param indexType 索引类型
     * @param analysis 分析器对象
     * @param mapping 索引的结构信息
     * @return
     * @throws Exception
     */
    public boolean createIndex(String indexName, String indexType, Analysis analysis, MappingFields mapping) throws Exception;

    /**
     * 创建索引
     * @param metaData 索引元数据信息
     * @param analysis settings的索引分析器对象
     * @param mapping 索引的结构信息
     * @return
     */
    public boolean createIndex(MetaData metaData, Analysis analysis, MappingFields mapping) throws Exception;

    /**
     * 删除指定的索引
     * @param indices 索引名称
     * @return 返回该索引是不是删除成功
     */
    public boolean dropIndex(String... indices) throws Exception;

    /**
     * 删除索引
     * @param clazz
     * @throws Exception
     * @return 返回该索引是不是删除成功
     */
    public boolean dropIndex(Class<T> clazz) throws Exception;

    /**
     * 索引是否存在
     * @param clazz
     * @return 是否存在该索引
     * @throws Exception
     */
    public boolean isIndexExists(Class<T> clazz) throws Exception;

    /**
     * 索引是否存在
     * @param indices 索引名称
     * @return 是否存在该索引
     * @throws Exception
     */
    public boolean isIndexExists(String... indices) throws Exception;

    /**
     * 索引类型是否存在
     * @param metaData 索引元数据信息
     * @return 是否存在该索引
     * @throws Exception
     */
    public boolean isIndexTypeExists(MetaData metaData) throws Exception;

}
