package com.lost.server.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lost.bean.Buyer;
import com.lost.dao.BuyerDao;
import com.lost.server.BuyerService;
import com.lost.vo.BuyerVo;

@Service("buyerService")
public class BuyerServiceImpl implements BuyerService {
//    private static final Logger LOG = Logger.getLogger(BuyerServiceImpl.class);
    
    @Resource
    BuyerDao buyerDao;
    
    @Override
    public boolean add(List<Buyer> buyers) {
        return buyerDao.add(buyers);
    }
    
    @Override
    public boolean add(BuyerVo vo) {
        return buyerDao.add(vo);
    }
    
    @Override
    public boolean update(BuyerVo vo) {
        return buyerDao.update(vo);
    }
    
    @Override
    public boolean delete(List<String> ids) {
        // TODO Auto-generated method stub
        return buyerDao.delete(ids);
    }
    
    @Override
    public boolean delete(String ids) {
        // TODO Auto-generated method stub
        return buyerDao.delete(ids);
    }
    
    @Override
    public List<Buyer> query(Buyer buyer) {
        // TODO Auto-generated method stub
        return buyerDao.query(buyer);
    }
    
    @Override
    public boolean isExists(Buyer buyer) {
        // TODO Auto-generated method stub
        return buyerDao.isExists(buyer);
    }
    
    @Override
    public boolean delById(String id) {
        return buyerDao.delById(id);
    }
    
    @Override
    public int queryTotal(BuyerVo vo) {
        return buyerDao.queryTotal(vo);
    }
    
    @Override
    public List<Buyer> query(BuyerVo vo) {
        return buyerDao.query(vo);
    }

    @Override
    public Buyer getById(String id) {
        return buyerDao.getById(id);
    }
    
}
