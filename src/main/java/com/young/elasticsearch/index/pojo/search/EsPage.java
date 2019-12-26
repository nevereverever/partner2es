package com.young.elasticsearch.index.pojo.search;

import com.young.elasticsearch.utils.EsConstant;
import java.io.Serializable;

/*************************
 * @author: YoungLu
 * @date: 2019/12/20 16:38
 * @description: elasticsearch分页对象
 **************************/
public class EsPage implements Serializable {
    //文档总数(有时可能是虚假的,不可超过EsConstant.MAX_RESULT_WINDOW)
    private int totalResultCount;
    //实际文档总数
    private long actualResultCount;
    //总页数
    private int totalPage;
    //当前页码
    private int currentPage;
    //页码大小
    private int pageSize;
    //当前索引位置
    private int currentStartIndex;

    public int getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(int totalResultCount) {
        //查询总数不能超过EsConstant.MAX_RESULT_WINDOW
        this.totalResultCount = (totalResultCount > EsConstant.MAX_RESULT_WINDOW) ? EsConstant.MAX_RESULT_WINDOW : totalResultCount;
        if( this.totalResultCount % pageSize == 0 )
            totalPage = this.totalResultCount / pageSize;
        else
            totalPage = this.totalResultCount / pageSize + 1;
    }

    public long getActualResultCount() {
        return actualResultCount;
    }

    public void setActualResultCount(long actualResultCount) {
        this.actualResultCount = actualResultCount;
        int totalResultCount = 0;
        if ( actualResultCount > Integer.MAX_VALUE ){
            totalResultCount = Integer.MAX_VALUE;
        } else {
            totalResultCount = (int) actualResultCount;
        }
        setTotalResultCount(totalResultCount);
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        if(currentPage<=0)
            currentPage = 1;
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentStartIndex() {
        currentStartIndex = (getCurrentPage() - 1) * getPageSize();
        if(currentStartIndex < 0)
            currentStartIndex = 0;
        return currentStartIndex;
    }

    public void setCurrentStartIndex(int currentIndex) {
        this.currentStartIndex = currentIndex;
    }
}
