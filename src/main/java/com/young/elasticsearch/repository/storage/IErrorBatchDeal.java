package com.young.elasticsearch.repository.storage;

import java.util.List;

/*************************
 * @author: YoungLu
 * @date: 2019/12/19 15:35
 * @description: 错误批次处理类
 **************************/
public interface IErrorBatchDeal<T> {
    /**
     * 处理出错的批次
     * @param errorList 错误批次集合
     */
    void deal(List<T> errorList);
}
