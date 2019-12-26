package com.young.elasticsearch.index.pojo.index;

import lombok.Data;
import org.springframework.util.StringUtils;

/*************************
 * @author: YoungLu
 * @date: 2019/12/17 15:06
 * @description: 索引元数据信息，与@ESMetaData对应
 **************************/
@Data
public class MetaData {
    //索引名称
    String indexname;
    //索引类型,从6.x后实际上一个索引有且仅有1个索引类型
    String indextype = "_doc";
    //索引前缀名
    String indexPrefix;
    //索引后缀时间格式
    String indexSuffixDateFormat = "yyyyMMdd";
    //分片数
    int numberOfShards = 1;
    //副本数
    int numberOfReplicas = 0;
    //是否打印request请求串
    boolean printLog = false;
    //查询索引的集合
    String[] searchIndexNames;

    public MetaData(){

    }

    public String[] getSearchIndexNames() {
        if ( searchIndexNames == null || searchIndexNames.length ==0 ) {
            this.searchIndexNames = new String[]{this.indexname};
        }
        return searchIndexNames;
    }

    public MetaData(String indexname) {
        this.indexname = indexname;
    }

    public MetaData(String indexname, String indextype) {
        this.indexname = indexname;
        if ( !StringUtils.isEmpty(indextype) ) {
            this.indextype = indextype;
        }
    }

    public MetaData(String indexname, int numberOfShards, int numberOfReplicas) {
        this.indexname = indexname;
        this.numberOfShards = numberOfShards;
        this.numberOfReplicas = numberOfReplicas;
    }

    public MetaData(String indexname, String indextype, int numberOfShards, int numberOfReplicas) {
        this.indexname = indexname;
        if ( !StringUtils.isEmpty(indextype) ) {
            this.indextype = indextype;
        }
        this.numberOfShards = numberOfShards;
        this.numberOfReplicas = numberOfReplicas;
    }
}
