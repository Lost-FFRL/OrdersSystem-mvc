package com.lost.server.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lost.bean.Express;
import com.lost.dao.ExpressDao;
import com.lost.server.ExpressService;
import com.lost.vo.ExpressVo;

@Service("expressService")
public class ExpressServiceImpl implements ExpressService {
//    private final Logger LOG = Logger.getLogger(ExpressServiceImpl.class);

    @Resource
    ExpressDao expressDao;

    @Override
    public boolean add(List<Express> express) {
        // TODO Auto-generated method stub
        return expressDao.add(express);
    }
    
    @Override
    public boolean add(ExpressVo vo){
        return expressDao.add(vo);
    }
   
    @Override
    public Express getById(String id){
        return expressDao.getById(id);
    }

    @Override
    public boolean update(ExpressVo vo) {
        // TODO Auto-generated method stub
        return expressDao.update(vo);
    }

    @Override
    public boolean delete(List<String> ids) {
        // TODO Auto-generated method stub
        return expressDao.delete(ids);
    }

    @Override
    public boolean delete(String ids) {
        // TODO Auto-generated method stub
        return expressDao.delete(ids);
    }
    
    @Override
    public boolean delById(String id){
        return expressDao.delById(id);
    }

    @Override
    public List<Express> query(ExpressVo vo) {
        return expressDao.query(vo);
    }

    @Override
    public boolean isExists(Express express) {
        // TODO Auto-generated method stub
        return expressDao.isExists(express);
    }

    @Override
    public int queryTotal(ExpressVo vo) {
        return expressDao.queryTotal(vo);
    }

    @Override
    public List<Express> getDisplayName() {
        return expressDao.getDisplayName();
    }

}
