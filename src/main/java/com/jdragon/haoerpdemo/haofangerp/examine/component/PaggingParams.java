package com.jdragon.haoerpdemo.haofangerp.examine.component;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 19:00
 */
public class PaggingParams {
    @ApiModelProperty(example = "1")
    private long pageNum;//当前页数
    @ApiModelProperty(example = "5")
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
