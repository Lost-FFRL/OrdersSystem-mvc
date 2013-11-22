package com.lost.vo;


public abstract class PageVo {
    
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
    private int pageSize = 10;
    
    /**
     * 总数
     */
    private int total;
    
    private int limitStart;
    
    private int limitEnd;
    
    public int getLimitStart(){
        limitStart = (curPage-1) * pageSize;
        limitStart = limitStart < 0 ? 0 : limitStart;
        return limitStart;
    }
    
    public int getLimitEnd(){
        limitEnd = pageSize <= 0 ? 10 : pageSize;
        return limitEnd;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage <= 0 ? 1 : curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
    }
}
