package com.lost.dao;

import java.util.List;

import com.lost.bean.Product;
import com.lost.vo.OrdersVo;
import com.lost.vo.ProductVo;

public interface ProductDao {
    
    /**
     * 新增
     * 
     * @param vo
     * @return
     */
    boolean add(ProductVo vo);
    
    boolean addOrdersProduct(OrdersVo vo);
    
    /**
     * 更新
     * 
     * @param obj
     */
    boolean update(ProductVo vo);
    
    /**
     * 根据ID删除数据
     * 
     * @return
     */
    boolean delById(String id);
    
    /**
     * 根据ID获取数据
     * 
     * @param id
     * @return
     */
    Product getById(String id);
    
    /**
     * 获取查询总量
     * 
     * @param vo
     * @return
     */
    int queryTotal(ProductVo vo);
    
    /**
     * 查询
     * 
     * @param obj
     * @return
     */
    List<Product> query(ProductVo vo);
    
    List<Product> query();
}
