package com.lost.dao;

import java.util.List;

import com.lost.bean.Buyer;
import com.lost.vo.BuyerVo;



public interface BuyerDao
{
    /**
     * 新增
     * @return
     */
    boolean add(List<Buyer> buyers);
    
    /**
     * 新增
     * @param vo
     * @return
     */
    boolean add(BuyerVo vo);
    
    /**
     * 更新
     * @param obj
     */
    boolean update(BuyerVo vo);
    
    
    boolean delById(String id);
    
    /**
     * 删除
     * @return
     */
    boolean delete(List<String> ids);
    
    /**
     * 删除
     * @param ids
     * @return
     */
    boolean delete(String ids);
    
    /**
     * 根据ID获取数据
     * @param id
     * @return
     */
    Buyer getById(String id);
    
    /**
     * 获取查询总量
     * @param vo
     * @return
     */
    int queryTotal(BuyerVo vo);
    
    /**
     * 查询
     * @param obj
     * @return
     */
    List<Buyer> query(BuyerVo vo);
    
    /**
     * 查询
     * @param obj
     * @return
     */
    List<Buyer> query(Buyer buyer);
    
    /**
     * 检测是否存在
     * @param user
     * @return
     */
    boolean isExists(Buyer buyer);
}
