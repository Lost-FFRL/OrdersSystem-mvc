package com.lost.server;

import java.util.List;

import com.lost.bean.PayType;
import com.lost.vo.PayTypeVo;


public interface PayTypeService
{
    /**
     * 新增
     * @param vo
     * @return
     */
    boolean add(PayTypeVo vo);
    
    /**
     * 更新
     * @param obj
     */
    boolean update(PayTypeVo vo);
    
    /**
     * 根据ID删除数据
     * @return
     */
    boolean delById(String id);
    
    
    /**
     * 根据ID获取数据
     * @param id
     * @return
     */
    PayType getById(String id);
    
    /**
     * 获取显示名称
     * @return
     */
    List<PayType> getDisplayName();
    
    /**
     * 获取查询总量
     * @param vo
     * @return
     */
    int queryTotal(PayTypeVo vo);
    
    /**
     * 查询
     * @param obj
     * @return
     */
    List<PayType> query(PayTypeVo vo);
}
