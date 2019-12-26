package com.young.elasticsearch.repository;

import com.young.elasticsearch.index.pojo.index.MetaData;
import com.young.elasticsearch.index.pojo.search.Additions;
import com.young.elasticsearch.index.pojo.search.EsResult;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;

import java.util.List;
import java.util.Map;

/*************************
 * @author: YoungLu
 * @date: 2019/12/18 15:08
 * @description: elasticsearch查询组件
 **************************/
public interface IElasticsearchTemplate<T,M> {

    //新增(注意,新增的API可用于修改索引,但并不建议这么做)-----------------------------------------------------

    /**
     * 保存文档到指定索引,并根据路由键决定到哪个elasticsearch节点
     * @param metaData 索引信息
     * @param t 数据对象
     * @param routing 路由键
     * @return 是否保存成功
     * @throws Exception
     */
    boolean save(MetaData metaData, T t, String routing) throws Exception;

    /**
     * 保存文档到指定索引
     * @param metaData 索引信息
     * @param t 数据对象
     * @return 是否保存成功
     */
    boolean save(MetaData metaData, T t) throws Exception;

    /**
     * 保存文档
     * @param t 数据对象
     * @return 是否保存成功
     */
    boolean save(T t) throws Exception;

    /**
     * 以Map形式保存
     * @param metaData 索引信息
     * @param id
     * @param sourceMap 索引的数据
     * @param routing 路由键
     * @return
     * @throws Exception
     */
    boolean saveMap(MetaData metaData, String id, Map<String, ?> sourceMap, String routing) throws Exception;

    /**
     * 批量新增,但这里并非一次性全部一起存放,会将集合拆分,拆分大小在Constant.BULK_SAVE_COUNT设置
     * @param tList
     * @return 批量保存的详细信息
     * @throws Exception
     */
    String saveBatch(List<T> tList) throws Exception;

    /**
     * 批量新增,但这里并非一次性全部一起存放,会将集合拆分,拆分大小在Constant.BULK_SAVE_COUNT设置
     * @param tList
     * @param metaData 索引信息
     * @return 批量保存的详细信息
     * @throws Exception
     */
    String saveBatch(List<T> tList, MetaData metaData) throws Exception;

    //修改-----------------------------------------------------
    /**
     * 修改文档
     * @param t 数据对象
     * @return 是否修改成功
     * @throws Exception
     */
    boolean update(T t) throws Exception;


    /**
     * 修改文档
     * @param metaData 索引信息
     * @param t 数据对象
     * @return 是否修改成功
     * @throws Exception
     */
    boolean update(MetaData metaData, T t) throws Exception;

    //删除-----------------------------------------------------
    /**
     * 删除文档
     * @param metaData 索引信息
     * @param t 数据对象
     * @param routing 路由键
     * @return 是否成功删除
     * @throws Exception
     */
    boolean delete(MetaData metaData, T t, M id, String routing) throws Exception;

    /**
     * 删除文档
     * @param metaData 索引信息
     * @param t 数据对象
     * @return 是否成功删除
     * @throws Exception
     */
    boolean delete(MetaData metaData, T t) throws Exception;

    /**
     * 删除文档
     * @param t 数据对象
     * @return 是否成功删除
     * @throws Exception
     */
    boolean delete(T t) throws Exception;

    /**
     * 根据id删除文档
     * @param metaData 索引信息
     * @param id
     * @return 是否删除成功
     */
    boolean deleteById(MetaData metaData, M id) throws Exception;

    /**
     * 根据条件删除
     * @param metaData 索引元数据
     * @param clazz 数据对象
     * @param queryBuilder 查询条件
     * @return 删除的response对象{
     * bulkResponse.getTotal();处理的文档总数
     * bulkResponse.getDeleted();删除了多少条文档
     * bulkResponse.getBatches();执行的批次数
     * bulkResponse.getNoops();跳过的文档数
     * bulkResponse.getTook();耗费的时间
     * }
     */
    public BulkByScrollResponse deleteByConditions(MetaData metaData, QueryBuilder queryBuilder, Class clazz) throws Exception;

    /**
     * 根据条件删除
     * @param clazz 数据对象
     * @param queryBuilder 查询条件
     * @return 删除的response对象{
     * bulkResponse.getTotal();处理的文档总数
     * bulkResponse.getDeleted();删除了多少条文档
     * bulkResponse.getBatches();执行的批次数
     * bulkResponse.getNoops();跳过的文档数
     * bulkResponse.getTook();耗费的时间
     * }
     */
    BulkByScrollResponse deleteByConditions(QueryBuilder queryBuilder, Class clazz) throws Exception;

    /**
     * 根据条件删除
     * @param metaData 索引元数据
     * @param queryBuilder 查询条件
     * @return 删除的response对象{
     * bulkResponse.getTotal();处理的文档总数
     * bulkResponse.getDeleted();删除了多少条文档
     * bulkResponse.getBatches();执行的批次数
     * bulkResponse.getNoops();跳过的文档数
     * bulkResponse.getTook();耗费的时间
     * }
     */
    BulkByScrollResponse deleteByConditions(MetaData metaData, QueryBuilder queryBuilder) throws Exception;

    //查询-----------------------------------------------------

    /**
     * 原生查询(如果其他查询API无法满足要求时,使用这个API构建查询)
     * @param searchRequest 查询请求
     * @return 查询相应对象
     * @throws Exception
     */
    SearchResponse nativeSearch(SearchRequest searchRequest) throws Exception;

    /**
     * 非分页普通查询（查询数量由EsConstant.NO_PAGING_DEFALT_SIZE决定）
     * @param queryBuilder 查询条件
     * @param clazz 数据对象
     * @return 查询结果
     * @throws Exception
     */
    List<T> search(QueryBuilder queryBuilder, Class<T> clazz) throws Exception;

    /**
     * 非分页普通查询（查询数量由EsConstant.NO_PAGING_DEFALT_SIZE决定）
     * @param metaData 索引信息
     * @param queryBuilder 查询条件
     * @param clazz 数据对象
     * @return 查询结果
     */
    List<T> search(QueryBuilder queryBuilder, MetaData metaData, Class<T> clazz) throws Exception;


    /**
     * 分页查询
     * @param additions
     * @param queryBuilder
     * @param clazz
     * @param indices
     * @return
     * @throws Exception
     */
    EsResult<T> pagingSearch(Additions additions, QueryBuilder queryBuilder, Class<T> clazz, String... indices) throws Exception;

    /**
     * 根据docId获取指定索引中的文档
     * @param docId
     * @return
     */
    T getById(M docId, Class<T> clazz) throws Exception;

    /**
     * 根据docId获取指定索引中的文档
     * @param docId
     * @param metaData
     * @param clazz
     * @return
     */
    T getById(M docId, MetaData metaData, Class<T> clazz) throws Exception;
}
