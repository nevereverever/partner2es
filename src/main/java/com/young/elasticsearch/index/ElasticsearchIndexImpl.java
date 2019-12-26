package com.young.elasticsearch.index;

import com.young.elasticsearch.index.configure.mappings.EsMappings;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.object.MappingFields;
import com.young.elasticsearch.index.configure.settings.EsSettings;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.Analysis;
import com.young.elasticsearch.index.pojo.index.MetaData;
import lombok.Data;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

/*************************
 * @author: YoungLu
 * @date: 2019/12/17 17:01
 * @description: elasticsearch 索引操作对象实现类
 **************************/
@Data
public class ElasticsearchIndexImpl<T> implements IElasticsearchIndex<T> {
    private RestHighLevelClient restHighLevelClient;

    public ElasticsearchIndexImpl(){
    }

    public ElasticsearchIndexImpl( RestHighLevelClient restHighLevelClient ){
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public void createIndex(Class<T> clazz) throws Exception {

    }

    @Override
    public boolean createIndex(String indexName, String indexType, Analysis analysis, MappingFields mapping) throws Exception {
        MetaData metaData = new MetaData(indexName, indexType);
        return createIndex(metaData, analysis, mapping);
    }

    @Override
    public boolean createIndex(MetaData metaData, Analysis analysis, MappingFields mapping) throws Exception {
        //索引名和索引类型
        String indexName = metaData.getIndexname();
        String indexType = metaData.getIndextype();
        //索引分片数和索引副本数
        int numberOfShards = metaData.getNumberOfShards();
        int numberOfReplicas = metaData.getNumberOfReplicas();
        //构建创建索引的请求对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        //1.提前定义索引的分析器等信息
        XContentBuilder settingBuilder = EsSettings.settingsBuilder(numberOfShards, numberOfReplicas, analysis);
        createIndexRequest.settings(settingBuilder);
        //2.定义索引的结构信息
        XContentBuilder mappingBuilder = EsMappings.mappingBuilder(mapping);
        createIndexRequest.mapping(indexType, mappingBuilder);

        CreateIndexResponse response = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @Override
    public boolean dropIndex(String... indices) throws Exception {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indices);
        AcknowledgedResponse response = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @Override
    public boolean dropIndex(Class<T> clazz) throws Exception {
        return false;
    }

    @Override
    public boolean isIndexExists(Class<T> clazz) throws Exception {
        return false;
    }

    @Override
    public boolean isIndexExists(String... indices) throws Exception {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(indices);
        return restHighLevelClient.indices().exists(getIndexRequest,RequestOptions.DEFAULT);
    }

    @Override
    public boolean isIndexTypeExists(MetaData metaData) throws Exception {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(metaData.getIndexname());
        getIndexRequest.types(metaData.getIndextype());
        restHighLevelClient.indices().exists(getIndexRequest,RequestOptions.DEFAULT);
        return false;
    }
}
