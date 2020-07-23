package com.young.elasticsearch.repository;

import com.young.elasticsearch.index.pojo.index.MetaData;
import com.young.elasticsearch.index.pojo.search.Additions;
import com.young.elasticsearch.index.pojo.search.EsPage;
import com.young.elasticsearch.index.pojo.search.EsResult;
import com.young.elasticsearch.repository.search.highlight.EsHightlightBuilder;
import com.young.elasticsearch.repository.search.highlight.pojo.Highlight;
import com.young.elasticsearch.repository.search.sort.EsSortBuilder;
import com.young.elasticsearch.repository.search.sort.pojo.Sort;
import com.young.elasticsearch.repository.storage.ErrorBatchDealLog4jImpl;
import com.young.elasticsearch.repository.storage.IErrorBatchDeal;
import com.young.elasticsearch.utils.EsConstant;
import com.young.elasticsearch.utils.EsTools;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*************************
 * @author: YoungLu
 * @date: 2019/12/18 15:28
 * @description: elasticsearch查询组件实现
 **************************/
@Data
@Log4j2
public class ElasticsearchTemplateImpl<T, M> implements IElasticsearchTemplate<T, M> {
    private RestHighLevelClient restHighLevelClient;

    //默认用log4j保存批量保存没正确保存的批次信息
    private IErrorBatchDeal errorBatchDeal = new ErrorBatchDealLog4jImpl();

