package com.jdragon.haoerpdemo.haofangerp.examine.domain.vo;

import java.util.List;

/**
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/28 17:53
 */
public class ResponseVo<T> {
    private List<T> data;
    private long total;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
