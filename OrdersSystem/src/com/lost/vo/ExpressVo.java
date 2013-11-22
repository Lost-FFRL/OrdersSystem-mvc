package com.lost.vo;

/**
 * 快递
 * @author 张培军
 *
 */
public class ExpressVo extends PageVo
{
    /**
     * 数据唯一标识
     */
    private int id;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 描述
     */
    private String desc;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 手机号码
     */
    private String mobile;
    
    /**
     * 联系地址
     */
    private String address;
    
    /**
     * 信誉
     */
    private int reputation;
    
    /**
     * 运费
     */
    private float price;
    
    /**
     * 邮编
     */
    private int position;
    
    /**
     * 状态<br>
     * 0.数据删除<br>
     * 1.可用<br>
     * 2.不可用
     */
    private int status;
    
    /**
     * 数据创建时间
     */
    private String createDate;
    
    /**
     * 数据更新时间
     */
    private String updateDate;
    
    /**
     * 备注
     */
    private String remark;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public int getReputation()
    {
        return reputation;
    }

    public void setReputation(int reputation)
    {
        this.reputation = reputation;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
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

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
