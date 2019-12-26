package com.young;

import com.young.elasticsearch.config.ElasticsearchConfiguration;
import com.young.elasticsearch.config.ElasticsearchProperties;
import com.young.elasticsearch.index.ElasticsearchIndexImpl;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.Datatypes;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.Fields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.object.MappingFields;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.string.TextFields;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer.Analyzer;
import com.young.elasticsearch.index.configure.settings.analysis.pojo.analyzer.CustomAnalyzer;
import com.young.elasticsearch.index.pojo.index.MetaData;
import com.young.elasticsearch.index.pojo.search.Additions;
import com.young.elasticsearch.index.pojo.search.EsPage;
import com.young.elasticsearch.index.pojo.search.EsResult;
import com.young.elasticsearch.repository.ElasticsearchTemplateImpl;
import com.young.elasticsearch.repository.search.highlight.pojo.Highlight;
import com.young.elasticsearch.repository.search.sort.pojo.Sort;
import com.young.pojo.LogSample;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.young.elasticsearch.index.configure.mappings.fields.datatypes.pojo.object.Properties;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath*:applicationContext*.xml"})
public class AppTest {
    @Autowired
    ElasticsearchConfiguration elasticsearchConfiguration;

    @Autowired
    ElasticsearchIndexImpl elasticsearchIndex;

    @Autowired
    ElasticsearchTemplateImpl elasticsearchTemplate;

    public static final String indexName = "powercloud_log_20191226";

    @Test
    public void demoTester() throws Exception {
        init();
        searchPagingDemo();
    }

    void searchPagingDemo() throws Exception {
        Additions addtions = new Additions();
        EsPage esPage = new EsPage();
        esPage.setCurrentPage(1);
        esPage.setPageSize(10);
        addtions.setEsPage(esPage);
        Highlight highLight = new Highlight();

        HighlightBuilder.Field[] fields = new HighlightBuilder.Field[1];
        HighlightBuilder.Field field = new HighlightBuilder.Field("log_detail");
        field.preTags("<div>");
        field.postTags("</div>");
        fields[0] = field;

        highLight.setFields(fields);
        addtions.setHighlight(highLight);
        Sort sort1=new Sort();
        sort1.setField("log_time");
        sort1.setOrder(SortOrder.DESC);
        Sort[] sorts = {sort1};
        addtions.setSorts(sorts);

        QueryBuilder queryBuilder = new MatchPhraseQueryBuilder("log_detail", "微服务消费者");
        EsResult<LogSample> result = elasticsearchTemplate.pagingSearch(addtions,queryBuilder,LogSample.class,indexName);
        System.out.println(result.getResultList());
    }

    void searchNoPapingDemo() throws Exception {
        MetaData metaData = new MetaData(indexName);
        metaData.setPrintLog(true);

        QueryBuilder queryBuilder = new MatchAllQueryBuilder();
        List<LogSample> sampleList = elasticsearchTemplate.search( queryBuilder , metaData, LogSample.class);

        System.out.println("list长度为:"+sampleList.size());
        System.out.println("list内容:"+sampleList.toString());
    }

    /**
     * BulkByScrollResponse[took=894ms,timed_out=false,sliceId=null,updated=0,created=0,deleted=10000,batches=10,versionConflicts=0,noops=0,retries=0,throttledUntil=0s,bulk_failures=[],search_failures=[]]
     * @throws Exception
     */
    void deleteByQueryDemo() throws Exception {
        MetaData metaData = new MetaData(indexName);
        metaData.setPrintLog(true);
        //删除所有的日志
        QueryBuilder queryBuilder = new MatchAllQueryBuilder();
        System.out.println(elasticsearchTemplate.deleteByConditions(metaData,queryBuilder));
    }

    void deleteDemo() throws Exception {
        LogSample logSample = new LogSample();
        logSample.setId("5Ke3HG8BqdQaDPAGu5h4");
        elasticsearchTemplate.delete(logSample);
    }

