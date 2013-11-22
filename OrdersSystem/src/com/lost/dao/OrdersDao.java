package com.lost.dao;

import java.util.List;

import com.lost.bean.Orders;
import com.lost.bean.OrdersProduct;
import com.lost.vo.OrdersVo;

public interface OrdersDao {

    /**
     * 新增
     * 
     * @param vo
     * @return
     */
    boolean add(OrdersVo vo);
    
    /**
     * 新增商品
     * 
     * @param ops
     * @return
     */
    boolean addOrdersProduct(List<OrdersProduct> ops);
    
    /**
     * 更新
     * 
     * @param obj
     */
    boolean update(OrdersVo vo);
    
    boolean updateOrdersProduct(List<OrdersProduct> ops);
    
    /**
     * 根据ID删除数据
     * 
     * @return
     */
    boolean delById(String id);
    
    boolean delOPById(String id);
    
    /**
     * 根据ID获取数据
     * 
     * @param id
     * @return
     */
    Orders getById(String id);
    
    /**
     * 获取查询总量
     * 
     * @param vo
     * @return
     */
    int queryTotal(OrdersVo vo);
    
    /**
     * 查询
     * 
     * @param obj
     * @return
     */
    List<Orders> query(OrdersVo vo);

}
