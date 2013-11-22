package com.lost.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.lost.bean.PayType;
import com.lost.dao.PayTypeDao;
import com.lost.util.Constants;
import com.lost.util.Utils;
import com.lost.vo.PayTypeVo;

@Repository("payTypeDao")
public class PayTypeDaoImpl extends BaseDaoAbstract implements PayTypeDao {
    
    private static final Logger LOG = Logger.getLogger(PayTypeDaoImpl.class);
    
    private String payTypeTable = "os_paytype";
    
    @Override
    public boolean add(PayTypeVo vo) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ")
            .append(payTypeTable)
            .append("(num,NAME,`desc`,STATUS, createDate, updateDate, remark)")
            .append(" VALUES(?,?,?,1,NOW(),NOW(),?)");
        return 1 == getJdbcTemplate().update(sql.toString(), vo.getNumber(), vo.getName(), vo.getDesc(), vo.getRemark());
    }
    
    @Override
    public boolean update(PayTypeVo vo) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ")
            .append(payTypeTable)
            .append(" set num = ?,name = ?,`desc`=?,updateDate=NOW(), remark =?")
            .append(" where id=?");
        return 1 == getJdbcTemplate().update(sql.toString(),
            vo.getNumber(),
            vo.getName(),
            vo.getDesc(),
            vo.getRemark(),
            vo.getId());
    }
    
    @Override
    public boolean delById(String id) {
        String sql = "UPDATE " + payTypeTable + " set status = ? where id = ? ";
        return 1 == getJdbcTemplate().update(sql, Constants.STATUS_DELETE, id);
    }
    
    @Override
    public PayType getById(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,num,name,`desc`,remark,status, ")
            .append("DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + payTypeTable + " where ")
            .append(" id = ? and status <> ?");
        return getJdbcTemplate().queryForObject(sql.toString(), getPayTypeMapper(), id, Constants.STATUS_DELETE);
    }
    
    @Override
    public int queryTotal(PayTypeVo vo) {
        if (null == vo) {
            LOG.info("PayType query param is null !");
            return 0;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select count(id)")
            .append(" from " + payTypeTable)
            .append(" where name like ? and num like ? and status <> ?");
        String name = Utils.completeMatch(vo.getName());
        String num = Utils.completeMatch(vo.getNumber());
        return getJdbcTemplate().queryForObject(sql.toString(), Integer.class, name, num, Constants.STATUS_DELETE);
    }
    
    @Override
    public List<PayType> query(PayTypeVo vo) {
        if (null == vo) {
            LOG.info("PayType query param is null !");
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select id,num,name,`desc`,remark,status, ")
            .append("DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + payTypeTable + " where ")
            .append(" name like ? and num like ? and status <> ?")
            .append(" order by createDate desc")
            .append(" limit ?,?");
        String name = Utils.completeMatch(vo.getName());
        String num = Utils.completeMatch(vo.getNumber());
        return getJdbcTemplate().query(sql.toString(),
            getPayTypeMapper(),
            name,
            num,
            Constants.STATUS_DELETE,
            vo.getLimitStart(),
            vo.getLimitEnd());
    }
    
    @Override
    public List<PayType> getDisplayName() {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,name from ").append(payTypeTable).append(" where status <> ?");
        return getJdbcTemplate().query(sql.toString(), getNameMapper(), Constants.STATUS_DELETE);
    }
    
    /**
     * 获取搜索返回对象
     * 
     * @return
     */
    private ParameterizedRowMapper<PayType> getPayTypeMapper() {
        return new ParameterizedRowMapper<PayType>() {
            @Override
            public PayType mapRow(ResultSet rs, int arg1)
                throws SQLException {
                PayType bean = new PayType();
                bean.setId(rs.getInt("id"));
                bean.setName(rs.getString("name"));
                bean.setNumber(rs.getString("num"));
                bean.setDesc(rs.getString("desc"));
                bean.setStatus(rs.getInt("status"));
                bean.setRemark(rs.getString("remark"));
                bean.setCreateDate(rs.getString("cd"));
                bean.setUpdateDate(rs.getString("ud"));
                return bean;
            }
        };
    }
    
    /**
     * 获取搜索返回对象
     * 
     * @return
     */
    private ParameterizedRowMapper<PayType> getNameMapper() {
        return new ParameterizedRowMapper<PayType>() {
            @Override
            public PayType mapRow(ResultSet rs, int arg1)
                throws SQLException {
                PayType bean = new PayType();
                bean.setId(rs.getInt("id"));
                bean.setName(rs.getString("name"));
                return bean;
            }
        };
    }
}