    void updateDemo() throws Exception {
        LogSample logSample = new LogSample();
        logSample.setId("5Ke3HG8BqdQaDPAGu5h4");
        logSample.setLog_outline("被修改的日志概要信息.");
        logSample.setLog_detail("被修改的日志详情信息.");
        System.out.println(elasticsearchTemplate.update(logSample)?"文档修改成功":"文档修改失败");
    }

    void batchSaveDemo() throws Exception {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<LogSample> sampleList = new ArrayList<>();
        LogSample logSample = new LogSample();
        logSample.setApp_name("powercloud");
        logSample.setGroup_name("powercloud_log");
        logSample.setModule_name("powercloud_log");
        logSample.setLog_type("error");
        logSample.setServer_uuid("adfdfga");
        logSample.setLog_outline("这是AOP后的日志概要,@"+Calendar.getInstance().getTime() );
        logSample.setLog_detail("这是AOP后的日志详情解放昆仑山搭街坊肯德基死哦官方及哦韩国");

        for (int i = 0; i < 10000; i++ ){
            Date d = new Date();
            logSample.setLog_time(fmt.format(d));
            sampleList.add(logSample);
        }
        System.out.println(elasticsearchTemplate.saveBatch(sampleList));
    }

    void saveDemo() throws Exception {
        LogSample logSample = new LogSample();
        logSample.setApp_name("powercloud");
        logSample.setGroup_name("powercloud_log");
        logSample.setModule_name("powercloud_log");
        logSample.setLog_type("error");
        logSample.setServer_uuid("adfdfga");
        logSample.setLog_outline("这是AOP后的日志概要,@"+Calendar.getInstance().getTime() );
        String detail = "中华人民共和国中国中国人萨拉丁建瓯撒旦粉红色的和覅u还是对规划爱思收到两份简历大赛" +
                "中华人民中较上年得分第四u覅杜埃菲迪斯uiu的撒啊" +
                "撒地方和湿地和覅算好覅u说大话覅u还是中国人民" +
                "收到发士大夫hi说大话覅u啊但是u还u给中国人民" +
                "中华人民共和国";

        logSample.setLog_detail(detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail+detail);
        logSample.setLog_time("2019-12-22 10:00:00");
        elasticsearchTemplate.save(logSample);
    }


    void dropIndexDemo() throws Exception {
        String[] index = {"es-http-test","hello"};
        System.out.println(elasticsearchIndex.dropIndex(index)?"索引删除成功":"索引删除失败");
    }

    /**
     * 判断索引是不是存在的demo
     * @throws Exception
     */
    void indexExistsDemo() throws Exception {
        String index = indexName;
        System.out.println(elasticsearchIndex.isIndexExists(index)?"该索引存在":"该索引不存在");
    }


    /**
     * 创建索引的demo
     * @throws Exception
     */
    void createIndexDemo() throws Exception{
        MetaData metaData = new MetaData("es-http-test","_doc",1,0);
        metaData.setPrintLog(true);
        MappingFields mapping = new MappingFields();
        Properties properties = new Properties();
        List<Fields> fields = new ArrayList<>();
        TextFields name = new TextFields();
        name.setDataType(Datatypes.TEXT);
        name.setFieldName("name");

        Analyzer ikAnalyzer = new CustomAnalyzer() ;
        ikAnalyzer.setName("ik_smart");
        name.setAnalyzer(ikAnalyzer);
        fields.add(name);

        properties.setFields(fields);
        mapping.setProperties(properties);

        elasticsearchIndex.createIndex(metaData,null, mapping);
    }

    /**
     * 客户端初始化
     */
    public void init(){
        ElasticsearchProperties properties = new ElasticsearchProperties();
        properties.setClusterName("powercloud_log");
        RestHighLevelClient client = elasticsearchConfiguration.getHttpClientFromPool(properties);
        elasticsearchIndex.setRestHighLevelClient(client);
        elasticsearchTemplate.setRestHighLevelClient(client);
    }
}
