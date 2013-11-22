package com.lost.vo;

import java.util.List;

public class Page<T>
{
    /**
     * 总页数
     */
    private int totalPage;
    
    /**
     * 当前页数
     */
    private int curPage;
    
    /**
     * 每页数量
     */
    private int pageSize;
    
    /**
     * 总数
     */
    private int total;
    
    /**
     * 分页查询结果
     */
    private Object result;
    
    private List<T> list;
    
    
    public Page()
    {
        // 设置默认值
        curPage = 1;
        pageSize = 10;
    }
    
    public Page(int curPage,int pageSize,int total,List<T> list){
        this.total = total;
        if (0 == this.total){
            this.curPage = 0;
        } else {
            this.curPage = curPage <= 0 ? 1 : curPage;
        }
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
        int mod = total / this.pageSize;
        this.totalPage = (total % pageSize) > 0 ? mod + 1 : mod;
        this.list = list;
    }
    
    public int getTotalPage()
    {
        return totalPage;
    }
    
    public void setTotalPage(int totalPage)
    {
        this.totalPage = totalPage;
    }
    
    public int getCurPage()
    {
        return curPage;
    }
    
    public void setCurPage(int curPage)
    {
        this.curPage = curPage;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getTotal()
    {
        return total;
    }
    
    public void setTotal(int total)
    {
        this.total = total;
    }
    
    public Object getResult()
    {
        return result;
    }
    
    public void setResult(Object result)
    {
        this.result = result;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
