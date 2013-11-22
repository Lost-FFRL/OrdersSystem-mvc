package com.lost.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.lost.bean.Buyer;
import com.lost.dao.BuyerDao;
import com.lost.util.Constants;
import com.lost.util.Utils;
import com.lost.vo.BuyerVo;

@Repository("buyerDao")
public class BuyerDaoImpl extends BaseDaoAbstract implements BuyerDao {
    
    private static final Logger LOG = Logger.getLogger(BuyerDaoImpl.class);
    
    private String buyerTable = "os_buyer";
    
    @Override
    public boolean add(List<Buyer> buyers) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean add(BuyerVo vo) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ")
            .append(buyerTable)
            .append("(NAME,sex, `desc`,phone, mobile, address, reputation, STATUS, createDate, updateDate, remark)")
            .append(" VALUES(?,?,?,?,?,?,?,1,NOW(),NOW(),?)");
        return 1 == getJdbcTemplate().update(sql.toString(),
            vo.getName(),
            vo.getSex(),
            vo.getDesc(),
            vo.getPhone(),
            vo.getMobile(),
            vo.getAddress(),
            vo.getReputation(),
            vo.getRemark());
    }
    
    @Override
    public boolean update(BuyerVo vo) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ")
            .append(buyerTable)
            .append(" set name = ?,sex=?,`desc`=?,phone=? , mobile=?,address=?, reputation=? , updateDate=NOW(), remark =?")
            .append(" where id=?");
        return 1 == getJdbcTemplate().update(sql.toString(),
            vo.getName(),
            vo.getSex(),
            vo.getDesc(),
            vo.getPhone(),
            vo.getMobile(),
            vo.getAddress(),
            vo.getReputation(),
            vo.getRemark(),
            vo.getId());
    }
    
    @Override
    public boolean delById(String id) {
        String sql = "UPDATE " + buyerTable + " set status = ? where id = ? ";
        return 1 == getJdbcTemplate().update(sql, Constants.STATUS_DELETE, id);
    }
    
    
    @Override
    public Buyer getById(String id){
        StringBuffer sql = new StringBuffer();
        sql.append("select id,name,sex,`desc`,phone,mobile,address,reputation,remark, ")
            .append("DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + buyerTable + " where ")
            .append(" id = ? and status <> ?");
        return getJdbcTemplate().queryForObject(sql.toString(), getBuyerMapper(), id, Constants.STATUS_DELETE);
    }
    
    @Override
    public boolean delete(List<String> ids) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean delete(String ids) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public int queryTotal(BuyerVo vo) {
        if (null == vo) {
            LOG.info("Buyer query param is null !");
            return 0;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select count(id) from ")
            .append(buyerTable)
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
    public List<Buyer> query(BuyerVo vo) {
        if (null == vo) {
            LOG.info("Buyer query param is null !");
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select id,name,sex,`desc`,phone,mobile,address,reputation,remark, ")
            .append("DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + buyerTable + " where ")
            .append(" name like ? and mobile like ? and phone like ? and status <> ?")
            .append(" order by createDate desc")
            .append(" limit ?,?");
        String name = Utils.completeMatch(vo.getName());
        String mobile = Utils.completeMatch(vo.getMobile());
        String phone = Utils.completeMatch(vo.getPhone());
        return getJdbcTemplate().query(sql.toString(),
            getBuyerMapper(),
            name,
            mobile,
            phone,
            Constants.STATUS_DELETE,
            vo.getLimitStart(),
            vo.getLimitEnd());
    }
    
    @Override
    public List<Buyer> query(Buyer buyer) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean isExists(Buyer buyer) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * 获取搜索返回对象
     * 
     * @return
     */
    private ParameterizedRowMapper<Buyer> getBuyerMapper() {
        return new ParameterizedRowMapper<Buyer>() {
            @Override
            public Buyer mapRow(ResultSet rs, int arg1)
                throws SQLException {
                Buyer bean = new Buyer();
                bean.setId(rs.getInt("id"));
                bean.setSex(rs.getInt("sex"));
                bean.setName(rs.getString("name"));
                bean.setDesc(rs.getString("desc"));
                bean.setPhone(rs.getString("phone"));
                bean.setMobile(rs.getString("mobile"));
                bean.setAddress(rs.getString("address"));
                bean.setReputation(rs.getInt("reputation"));
                bean.setUpdateDate(rs.getString("ud"));
                bean.setCreateDate(rs.getString("cd"));
                bean.setRemark(rs.getString("remark"));
                return bean;
            }
        };
    }
    
}
