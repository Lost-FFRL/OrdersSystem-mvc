package com.lost.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.lost.bean.Product;
import com.lost.dao.ProductDao;
import com.lost.util.Constants;
import com.lost.util.Utils;
import com.lost.vo.OrdersVo;
import com.lost.vo.ProductVo;

@Repository("productDao")
public class ProductDaoImpl extends BaseDaoAbstract implements ProductDao {
    private static final Logger LOG = Logger.getLogger(ProductDaoImpl.class);

    private String productTable = "os_product";

    @Override
    public boolean add(ProductVo vo) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ").append(productTable)
            .append("(proNum,NAME,`desc`,price,STATUS, createDate, updateDate, remark)")
            .append(" VALUES(?,?,?,?,1,NOW(),NOW(),?)");
        return 1 == getJdbcTemplate().update(sql.toString(), vo.getNumber(), vo.getName(),
            vo.getDesc(), vo.getPrice(), vo.getRemark());
    }

    @Override
    public boolean update(ProductVo vo) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ").append(productTable)
            .append(" set proNum = ?,name = ?,`desc`=?,price=?, updateDate=NOW(), remark =?")
            .append(" where id=?");
        return 1 == getJdbcTemplate().update(sql.toString(), vo.getNumber(), vo.getName(),
            vo.getDesc(), vo.getPrice(), vo.getRemark(), vo.getId());
    }

    @Override
    public boolean delById(String id) {
        String sql = "UPDATE " + productTable + " set status = ? where id = ? ";
        return 1 == getJdbcTemplate().update(sql, Constants.STATUS_DELETE, id);
    }

    @Override
    public Product getById(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,proNum,name,`desc`,price,STATUS,remark, ")
            .append(
                "DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + productTable + " where ").append(" id = ? and status <> ?");
        return getJdbcTemplate().queryForObject(sql.toString(), getProductMapper(), id,
            Constants.STATUS_DELETE);
    }

    @Override
    public int queryTotal(ProductVo vo) {
        if (null == vo) {
            LOG.info("Product query param is null !");
            return 0;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select count(id)").append(" from " + productTable)
            .append(" where name like ? and proNum like ? and status <> ?");
        String name = Utils.completeMatch(vo.getName());
        String num = Utils.completeMatch(vo.getNumber());
        return getJdbcTemplate().queryForObject(sql.toString(), Integer.class, name, num,
            Constants.STATUS_DELETE);
    }

    @Override
    public List<Product> query(ProductVo vo) {
        if (null == vo) {
            LOG.info("Product query param is null !");
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select id,proNum,name,`desc`,price,STATUS,remark, ")
            .append(
                "DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + productTable + " where ")
            .append(" name like ? and proNum like ? and status <> ?")
            .append(" order by createDate desc").append(" limit ?,?");
        String name = Utils.completeMatch(vo.getName());
        String num = Utils.completeMatch(vo.getNumber());
        return getJdbcTemplate().query(sql.toString(), getProductMapper(), name, num,
            Constants.STATUS_DELETE, vo.getLimitStart(), vo.getLimitEnd());
    }

    @Override
    public List<Product> query() {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,proNum,name,`desc`,price,STATUS,remark, ")
            .append(
                "DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + productTable)
            .append(" where status <> ? ")
            .append(" order by createDate desc");
        return getJdbcTemplate().query(sql.toString(), getProductMapper(), Constants.STATUS_DELETE);
    }

    /**
     * 获取搜索返回对象
     * 
     * @return
     */
    private ParameterizedRowMapper<Product> getProductMapper() {
        return new ParameterizedRowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int arg1) throws SQLException {
                Product bean = new Product();
                bean.setId(rs.getInt("id"));
                bean.setNumber(rs.getString("proNum"));
                bean.setName(rs.getString("name"));
                bean.setDesc(rs.getString("desc"));
                // bean.setShelfLife(rs.getString("shelfLife"));
                // bean.setOutputDate(rs.getString("outputDate"));
                // bean.setExpiredDate(rs.getString("expiredDate"));
                bean.setPrice(rs.getFloat("price"));
                // bean.setDiscount(rs.getFloat("discount"));
                // bean.setDiscountPrice(rs.getFloat("discountPrice"));
                bean.setStatus(rs.getInt("status"));
                bean.setCreateDate(rs.getString("cd"));
                bean.setUpdateDate(rs.getString("ud"));
                bean.setRemark(rs.getString("remark"));
                return bean;
            }
        };
    }

    @Override
    public boolean addOrdersProduct(OrdersVo vo) {
        return false;
    }

}
