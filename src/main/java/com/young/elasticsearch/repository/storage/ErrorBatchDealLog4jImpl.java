package com.young.elasticsearch.repository.storage;

import lombok.extern.log4j.Log4j2;

import java.util.List;

/*************************
 * @author: YoungLu
 * @date: 2019/12/19 15:38
 * @description: elasticsearch错误批次log4j处理类
 **************************/
@Log4j2
public class ErrorBatchDealLog4jImpl<T> implements IErrorBatchDeal<T> {
    @Override
    public void deal(List<T> errorList) {
        log.error("错误批次未能成功保存的日志为:",errorList.toString());
    }
}
