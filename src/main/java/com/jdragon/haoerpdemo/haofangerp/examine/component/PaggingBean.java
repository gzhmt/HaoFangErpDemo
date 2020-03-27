package com.jdragon.haoerpdemo.haofangerp.examine.component;


import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.PageSizeException;
import com.jdragon.haoerpdemo.haofangerp.examine.component.exceptions.TotalException;

/**
 *
 * 根据传入的Pagging对象计算offset(起始索引)、end(尾索引)、lastPageCount(尾页记录数)
 *
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 18:57
 */
public class PaggingBean {
    private long pageNum;//当前页码
    private long pageSize;//每页记录
    private long total;//总记录数
    private long totalPage;//总页数

    /**
     *
     * @param paggingParams 封装了分页基本参数的参数对象
     * @param total 总记录数
     * @throws PageSizeException 每页记录数错误则抛出异常
     * @throws TotalException 总记录数错误则抛出异常
     */
    public PaggingBean(final PaggingParams paggingParams,long total) throws PageSizeException, TotalException {
        this.pageNum=paggingParams.getPageNum();
        this.pageSize=paggingParams.getPageSize();
        this.total=total;
        dected();
    }

    /**
     *
     * @return 返回起始索引(下标从0开始)
     */
    public long getOffset(){
        return (pageNum-1)*pageSize;
    }

    /**
     *
     * @return 返回尾索引(下标从0开始)
     */
    public long getEnd(){
        //是否为最后一页
        if(pageNum==totalPage){
            return total-1;
        }else{
            return pageNum*pageSize-1;
        }
    }

    /**
     *
     * 主要针对最后一页时的记录数
     * @return 返回经过处理的指定页面大小参数
     */
    public long getCorrectPageSize(){
        if(pageNum<totalPage){
            return pageSize;
        }else{
            return total-(pageNum-1)*pageSize;
        }
    }
    /**
     * 初始化总页数
     */
    private void initTotalPage(){
        long temp=total/pageSize;
        boolean isExact=total%pageSize==0;
        if(isExact){
            totalPage=temp;
        }else{
            totalPage=temp+1;
        }
    }

    /**
     * 检测与纠正错误参数
     */
    private void dected() throws PageSizeException,TotalException{
        initTotalPage();
        if(pageNum<=0){
            pageNum=1;
        }else if(pageNum>totalPage){
            pageNum=totalPage;
        }
        if(pageSize<=0){
            throw new PageSizeException("页记录数必须大于0");
        }
        if(total<0){
            throw new TotalException("总记录数不能小于0");
        }
    }

}
