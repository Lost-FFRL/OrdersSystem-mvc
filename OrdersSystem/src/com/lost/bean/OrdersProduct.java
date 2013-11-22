package com.lost.bean;

/**
 * 订单商品
 * @author 张培军
 *
 */
public class OrdersProduct
{
    /**
     * 数据唯一标识
     */
    private int id;
    
    /**
     * 订单id
     */
    private int ordersId;
    
    /**订单名称*/
    private String name;
    
    /**
     * 订单编号
     */
    private String ordersNum;
    
    private String proId;
    
    private String proNum;
    
    private String proName;
    
    private String proDesc;
    
    private float proPrice;
    
    private float proCount;

    /**
     * 商品
     */
    private Product product;
    
    /**
     * 总价
     */
    private float totalPice;
    
    /**
     * 折扣
     */
    private float discount;
    
    /**
     * 总折后价格
     */
    private float totalDisPrice;
    
    /**
     * 总数
     */
    private float totalCount;
    
    /**
     * 数据创建时间
     */
    private String createDate;

    /**
     * 数据更新时间
     */
    private String updateDate;
    
    /**
     * 数据状态<br>
     * 0.数据删除<br>
     * 1.已选择商品
     */
    private int status;
    
    /**
     * 备注
     */
    private String remark;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProNum() {
        return proNum;
    }

    public void setProNum(String proNum) {
        this.proNum = proNum;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }

    public float getProPrice() {
        return proPrice;
    }

    public void setProPrice(float proPrice) {
        this.proPrice = proPrice;
    }

    public float getProCount() {
        return proCount;
    }

    public void setProCount(float proCount) {
        this.proCount = proCount;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getOrdersId()
    {
        return ordersId;
    }

    public void setOrdersId(int ordersId)
    {
        this.ordersId = ordersId;
    }

    public String getOrdersNum()
    {
        return ordersNum;
    }

    public void setOrdersNum(String ordersNum)
    {
        this.ordersNum = ordersNum;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public float getTotalPice()
    {
        return totalPice;
    }

    public void setTotalPice(float totalPice)
    {
        this.totalPice = totalPice;
    }

    public float getDiscount()
    {
        return discount;
    }

    public void setDiscount(float discount)
    {
        this.discount = discount;
    }

    public float getTotalDisPrice()
    {
        return totalDisPrice;
    }

    public void setTotalDisPrice(float totalDisPrice)
    {
        this.totalDisPrice = totalDisPrice;
    }

    public float getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(float totalCount)
    {
        this.totalCount = totalCount;
    }

    public String getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    public String getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
