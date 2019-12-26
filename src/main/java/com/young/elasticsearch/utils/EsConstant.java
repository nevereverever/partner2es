package com.young.elasticsearch.utils;

/*************************
 * @author: YoungLu
 * @date: 2019/12/19 09:47
 * @description: 常量类
 **************************/
public class EsConstant {
    //不分页,默认查询条数
    public static final int NO_PAGING_DEFALT_SIZE = 200;
    //搜索建议默认条数
    public static final int COMPLETION_SUGGESTION_SIZE = 10;
    //创建索引mapping时，是否默认创建keyword
    public static final boolean DEFAULT_CREATE_KEYWORDS = true;
    //高亮字段默认tag
    public static final String DEFAULT_HIGHLIGHT_TAG = "";
    //elasticsearch默认集群名称
    public static final String CLUSTER_NAME = "powercloud_log";
    //elasticsearch默认服务器
    public static final String DEFAULT_ES_HOST = "127.0.0.1:9200";
    //SCROLL查询 2（小时）
    public static final long DEFAULT_SCROLL_TIME = 2;
    //SCROLL查询 每页默认条数
    public static final int DEFAULT_SCROLL_PERPAGE = 100;
    //批量新增每批次条数
    public static final int BULK_SAVE_COUNT = 5000;
    //删除时批次大小,默认1000条
    public static final int DELETE_BATCH_SIZE = 1000;
    //聚合查询返回最大条数
    public static final int AGG_RESULT_COUNT = Integer.MAX_VALUE;
    //elasticsearch设置的最大查询窗口大小
    public static final int MAX_RESULT_WINDOW = 10000;
 }
