package com.young.elasticsearch.index.pojo.search;

import com.young.elasticsearch.repository.search.highlight.pojo.Highlight;
import com.young.elasticsearch.repository.search.sort.pojo.Sort;
import lombok.Data;

import java.io.Serializable;

/*************************
 * @author: YoungLu
 * @date: 2019/12/23 10:48
 * @description: 查询的附加条件
 **************************/
@Data
public class Additions implements Serializable {
    //高亮信息
    private Highlight highlight;
    //排序字段
    private Sort[] sorts;
    //页码对象
    private EsPage esPage;
    //是否用searchAfter
    private boolean searchAfter = false;
    //路由键
    private String routing;
}
