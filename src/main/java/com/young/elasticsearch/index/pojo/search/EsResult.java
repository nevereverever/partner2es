package com.young.elasticsearch.index.pojo.search;

import lombok.Data;

import java.util.List;

/*************************
 * @author: YoungLu
 * @date: 2019/12/20 17:09
 * @description: elasticsearch分页查询结果对象
 **************************/
@Data
public class EsResult<T> {
    //查询结果
    private List<T> resultList;
    //分页对象
    private EsPage esPage;
    //排序值（主要用于search_after,一种应对深度翻页的东西）
    private Object[] sortValues;
}
