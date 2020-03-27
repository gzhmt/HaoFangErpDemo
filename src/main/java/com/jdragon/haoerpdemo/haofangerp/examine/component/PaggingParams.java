package com.jdragon.haoerpdemo.haofangerp.examine.component;

/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 19:00
 */
public class PaggingParams {
    private long pageNum;//当前页数
    private long pageSize;//每页条数
    public PaggingParams(){}
    public PaggingParams(long pageNum,long pageSize){
        this.pageNum=pageNum;
        this.pageSize=pageSize;
    }


    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
