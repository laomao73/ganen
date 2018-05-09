package com.ganen.util;

import java.io.Serializable;

public class Page implements Serializable {
    //当前页数
    private int pageNow;
    //每页条数
    private int pageSize;
    //总条数
    private int totalCount;
    //总页数
    private int totalPageCount;
    //开始位置
    @SuppressWarnings("unused")
    private int startPos;


    @Override
    public String toString() {
        return "Page{" +
                "pageNow=" + pageNow +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPageCount=" + totalPageCount +
                ", startPos=" + startPos +
                '}';
    }

    public Page(int pageSize, int totalCount) {
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPageCount = getTotalCount() / getPageSize();
        this.totalPageCount=(totalCount % pageSize == 0) ? totalPageCount : totalPageCount + 1;
        if(this.totalPageCount==0){
            this.totalPageCount=1;
        }
    }


    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        if (pageNow > totalPageCount) {
            pageNow = totalPageCount;
        }
        this.pageNow = pageNow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getStartPos() {
        if (totalCount < pageSize) {
            return 0;
        }
        return (pageNow - 1) * pageSize;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }
}
