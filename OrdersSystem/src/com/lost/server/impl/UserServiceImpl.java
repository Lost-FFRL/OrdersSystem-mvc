package com.lost.server.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.lost.bean.User;
import com.lost.dao.UserDao;
import com.lost.server.UserService;
import com.lost.vo.UserVo;


@Service("userService")
public class UserServiceImpl implements UserService {
	// private final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    @Resource
	UserDao userDao;

	@Override
	public boolean add(List<User> users) {
		return userDao.add(users);
	}

	@Override
	public boolean add(UserVo vo) {
		return userDao.add(vo);
	}

	@Override
	public boolean update(UserVo vo) {
		return userDao.update(vo);
	}

	@Override
	public boolean delete(List<String> ids) {
		return userDao.delete(ids);
	}

	@Override
	public boolean delete(String ids) {
		return userDao.delete(ids);
	}

	@Override
	public boolean delUpdateByIds(String ids) {
		return userDao.delUpdateByIds(ids);
	}

	/**
	 * 获取总量
	 */
	public int getCount(User user) {
		return userDao.getCount(user);
	}

	@Override
	public List<User> query(UserVo vo) {
		return userDao.query(vo);
	}

	@Override
	public boolean isExists(User user) {
		return userDao.isExists(user);
	}

	/**
	 * 检查用户名密码是否正确
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	public boolean checkUser(String account, String pwd) {
		return userDao.checkUser(account, pwd);
	}

    @Override
    public boolean delById(String id) {
        return userDao.delById(id);
    }

    @Override
    public int queryTotal(UserVo vo) {
        return userDao.queryTotal(vo);
    }

    @Override
    public User getById(String id) {
        return userDao.getById(id);
    }

}
