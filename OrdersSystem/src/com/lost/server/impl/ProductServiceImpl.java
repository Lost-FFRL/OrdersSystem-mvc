package com.lost.server.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lost.bean.Product;
import com.lost.dao.ProductDao;
import com.lost.server.ProductService;
import com.lost.vo.ProductVo;

@Service("productService")
public class ProductServiceImpl implements ProductService {
//    private final Logger LOG = Logger.getLogger(ProductServiceImpl.class);
    
    @Resource
    ProductDao productDao;
    
    @Override
    public boolean add(ProductVo vo) {
        return productDao.add(vo);
    }
    
    @Override
    public boolean update(ProductVo vo) {
        return productDao.update(vo);
    }
    
    @Override
    public boolean delById(String id) {
        return productDao.delById(id);
    }
    
    @Override
    public Product getById(String id) {
        return productDao.getById(id);
    }
    
    @Override
    public int queryTotal(ProductVo vo) {
        return productDao.queryTotal(vo);
    }
    
    @Override
    public List<Product> query(ProductVo vo) {
        return productDao.query(vo);
    }

    @Override
    public List<Product> query() {
        return productDao.query();
    }
    
}
