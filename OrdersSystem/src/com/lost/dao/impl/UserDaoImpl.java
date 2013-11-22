package com.lost.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.lost.bean.User;
import com.lost.dao.UserDao;
import com.lost.util.Constants;
import com.lost.util.Utils;
import com.lost.vo.UserVo;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoAbstract implements UserDao {
    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);
    
    private String userTable = "os_user";
    
    @Override
    public boolean add(List<User> users) {
        return false;
    }
    
    @Override
    public boolean add(UserVo vo) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ")
            .append(userTable)
            .append("(NAME,sex,password,account, `desc`,phone, mobile, address, STATUS, createDate, updateDate, remark)")
            .append(" VALUES(?,?,?,?,?,?,?,?,1,NOW(),NOW(),?)");
        return 1 == getJdbcTemplate().update(sql.toString(),
            vo.getName(),
            vo.getSex(),
            vo.getPassword(),
            vo.getAccount(),
            vo.getDesc(),
            vo.getPhone(),
            vo.getMobile(),
            vo.getAddress(),
            vo.getRemark());
    }
    
    @Override
    public boolean update(UserVo vo) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ")
            .append(userTable)
            .append(" set password = ?,name = ?,account=?,sex=?,`desc`=?,phone=? , mobile=?,address=?, updateDate=NOW(), remark =?")
            .append(" where id=?");
        return 1 == getJdbcTemplate().update(sql.toString(),
            vo.getPassword(),
            vo.getName(),
            vo.getAccount(),
            vo.getSex(),
            vo.getDesc(),
            vo.getPhone(),
            vo.getMobile(),
            vo.getAddress(),
            vo.getRemark(),
            vo.getId());
    }
    
    @Override
    public boolean delete(List<String> ids) {
        return false;
    }
    
    @Override
    public boolean delete(String ids) {
        return false;
    }
    
    @Override
    public boolean delById(String id) {
        String sql = "UPDATE " + userTable + " set status = ? where id = ? ";
        return 1 == getJdbcTemplate().update(sql, Constants.STATUS_DELETE, id);
    }
    
    @Override
    public User getById(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,account,num,password,name,sex,`desc`,phone,mobile,address,authority,remark, ")
            .append("DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + userTable + " where ")
            .append(" id = ? and status <> ?");
        return getJdbcTemplate().queryForObject(sql.toString(), getUserMapper(), id, Constants.STATUS_DELETE);
    }
    
    @Override
    public boolean delUpdateByIds(String ids) {
        return false;
    }
    
    @Override
    public int getCount(User user) {
        return 0;
    }
    
    @Override
    public int queryTotal(UserVo vo) {
        if (null == vo) {
            LOG.info("User query param is null !");
            return 0;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select count(id) from ")
            .append(userTable)
            .append(" where name like ? and mobile like ? and phone like ? and status <> ?");
        String name = Utils.completeMatch(vo.getName());
        String mobile = Utils.completeMatch(vo.getMobile());
        String phone = Utils.completeMatch(vo.getPhone());
        return getJdbcTemplate().queryForObject(sql.toString(),
            Integer.class,
            name,
            mobile,
            phone,
            Constants.STATUS_DELETE);
    }
    
    @Override
    public List<User> query(UserVo vo) {
        if (null == vo) {
            LOG.info("Express query param is null !");
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select id,num,account,password,name,sex,`desc`,phone,mobile,address,authority,remark, ")
            .append("DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + userTable + " where ")
            .append(" name like ? and mobile like ? and phone like ? and status <> ?")
            .append(" order by createDate desc")
            .append(" limit ?,?");
        String name = Utils.completeMatch(vo.getName());
        String mobile = Utils.completeMatch(vo.getMobile());
        String phone = Utils.completeMatch(vo.getPhone());
        return getJdbcTemplate().query(sql.toString(),
            getUserMapper(),
            name,
            mobile,
            phone,
            Constants.STATUS_DELETE,
            vo.getLimitStart(),
            vo.getLimitEnd());
    }
    
    @Override
    public boolean isExists(User user) {
        
        return false;
    }
    
    @Override
    public boolean checkUser(String account, String pwd) {
        if (Utils.isNotEmpty(account) && Utils.isNotEmpty(pwd)) {
            // statues： 1 代表在职
            StringBuffer sql = new StringBuffer();
            sql.append("select count(id) from ")
                .append(userTable)
                .append(" where account=? and password = ? and status=1 ");
            return (getJdbcTemplate().queryForObject(sql.toString(), Integer.class, account, pwd) > 0);
        } else {
            LOG.error("User checkUser param is null!");
            return false;
        }
    }
    
    /**
     * 获取搜索返回对象
     * 
     * @return
     */
    private ParameterizedRowMapper<User> getUserMapper() {
        return new ParameterizedRowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int arg1)
                throws SQLException {
                User userBean = new User();
                userBean.setId(rs.getInt("id"));
                userBean.setNumber(rs.getString("num"));
                userBean.setName(rs.getString("name"));
                userBean.setDesc(rs.getString("desc"));
                userBean.setSex(rs.getInt("sex"));
                userBean.setPhone(rs.getString("phone"));
                userBean.setMobile(rs.getString("mobile"));
                userBean.setAccount(rs.getString("account"));
                userBean.setAddress(rs.getString("address"));
                userBean.setAuthority(rs.getInt("authority"));
                userBean.setRemark(rs.getString("remark"));
                userBean.setPassword(rs.getString("password"));
                userBean.setUpdateDate(rs.getString("ud"));
                return userBean;
            }
        };
    }
}
