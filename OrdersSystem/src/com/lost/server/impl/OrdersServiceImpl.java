package com.lost.server.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lost.bean.Orders;
import com.lost.bean.OrdersProduct;
import com.lost.dao.OrdersDao;
import com.lost.server.OrdersService;
import com.lost.vo.OrdersVo;

@Transactional
@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {
    //    private final Logger LOG = Logger.getLogger(OrdersServiceImpl.class);

    @Resource
    OrdersDao ordersDao;

    @Override
    public boolean add(OrdersVo vo) {
        return ordersDao.add(vo);
    }

    @Override
    public boolean addOrdersProduct(List<OrdersProduct> ops) {
        return ordersDao.addOrdersProduct(ops);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean update(OrdersVo vo) {
        boolean flag = false;
        if (null != vo.getProductId() && vo.getProductId().length > 0) {
            String[] proId = vo.getProductId();
            int size = proId.length;
            List<OrdersProduct> addList = null;
            List<OrdersProduct> updateList = null;
            addList = new ArrayList<OrdersProduct>(size);
            updateList = new ArrayList<OrdersProduct>(size);
            for (int i = 0; i < size; i++) {
                OrdersProduct bean = new OrdersProduct();
                bean.setId(Integer.valueOf(vo.getOpId()[i]));
                bean.setOrdersId(vo.getId());
                bean.setOrdersNum(vo.getOrdersNum());
                bean.setProId(vo.getProductId()[i]);
                bean.setProName(vo.getProductName()[i]);
                bean.setProNum(vo.getProductNum()[i]);
                bean.setProDesc(vo.getProductDesc()[i]);
                bean.setProPrice(Float.valueOf(vo.getProductPrice()[i]));
                bean.setProCount(Float.valueOf(vo.getProductCount()[i]));
                // id = 0 表示新增数据
                if ("0".equals(vo.getOpId()[i])) {
                    addList.add(bean);
                } else {
                    updateList.add(bean);
                }
            }
            if (null != addList && addList.size() > 0) {
                flag = ordersDao.addOrdersProduct(addList);
                if (!flag) {
                    return flag;
                }
            }
            if (null != updateList && updateList.size() > 0) {
                flag = ordersDao.updateOrdersProduct(updateList);
                if (!flag) {
                    return flag;
                }
            }
        }
        flag = ordersDao.update(vo);
        return flag;
    }

    public boolean updateOrdersProduct(List<OrdersProduct> ops) {
        return ordersDao.updateOrdersProduct(ops);
    }

    @Override
    public boolean delById(String id) {
        return ordersDao.delById(id);
    }

    @Override
    public boolean delOPById(String oid) {
        return ordersDao.delOPById(oid);
    }

    @Override
    public Orders getById(String id) {
        return ordersDao.getById(id);
    }

    @Override
    public int queryTotal(OrdersVo vo) {
        return ordersDao.queryTotal(vo);
    }

    @Override
    public List<Orders> query(OrdersVo vo) {
        return ordersDao.query(vo);
    }

}
