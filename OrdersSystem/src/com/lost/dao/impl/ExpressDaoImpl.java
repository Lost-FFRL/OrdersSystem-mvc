package com.lost.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.lost.bean.Express;
import com.lost.dao.ExpressDao;
import com.lost.util.Constants;
import com.lost.util.Utils;
import com.lost.vo.ExpressVo;

@Repository("expressDao")
public class ExpressDaoImpl extends BaseDaoAbstract implements ExpressDao {
    
    private static final Logger LOG = Logger.getLogger(ExpressDaoImpl.class);
    
    private String expressTable = "os_express";
    
    @Override
    public boolean add(List<Express> express) {
        return false;
    }
    
    @Override
    public boolean add(ExpressVo vo) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ")
            .append(expressTable)
            .append("(NAME, `desc`,phone, mobile, address, reputation, price, STATUS, createDate, updateDate, remark)")
            .append(" VALUES(?,?,?,?,?,?,?,1,NOW(),NOW(),?)");
        return 1 == getJdbcTemplate().update(sql.toString(),
            vo.getName(),
            vo.getDesc(),
            vo.getPhone(),
            vo.getMobile(),
            vo.getAddress(),
            vo.getReputation(),
            vo.getPrice(),
            vo.getRemark());
    }
    
    @Override
    public Express getById(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,name,`desc`,phone,mobile,address,reputation,price,remark, ")
            .append("DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + expressTable + " where ")
            .append(" id = ? and status <> ?");
        return getJdbcTemplate().queryForObject(sql.toString(), getExpressMapper(), id, Constants.STATUS_DELETE);
    }
    
    @Override
    public boolean update(ExpressVo vo) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ")
            .append(expressTable)
            .append(" set name = ?,`desc`=?,phone=? , mobile=?,address=?, reputation=? , price =?, updateDate=NOW(), remark =?")
            .append(" where id=?");
        return 1 == getJdbcTemplate().update(sql.toString(),
            vo.getName(),
            vo.getDesc(),
            vo.getPhone(),
            vo.getMobile(),
            vo.getAddress(),
            vo.getReputation(),
            vo.getPrice(),
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
    
    /**
     * 根据ID删除记录
     * 
     * @param id
     * @return
     */
    public boolean delById(String id) {
        String sql = "UPDATE " + expressTable + " set status = ? where id = ? ";
        return 1 == getJdbcTemplate().update(sql, Constants.STATUS_DELETE, id);
    }
    
    @Override
    public int queryTotal(ExpressVo vo) {
        if (null == vo) {
            LOG.info("Express query param is null !");
            return 0;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select count(id) from ")
            .append(expressTable)
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
    public List<Express> query(ExpressVo vo) {
        if (null == vo) {
            LOG.info("Express query param is null !");
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select id,name,`desc`,phone,mobile,address,reputation,price,remark, ")
            .append("DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + expressTable + " where ")
            .append(" name like ? and mobile like ? and phone like ? and status <> ?")
            .append(" order by createDate desc")
            .append(" limit ?,?");
        String name = Utils.completeMatch(vo.getName());
        String mobile = Utils.completeMatch(vo.getMobile());
        String phone = Utils.completeMatch(vo.getPhone());
        return getJdbcTemplate().query(sql.toString(),
            getExpressMapper(),
            name,
            mobile,
            phone,
            Constants.STATUS_DELETE,
            vo.getLimitStart(),
            vo.getLimitEnd());
    }
    
    @Override
    public boolean isExists(Express express) {
        return false;
    }
    
    /**
     * 获取搜索返回对象
     * 
     * @return
     */
    private ParameterizedRowMapper<Express> getExpressMapper() {
        return new ParameterizedRowMapper<Express>() {
            @Override
            public Express mapRow(ResultSet rs, int arg1)
                throws SQLException {
                Express bean = new Express();
                bean.setId(rs.getInt("id"));
                bean.setName(rs.getString("name"));
                bean.setDesc(rs.getString("desc"));
                bean.setPhone(rs.getString("phone"));
                bean.setMobile(rs.getString("mobile"));
                bean.setAddress(rs.getString("address"));
                bean.setReputation(rs.getInt("reputation"));
                bean.setPrice(rs.getFloat("price"));
                bean.setUpdateDate(rs.getString("ud"));
                bean.setCreateDate(rs.getString("cd"));
                bean.setRemark(rs.getString("remark"));
                return bean;
            }
        };
    }
    
    @Override
    public List<Express> getDisplayName() {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,name from ").append(expressTable).append(" where status <> ?");
        return getJdbcTemplate().query(sql.toString(), getExpressNameMapper(), Constants.STATUS_DELETE);
    }
    
    /**
     * 获取搜索返回对象
     * 
     * @return
     */
    private ParameterizedRowMapper<Express> getExpressNameMapper() {
        return new ParameterizedRowMapper<Express>() {
            @Override
            public Express mapRow(ResultSet rs, int arg1)
                throws SQLException {
                Express bean = new Express();
                bean.setId(rs.getInt("id"));
                bean.setName(rs.getString("name"));
                return bean;
            }
        };
    }
}
