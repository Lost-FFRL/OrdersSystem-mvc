package com.lost.server.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lost.bean.PayType;
import com.lost.dao.PayTypeDao;
import com.lost.server.PayTypeService;
import com.lost.vo.PayTypeVo;

@Service("payTypeService")
public class PayTypeServiceImpl implements PayTypeService
{
//  private final Logger LOG = Logger.getLogger(PayTypeServiceImpl.class);

    @Resource
    PayTypeDao payTypeDao;
    
    @Override
    public boolean add(PayTypeVo vo) {
        return payTypeDao.add(vo);
    }

    @Override
    public boolean update(PayTypeVo vo) {
        return payTypeDao.update(vo);
    }

    @Override
    public boolean delById(String id) {
        return payTypeDao.delById(id);
    }

    @Override
    public PayType getById(String id) {
        return payTypeDao.getById(id);
    }

    @Override
    public int queryTotal(PayTypeVo vo) {
        return payTypeDao.queryTotal(vo);
    }

    @Override
    public List<PayType> query(PayTypeVo vo) {
        return payTypeDao.query(vo);
    }
    
    /**
     * 获取显示名称
     * @return
     */
    public List<PayType> getDisplayName(){
        return payTypeDao.getDisplayName();
    }
}
