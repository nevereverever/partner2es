package com.young.elasticsearch.config;

import com.young.elasticsearch.exception.ConnectionException;
import com.young.elasticsearch.utils.EsConstant;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/*************************
 * @author: YoungLu
 * @date: 2019/12/17 10:34
 * @description: elasticsearch配置类
 **************************/

public class ElasticsearchConfiguration {
    @Autowired
    private Properties configs;

    //是否已经初始化
    private boolean isInited = false;
    //http客户端连接池
    private Map<String, RestHighLevelClient> restClientPool = new ConcurrentHashMap<String, RestHighLevelClient>();

    public void initBySpring() {
        this.initEsClient("");
    }


    /**
     * 2020-07-24 由于springboot无法使用configs注入,可手动set
     * @return
     */
    public Properties getConfigs() {
        return configs;
    }

    public void setConfigs(Properties configs) {
        this.configs = configs;
    }

    /**
     * 根据文件路径初始化es客户端连接
     * @param confPath
     */
    public void initEsClient(String confPath) {
        //已经通过文件被初始化就不再重复进行初始化了
        if( isInited ){
            return;
        }
        try{
            //开始配置文件读取处理
            if( confPath == null || "".equals(confPath) ){
                this.initEsClient( (InputStream)null );
            }else{
                InputStream in = new FileInputStream( confPath );
                this.initEsClient(in);
            }
        }catch( Exception ex ){
            System.out.println("Cannot initEsClient (ElasticsearchConfiguration.initEsClientByFile)： \r\n" + ex.getMessage());
        }
        isInited = true;
    }

    public void initEsClient(InputStream in) {
        try{
            Properties prop = null;
            if(in != null) {
                prop = new Properties();
                prop.load( in );
            }else {
                prop = configs;
            }
            //加载elasticsearch配置文件
            String clusterName = prop.getProperty("es.cluster.name", EsConstant.CLUSTER_NAME);
            if( StringUtils.isEmpty(clusterName) ) {
                throw new IOException("elasticsearch cluster name is null,please check the configuration(the configuration item is <es.cluster.name>)");
            }

            String host = prop.getProperty("es.host.http", EsConstant.DEFAULT_ES_HOST);
            if( StringUtils.isEmpty(host) ) {
                throw new IOException("elasticsearch connection host is null,please check the configuration(the configuration item is <es.host.http>)");
            }

            String username = prop.getProperty("es.username");
            String password = prop.getProperty("es.password");
            ElasticsearchProperties elasticsearchProperties = new ElasticsearchProperties();
            elasticsearchProperties.setClusterName(clusterName);
            elasticsearchProperties.setHost(host);
            elasticsearchProperties.setUsername(username);
            elasticsearchProperties.setPassword(password);
            addHttpClientToPool(elasticsearchProperties);
        }catch (Exception ex) {
            System.out.println("Cannot initEsClient (ElasticsearchConfiguration.initEsClientByInputStream)： \r\n" + ex.getMessage());
        }
    }

    /**
     * 添加连接到连接池
     * @param elasticsearchProperties
     */
    public boolean addHttpClientToPool(ElasticsearchProperties elasticsearchProperties) {
        //集群名称
        String clusterName = elasticsearchProperties.getClusterName();
        if (StringUtils.isEmpty(clusterName)) {
            throw new NullPointerException("Elasticsearch clusterName is null.");
        }
        //服务器连接信息
        String host = elasticsearchProperties.getHost();
        if (StringUtils.isEmpty(host)) {
            throw new NullPointerException("Elasticsearch host information is null.");
        }

        String[] hosts = host.split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < httpHosts.length; i++) {
            String h = hosts[i];
            httpHosts[i] = new HttpHost(h.split(":")[0], Integer.parseInt(h.split(":")[1]), "http");
        }

        RestHighLevelClient restHighLevelClient = null;
        if (!StringUtils.isEmpty(elasticsearchProperties.getUsername())) {
            String username = elasticsearchProperties.getUsername();
            String password = elasticsearchProperties.getPassword();
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password));
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(httpHosts).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            httpClientBuilder.disableAuthCaching();
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                    })
            );
        } else {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(httpHosts));
        }

        if (restHighLevelClient != null) {
            restClientPool.put(clusterName, restHighLevelClient);
            return true;
        }

        return false;
    }
    /**
     * 从池中获取http客户端连接
     * @param elasticsearchProperties 集群连接信息
     * @return 客户端连接
     */
    public RestHighLevelClient getHttpClientFromPool( ElasticsearchProperties elasticsearchProperties ){
        String clusterName = elasticsearchProperties.getClusterName();
        RestHighLevelClient referInstance = restClientPool.get(clusterName);
        //没有获取到指定的连接实例,就尝试去添加连接实例
        if( referInstance == null && !StringUtils.isEmpty(elasticsearchProperties.getHost())) {
            try {
                //若成功添加则获取,否则不获取
                if ( addHttpClientToPool(elasticsearchProperties) ) {
                    referInstance = getHttpClientFromPool(elasticsearchProperties);
                }
            }catch (Exception ex){
                throw new ConnectionException("Trying to get elasticsearch http client error:"+ex.getMessage());
            }
        }

        return referInstance;
    }
}
