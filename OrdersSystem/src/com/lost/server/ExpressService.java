package com.lost.server;


import java.util.List;

import com.lost.bean.Express;
import com.lost.vo.ExpressVo;



public interface ExpressService
{
    /**
     * 新增
     * @return
     */
    boolean add(List<Express> express);
    
    /**
     * 新增
     * 
     * @param vo
     * @return
     */
    boolean add(ExpressVo vo);
    
    /**
     * 根据ID获取记录
     * 
     * @param id
     * @return
     */
    Express getById(String id);
    
    /**
     * 获取快递的显示名称
     * @return
     */
    List<Express> getDisplayName();
    
    /**
     * 更新
     * @param obj
     */
    boolean update(ExpressVo vo);
    
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
     * 根据ID删除记录
     * @param id
     * @return
     */
    boolean delById(String id);
    
    int queryTotal(ExpressVo vo);
    
    /**
     * 查询
     * @param obj
     * @return
     */
    List<Express> query(ExpressVo vo);
    
    /**
     * 检测是否存在
     * @param user
     * @return
     */
    boolean isExists(Express express);
}