    /**
     * 保存部分文档
     *
     * @param list        集合
     * @param metaData 索引信息
     * @return
     * @throws Exception
     */
    private BulkResponse savePart(List<T> list, MetaData metaData) throws Exception {
        T t = list.get(0);
        //1.获取所有有含义的字段名称
        List<String> meaningFieldsList = EsTools.getFieldNameFromObjWithoutIdAndNullField(t.getClass());
        //2.处理索引信息
        metaData = dealMetaData(metaData, t.getClass());
        //获取索引名称和索引类型
        String indexName = metaData.getIndexname();
        String indexType = metaData.getIndextype();

        BulkRequest bulkRequest = new BulkRequest();

        for (int i = 0; i < list.size(); i++) {
            T tObj = list.get(i);
            M id = EsTools.getESId(tObj);
            String source = EsTools.convertToJson(tObj,meaningFieldsList);

            IndexRequest indexRequest = null;
            if (StringUtils.isEmpty(id)) {
                indexRequest = new IndexRequest(indexName, indexType);
            } else {
                indexRequest = new IndexRequest(indexName, indexType, id.toString());
            }

            indexRequest.source(source, XContentType.JSON);
            if (metaData.isPrintLog()) {
                log.info("Index document save success(Batch save): index:{},type:{},doc:{} ", indexName, indexType, source);
            }
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse;
    }

    /**
     * 处理索引元数据信息
     *
     * @param metaData 索引元数据对象
     * @param clazz           泛型对象
     */
    private MetaData dealMetaData(MetaData metaData, Class clazz) {
        //索引的信息为空,就尝试从对象中获取索引信息
        if (metaData == null) {
            metaData = EsTools.getIndexInformation(clazz);
        }
        if (metaData == null) {
            throw new NullPointerException("无法获取索引基本信息,请检查.");
        }
        return metaData;
    }

    @Override
    public boolean save(MetaData metaData, T t, String routing) throws Exception {
        metaData = dealMetaData(metaData, t.getClass());
        //获取索引名称和索引类型
        String indexName = metaData.getIndexname();
        String indexType = metaData.getIndextype();

        //获取对象中被@ESID注解的字段值
        M id = EsTools.getESId(t);

        IndexRequest indexRequest = null;
        if (StringUtils.isEmpty(id)) {
            indexRequest = new IndexRequest(indexName, indexType);
        } else {
            indexRequest = new IndexRequest(indexName, indexType, id.toString());
        }

        String source = EsTools.convertToJson(t,null);
        indexRequest.source( source, XContentType.JSON );

        if ( !StringUtils.isEmpty(routing) ) {
            indexRequest.routing(routing);
        }

        if (metaData.isPrintLog()) {
            log.info("被索引文档的内容为:{}", source);
        }

        IndexResponse indexResponse = null;
        indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            if (metaData.isPrintLog()) {
                log.info("ElasticsearchTemplateImpl.save() - Index document create success: index:{},type:{},doc:{} ", indexName, indexType, source);
            }
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            if (metaData.isPrintLog()) {
                log.info("ElasticsearchTemplateImpl.save() - Index document update success: index:{},type:{},doc:{} ", indexName, indexType, source);
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public boolean save(MetaData metaData, T t) throws Exception {
        return save(metaData, t, null);
    }

    @Override
    public boolean save(T t) throws Exception {
        return save(null, t);
    }

    @Override
    public boolean saveMap(MetaData metaData, String id, Map<String, ?> sourceMap, String routing) throws Exception {
        //获取索引名和索引类型
        String indexName = metaData.getIndexname();
        if ( StringUtils.isEmpty(metaData) ) {
            throw new NullPointerException("保存map类型索引失败,索引名称不可为空.");
        }

        String indexType = metaData.getIndextype();

        IndexRequest indexRequest = null;
        if (StringUtils.isEmpty(id)) {
            indexRequest = new IndexRequest( indexName, indexType );
        } else {
            indexRequest = new IndexRequest( indexName, indexType, id );
        }

        indexRequest.source(sourceMap);

        if ( !StringUtils.isEmpty(routing) ) {
            indexRequest.routing(routing);
        }

        if (metaData.isPrintLog()) {
            log.info("被索引文档的内容为:{}", sourceMap);
        }

        IndexResponse indexResponse = null;
        indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            if (metaData.isPrintLog()) {
                log.info("ElasticsearchTemplateImpl.save() - Index document create success: index:{},type:{},doc:{} ", indexName, indexType, sourceMap);
            }
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            if (metaData.isPrintLog()) {
                log.info("ElasticsearchTemplateImpl.save() - Index document update success: index:{},type:{},doc:{} ", indexName, indexType, sourceMap);
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public String saveBatch(List<T> tList) throws Exception {
        return saveBatch(tList, null);
    }

    @Override
    public String saveBatch(List<T> tList, MetaData metaData) throws Exception {
        StringBuilder saveBatchDetails = new StringBuilder("批量保存明细信息");
        int tListSize = 0;

        if (tList == null || (tListSize = tList.size()) == 0) {
            saveBatchDetails.append("需要保存的数据集合为空.");
            return saveBatchDetails.toString();
        }
        //防止服务器端内存爆掉,这里要对集合进行拆分然后批量请求
        List<List<T>> partedList = EsTools.splitList(tList);
        int partedListSize = partedList.size();
        saveBatchDetails.append("(总数据量:");
        saveBatchDetails.append(tListSize);
        saveBatchDetails.append(",批量保存的数据被拆分,每批次大小为");
        saveBatchDetails.append(EsConstant.BULK_SAVE_COUNT);
        saveBatchDetails.append(",总共被拆分为:");
        saveBatchDetails.append(partedListSize);
        saveBatchDetails.append("个集合分批保存):\r\n");
        for (int i = 0; i < partedListSize; i++) {
            List<T> currentPartList = null;
            try {
                saveBatchDetails.append("批次");
                saveBatchDetails.append(i);
                saveBatchDetails.append(":");
                currentPartList = partedList.get(i);
                BulkResponse bulkResponse = savePart(currentPartList, metaData);
                saveBatchDetails.append(bulkResponse.status());
            } catch (Exception e) {
                saveBatchDetails.append("批量保存失败,请检查日志!");
                log.error("索引文档批量保存失败:{}.", e.toString());
                errorBatchDeal.deal(currentPartList);
                continue;
            } finally {
                saveBatchDetails.append(";\r\n");
            }
        }
        return saveBatchDetails.toString();
    }

    @Override
    public boolean update(T t) throws Exception {
        return update(null,t);
    }

    @Override
    public boolean update(MetaData metaData, T t) throws Exception {
        metaData = dealMetaData(metaData, t.getClass());
        //获取索引名称和索引类型
        String indexName = metaData.getIndexname();
        String indexType = metaData.getIndextype();
        //获取对象中被@ESID注解的字段值
        M id = EsTools.getESId(t);
        if (StringUtils.isEmpty(id)) {
            throw new NullPointerException("修改索引文档信息时,文档id不可为空!");
        }
        //构建修改请求
        UpdateRequest updateRequest = new UpdateRequest(indexName, indexType, id.toString());
        //获取除了id以外的其他字段值并映射为map
        Map source = EsTools.getFieldValueToMap(t);
        updateRequest.doc(source);
        if (metaData.isPrintLog()) {
            log.info("被修改的索引名称:{},索引类型:{},文档id为:{},内容为:{}",indexName,indexType, id, source);
        }

        UpdateResponse updateResponse = null;
        updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
            if (metaData.isPrintLog()) {
                log.info("ElasticsearchTemplateImpl.update() - Index document create success: index:{},type:{},doc_id:{},doc:{} ", indexName, indexType, id, source);
            }
        } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            if (metaData.isPrintLog()) {
                log.info("ElasticsearchTemplateImpl.update() - Index document update success: index:{},type:{},doc_id:{},doc:{} ", indexName, indexType, id, source);
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(MetaData metaData, T t, M id, String routing) throws Exception {
        if (t != null) {
            metaData = dealMetaData(metaData, t.getClass());
        }
        String indexName = metaData.getIndexname();
        String indexType = metaData.getIndextype();

        if (StringUtils.isEmpty(id)) {
            //获取对象中被@ESID注解的字段值
            id = EsTools.getESId(t);
            if (StringUtils.isEmpty(id)) {
                throw new NullPointerException("根据id删除索引文档信息时,文档id不可为空!");
            }
        }
        DeleteRequest deleteRequest = new DeleteRequest(indexName, indexType, id.toString());
        if (!StringUtils.isEmpty(routing)) {
            deleteRequest.routing(routing);
        }
        DeleteResponse deleteResponse = null;
        deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);

        if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
            if (metaData.isPrintLog()) {
                log.info("ElasticsearchTemplateImpl.delete() - Index document delete success: index:{},type:{},id:{} ", indexName, indexType, id);
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(MetaData metaData, T t) throws Exception {
        return delete(metaData, t,null,null);
    }

    @Override
    public boolean delete(T t) throws Exception {
        return delete(null, t);
    }

    @Override
    public boolean deleteById(MetaData metaData, M id) throws Exception {
        return delete(metaData,null,id,null);
    }

    @Override
    public BulkByScrollResponse deleteByConditions(MetaData metaData, QueryBuilder queryBuilder, Class clazz) throws Exception {
        metaData = dealMetaData(metaData, clazz);
        String[] indicesNames = metaData.getSearchIndexNames();
        DeleteByQueryRequest request = new DeleteByQueryRequest(indicesNames);
        if (metaData.isPrintLog()) {
            log.info("根据条件删除日志,索引名称:{},查询条件为:{}", indicesNames, queryBuilder);
        }
        request.setQuery(queryBuilder);
        request.setBatchSize(EsConstant.DELETE_BATCH_SIZE);
        BulkByScrollResponse bulkResponse = restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
        return bulkResponse;
    }

    @Override
    public BulkByScrollResponse deleteByConditions(QueryBuilder queryBuilder, Class clazz) throws Exception {
        return deleteByConditions(null,queryBuilder,clazz);
    }

    @Override
    public BulkByScrollResponse deleteByConditions(MetaData metaData, QueryBuilder queryBuilder) throws Exception {
        return deleteByConditions(metaData, queryBuilder, null);
    }

    @Override
    public SearchResponse nativeSearch(SearchRequest searchRequest) throws Exception {
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    @Override
    public List<T> search(QueryBuilder queryBuilder, Class<T> clazz) throws Exception {
        return search(queryBuilder, null, clazz);
    }

    @Override
    public List<T> search(QueryBuilder queryBuilder, MetaData metaData, Class<T> clazz) throws Exception {
        metaData = dealMetaData(metaData,clazz);
        String[] searchIndices = metaData.getSearchIndexNames();
        List<T> list = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(searchIndices);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(EsConstant.NO_PAGING_DEFALT_SIZE);
        searchRequest.source(searchSourceBuilder);
        if (metaData.isPrintLog()) {
            log.info("查询索引:{},查询大小:{},查询条件:{}",searchIndices,EsConstant.NO_PAGING_DEFALT_SIZE,searchSourceBuilder.toString());
        }
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String source = hit.getSourceAsString();
            T hitObj =  EsTools.convertToObj(source, clazz);
            //将_id字段重新赋值给@ESID注解的字段
            EsTools.correctIDToObj(clazz, hitObj, hit.getId());
            list.add(hitObj);
        }
        return list;
    }

    @Override
    public EsResult<T> pagingSearch(Additions additions, QueryBuilder queryBuilder, Class<T> clazz, String... indices) throws Exception {
        if ( additions == null) {
            throw new NullPointerException("分页查询方法,附加条件不可为空.");
        }
        //从对象中获取索引元数据信息
        MetaData metaData = EsTools.getIndexInformation(clazz);
        if (metaData == null) {
            throw new NullPointerException("分页查询方法,无法获取索引数据信息,请确认注解@EsMetaData标签是否使用正确.");
        }

        //参数中的索引名称为空,就从索引数据信息对象中获取要查询的索引名称
        if ( EsTools.arrayISNULL(indices) ) {
            indices = metaData.getSearchIndexNames();
        }

        //构建查询对象和请求信息
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        //是否开启高亮
        boolean isHighLight = false;
        Highlight highlight = additions.getHighlight();
        if( highlight != null ) {
            isHighLight = true;
            HighlightBuilder highlightBuilder = EsHightlightBuilder.highlightBuilder(highlight);
            searchSourceBuilder.highlighter(highlightBuilder);
        }

        //是否进行排序
        Sort[] sorts = additions.getSorts();
        if ( sorts != null ) {
            for (Sort sort : sorts) {
                SortBuilder sortBuilder = EsSortBuilder.sortBuilder(sort);
                searchSourceBuilder.sort(sortBuilder);
            }
        }
        return getPagingResult(searchRequest, additions.getEsPage(), searchSourceBuilder, additions.isSearchAfter(), isHighLight, metaData.isPrintLog(),clazz);
    }

    @Override
    public T getById(M docId, Class<T> clazz) throws Exception {
        return getById(docId,null,clazz);
    }

    @Override
    public T getById(M docId, MetaData metaData, Class<T> clazz) throws Exception {
        if (StringUtils.isEmpty(docId)) {
            throw new NullPointerException("根据id查询文档详细内容时id不可为空");
        }
        metaData = dealMetaData(metaData,clazz);
        //查询的索引名称
        String indexName = metaData.getIndexname();
        String indexType = metaData.getIndextype();
        GetRequest getRequest = new GetRequest(indexName, indexType, docId.toString());
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        if ( metaData.isPrintLog() ) {
            log.info("查询索引名:{},索引类型:{},文档id:{}的索引文档.",indexName,indexType,docId);
        }

        return EsTools.convertToObj(getResponse.getSourceAsString(),clazz);
    }

    private EsResult<T> getPagingResult(SearchRequest searchRequest, EsPage esPage, SearchSourceBuilder searchSourceBuilder, boolean searchAfter, boolean isHighlight, boolean isPrintLog, Class<T> clazz) throws IOException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        String searchUUID = null;
        EsResult result = new EsResult();
        //1.获取当前页码偏移量和页面大小,设置页码信息
        int currentStartIndex = esPage.getCurrentStartIndex();
        int pageSize = esPage.getPageSize();
        //对于超过查询窗口大小的查询 页大小应该根据情况切掉一部分 否则会报错
        if( currentStartIndex + pageSize > EsConstant.MAX_RESULT_WINDOW ) {
            pageSize = EsConstant.MAX_RESULT_WINDOW - currentStartIndex;
        }
        //searchAfter不能有from字段
        if( !searchAfter ) {
            searchSourceBuilder.from(currentStartIndex);
        }
        searchSourceBuilder.size(pageSize);
        searchRequest.source(searchSourceBuilder);

        if ( isPrintLog ) {
            searchUUID = UUID.randomUUID().toString();
            log.info("分页查询id:{}-查询请求内容为:{}",searchUUID,searchSourceBuilder.toString());
        }

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //2.处理结果集
        SearchHits searchHits = searchResponse.getHits();
        esPage.setActualResultCount(searchHits.getTotalHits());
        result.setEsPage(esPage);
        log.info("分页查询id:{}-查询响应内容为:{}",searchUUID,searchResponse.toString());
        SearchHit[] searchHitArr = searchHits.getHits();
        int searchHitArrLength = searchHitArr.length;
        if( searchHitArrLength == 0 ) {
            return result;
        }

        List<T> resultList = new ArrayList<>(searchHitArrLength);
        for ( SearchHit searchHit : searchHitArr ) {
            T t = EsTools.convertToObj(searchHit.getSourceAsString(),clazz);
            EsTools.correctIDToObj(clazz, t,searchHit.getId());
            //替换高亮字段内容
            if ( isHighlight ) {
                Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
                List<String> ignoredProperties = new ArrayList<>();
                Object obj = EsTools.highLightMapToObj(highlightFieldMap, clazz,ignoredProperties);
                String[] ignoredPropertiesArr = new String[ignoredProperties.size()];
                BeanUtils.copyProperties(obj, t, ignoredProperties.toArray(ignoredPropertiesArr));
            }
            resultList.add(t);
        }

        result.setResultList(resultList);
        //3.最后一条的值作为searchAfter的sortValues
        result.setSortValues(searchHitArr[searchHitArrLength -1].getSortValues());
        return result;
    }

}
