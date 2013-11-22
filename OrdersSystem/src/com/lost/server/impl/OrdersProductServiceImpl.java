package com.lost.server.impl;

import java.util.List;


import org.apache.log4j.Logger;

import com.lost.bean.OrdersProduct;
import com.lost.dao.OrdersProductDao;
import com.lost.dao.impl.OrdersProductDaoImpl;
import com.lost.server.OrdersProductService;


public class OrdersProductServiceImpl implements OrdersProductService
{
    private final Logger LOG = Logger.getLogger(OrdersProductServiceImpl.class);
    
    private OrdersProductDao opDao;

    @Override
    public boolean add(List<OrdersProduct> ordersProducts) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean update(OrdersProduct ordersProduct) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(List<String> ids) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(String ids) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delByOrdersId(String ids) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<OrdersProduct> query(OrdersProduct ordersProduct) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isExists(OrdersProduct ordersProduct) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
