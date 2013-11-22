package com.lost.dao;

import java.util.List;

import com.lost.bean.User;
import com.lost.vo.UserVo;



public interface UserDao
{
    /**
     * 新增
     * @return
     */
    boolean add(List<User> users);
    
    /**
     * 新增用户
     * @param user
     * @return
     */
    boolean add(UserVo vo);
    
    /**
     * 更新
     * @param obj
     */
    boolean update(UserVo vo);
    
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
    
    boolean delById(String id);
    
    /**
     * 修改状态为删除
     * @return
     */
    boolean delUpdateByIds(String ids);
    
    User getById(String id);
    
    int getCount(User user);
    
    int queryTotal(UserVo vo);
    
    /**
     * 查询
     * @param obj
     * @return
     */
    List<User> query(UserVo vo);
    
    /**
     * 检测是否存在
     * @param user
     * @return
     */
    boolean isExists(User user);
    
    /**
     * 检查用户名密码是否正确
     * @param account
     * @param pwd
     * @return
     */
    boolean checkUser(String account, String pwd);
}
