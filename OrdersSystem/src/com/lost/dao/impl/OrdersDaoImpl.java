package com.lost.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lost.bean.Orders;
import com.lost.bean.OrdersProduct;
import com.lost.dao.OrdersDao;
import com.lost.util.Constants;
import com.lost.util.Utils;
import com.lost.vo.OrdersVo;
import com.mysql.jdbc.Statement;

@Transactional
@Repository("ordersDao")
public class OrdersDaoImpl extends BaseDaoAbstract implements OrdersDao {
    private static final Logger LOG = Logger.getLogger(OrdersDaoImpl.class);

    private String ordersTable = "os_orders";

    private String ordersProTab = "os_orders_product";

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean add(OrdersVo vo) {
        if (null != vo) {
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO ")
                .append(ordersTable)
                .append("(ordersNum, NAME, `DESC`, startDate, endDate, totalPrice, totalProduct,")
                .append("buyerName,buyerAddress,buyerPhone,buyerMsg,payType,expressName,payId,")
                .append("expressId,expressNum,remark,STATUS,expressPrice,createDate,updateDate)")
                .append(
                    " VALUES(?,?,?,STR_TO_DATE(?,'%Y-%m-%d %T' ),STR_TO_DATE(?,'%Y-%m-%d %T' ),?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),NOW())");
            // 增加订单
            int orderId = addOrders(vo);
            // 增加订单商品
            vo.setId(orderId);
            return addOrdersProduct(vo);
        } else {
            LOG.info("Params is null !");
            return false;
        }
    }

    public boolean addOrdersProduct(List<OrdersProduct> ops) {
        if (null != ops && ops.size() > 0) {
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO ")
                .append(ordersProTab)
                .append(
                    "(ordersId,ordersNum,proNum,proId,NAME,`DESC`,price,COUNT,createDate,updateDate,STATUS,remark)")
                .append("VALUES(?,?,?,?,?,?,?,?,NOW(),NOW(),?,?)");
            List<Object[]> list = new ArrayList<Object[]>(ops.size());
            Object[] op = null;
            for (OrdersProduct bean : ops) {
                op = new Object[10];
                op[0] = bean.getId();
                op[1] = bean.getOrdersNum();
                op[2] = bean.getProNum();
                op[3] = bean.getProId();
                op[4] = bean.getName();
                op[5] = bean.getProDesc();
                op[6] = bean.getProPrice();
                op[7] = bean.getProCount();
                op[8] = 1;
                op[9] = bean.getRemark();
                list.add(op);
            }
            int[] result = getJdbcTemplate().batchUpdate(sql.toString(), list);
            return ops.size() == result.length;
        } else {
            LOG.info("Params is null !");
            return false;
        }
    }

    public boolean addOrdersProduct(OrdersVo vo) {
        if (null != vo && null != vo.getOrdersProduct() && vo.getOrdersProduct().size() > 0) {
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO ")
                .append(ordersProTab)
                .append(
                    "(ordersId,ordersNum,proNum,proId,NAME,`DESC`,price,COUNT,createDate,updateDate,STATUS,remark)")
                .append("VALUES(?,?,?,?,?,?,?,?,NOW(),NOW(),?,?)");
            List<Object[]> list = new ArrayList<Object[]>(vo.getOrdersProduct().size());
            Object[] op = null;
            for (OrdersProduct bean : vo.getOrdersProduct()) {
                op = new Object[10];
                op[0] = vo.getId();
                op[1] = vo.getOrdersNum();
                op[2] = bean.getProNum();
                op[3] = bean.getProId();
                op[4] = vo.getName();
                op[5] = bean.getProDesc();
                op[6] = bean.getProPrice();
                op[7] = bean.getProCount();
                op[8] = 1;
                op[9] = bean.getRemark();
                list.add(op);
            }
            int[] result = getJdbcTemplate().batchUpdate(sql.toString(), list);
            return vo.getOrdersProduct().size() == result.length;
        } else {
            LOG.info("Params is null !");
            return false;
        }
    }

    public int addOrders(final OrdersVo vo) {
        final String sql = "INSERT INTO "
                           + ordersTable
                           + "(ordersNum, NAME, `DESC`, startDate, totalPrice, totalProduct,"
                           + "buyerName,buyerAddress,buyerPhone,buyerMsg,payType,expressName,payId,"
                           + "expressId,expressNum,remark,STATUS,expressPrice,createDate,updateDate)"
                           + " VALUES(?,?,?,NOW(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),NOW())";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = null;
                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, vo.getOrdersNum());
                ps.setString(2, vo.getName());
                ps.setString(3, vo.getDesc());
                ps.setFloat(4, vo.getTotalPrice());
                ps.setFloat(5, vo.getTotalProduct());
                ps.setString(6, vo.getBuyerName());
                ps.setString(7, vo.getBuyerAddress());
                ps.setString(8, vo.getBuyerPhone());
                ps.setString(9, vo.getBuyerMsg());
                ps.setString(10, vo.getPayTypeName());
                ps.setString(11, vo.getExpressName());
                ps.setInt(12, vo.getPayTypeId());
                ps.setInt(13, vo.getExpressId());
                ps.setString(14, vo.getExpressNum());
                ps.setString(15, vo.getRemark());
                ps.setInt(16, vo.getStatus());
                ps.setFloat(17, vo.getExpressPrice());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    };

    @Override
    public boolean update(OrdersVo vo) {

        // TODO 订单对应产品，待更新
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ").append(ordersTable)
            .append(" set ordersNum = ?,NAME = ? , `DESC` = ? ,expressPrice = ?,");
        int status = vo.getStatus();
        // 根据状态设置结束时间
        if (6 == status || 9 == status) {
            sql.append(",endDate = NOW(),");
        }
        sql.append(
            "totalPrice=?,totalProduct = ? , buyerName = ? , buyerAddress = ? , buyerPhone = ? ,buyerMsg = ? , ")
            .append(
                "payType=?,expressName = ? , payId = ? , expressId = ? , expressNum = ? , remark = ?, STATUS = ? , updateDate = NOW() ")
            .append(" where id=?");
        return 1 == getJdbcTemplate().update(sql.toString(), vo.getOrdersNum(), vo.getName(),
            vo.getDesc(), vo.getExpressPrice(), vo.getTotalPrice(), vo.getTotalProduct(),
            vo.getBuyerName(), vo.getBuyerAddress(), vo.getBuyerPhone(), vo.getBuyerMsg(),
            vo.getPayTypeName(), vo.getExpressName(), vo.getPayTypeId(), vo.getExpressId(),
            vo.getExpressNum(), vo.getRemark(), status, vo.getId());
    }

    @Override
    public boolean updateOrdersProduct(List<OrdersProduct> ops) {
        if (null != ops && ops.size() > 0) {
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE ").append(ordersProTab)
                .append(" set count = ?,updateDate = NOW() ").append(" where id = ?");
            List<Object[]> list = new ArrayList<Object[]>(ops.size());
            Object[] op = null;
            for (OrdersProduct bean : ops) {
                op = new Object[2];
                op[0] = bean.getProCount();
                op[1] = bean.getId();
                list.add(op);
            }
            int[] result = getJdbcTemplate().batchUpdate(sql.toString(), list);
            return ops.size() == result.length;
        } else {
            LOG.info("Params is null !");
            return false;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delById(String id) {
        String sql = "UPDATE " + ordersTable + " set status = ? where id = ? ";
        if (1 == getJdbcTemplate().update(sql, Constants.STATUS_DELETE, id)) {
            sql = "UPDATE " + ordersProTab + " set status = ? where ordersId = ? ";
            getJdbcTemplate().update(sql, Constants.STATUS_DELETE, id);
            return true;
        } else {
            return false;
        }
    }

    public boolean delOPByOid(String oid) {
        String sql = "UPDATE " + ordersProTab + " set status = ? where ordersId = ? ";
        return 1 == getJdbcTemplate().update(sql, Constants.STATUS_DELETE, oid);
    }
    
    public boolean delOPById(String id) {
        String sql = "UPDATE " + ordersProTab + " set status = ? where id = ? ";
        return 1 == getJdbcTemplate().update(sql, Constants.STATUS_DELETE, id);
    }

    @Override
    public Orders getById(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,ordersNum, NAME, `DESC`,totalPrice, totalProduct,expressPrice,")
            .append("buyerName,buyerAddress,buyerPhone,buyerMsg,payType,expressName,payId,")
            .append("expressId,expressNum,remark,STATUS,")
            .append(
                "DATE_FORMAT(startDate,'%Y-%m-%d %T') sd, DATE_FORMAT(endDate,'%Y-%m-%d %T') ed, ")
            .append(
                "DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + ordersTable + " where ").append(" id = ? and status <> ?");
        Orders orders = getJdbcTemplate().queryForObject(sql.toString(), getOrdersMapper(), id,
            Constants.STATUS_DELETE);
        orders.setOrdersProduct(getOPByOid(id));
        return orders;
    }

    public List<OrdersProduct> getOPByOid(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ID,ordersId,ordersNum, proId,proNum,NAME, `DESC`, price, count,")
            .append(
                "DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud, ")
            .append("remark,STATUS,createDate,updateDate from ").append(ordersProTab)
            .append(" where status <> ? and ordersId = ?");
        return getJdbcTemplate().query(sql.toString(), getOrdersProductMapper(),
            Constants.STATUS_DELETE, id);
    }

    @Override
    public int queryTotal(OrdersVo vo) {
        if (null == vo) {
            LOG.info("Orders query param is null !");
            return 0;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select count(id)")
            .append(" from " + ordersTable)
            .append(
                " where ordersNum like ? and name like ? and buyerName like ? and expressNum like ? ");
        String num = Utils.completeMatch(vo.getOrdersNum());
        String name = Utils.completeMatch(vo.getName());
        String buyer = Utils.completeMatch(vo.getBuyerName());
        String expressNum = Utils.completeMatch(vo.getExpressNum());
        if (Constants.STATUS_DELETE < vo.getStatus()
            && vo.getStatus() <= Constants.OrdersStatus.MOENY_RETURN) {
            sql.append(" and status = ?");
            return getJdbcTemplate().queryForObject(sql.toString(), Integer.class, num, name,
                buyer, expressNum, vo.getStatus());
        } else {
            sql.append(" and status <> ?");
            // 查询所以状态数据
            return getJdbcTemplate().queryForObject(sql.toString(), Integer.class, num, name,
                buyer, expressNum, Constants.STATUS_DELETE);
        }

    }

    @Override
    public List<Orders> query(OrdersVo vo) {
        if (null == vo) {
            LOG.info("Orders query param is null !");
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select id,ordersNum, NAME, `DESC`,totalPrice, totalProduct,expressPrice,")
            .append("buyerName,buyerAddress,buyerPhone,buyerMsg,payType,expressName,payId,")
            .append("expressId,expressNum,remark,STATUS,")
            .append(
                "DATE_FORMAT(startDate,'%Y-%m-%d %T') sd, DATE_FORMAT(endDate,'%Y-%m-%d %T') ed, ")
            .append(
                "DATE_FORMAT(createDate,'%Y-%m-%d %T') cd, DATE_FORMAT(updateDate,'%Y-%m-%d %T') ud ")
            .append(" from " + ordersTable)
            .append(
                " where ordersNum like ? and name like ? and buyerName like ? and expressNum like ? ");
        String num = Utils.completeMatch(vo.getOrdersNum());
        String name = Utils.completeMatch(vo.getName());
        String buyer = Utils.completeMatch(vo.getBuyerName());
        String expressNum = Utils.completeMatch(vo.getExpressNum());
        if (Constants.STATUS_DELETE < vo.getStatus()
            && vo.getStatus() <= Constants.OrdersStatus.MOENY_RETURN) {
            sql.append(" and status = ?");
            return getJdbcTemplate().query(sql.toString(), getOrdersMapper(), num, name, buyer,
                expressNum, vo.getStatus());
        } else {
            sql.append(" and status <> ?");
            // 查询所以状态数据
            return getJdbcTemplate().query(sql.toString(), getOrdersMapper(), num, name, buyer,
                expressNum, Constants.STATUS_DELETE);
        }
    }

    /**
     * 获取搜索返回对象
     * 
     * @return
     */
    private ParameterizedRowMapper<Orders> getOrdersMapper() {
        return new ParameterizedRowMapper<Orders>() {
            @Override
            public Orders mapRow(ResultSet rs, int arg1) throws SQLException {
                Orders bean = new Orders();
                bean.setId(rs.getInt("id"));
                bean.setOrdersNum(rs.getString("ordersNum"));
                bean.setName(rs.getString("name"));
                bean.setDesc(rs.getString("desc"));
                bean.setStartDate(rs.getString("sd"));
                bean.setEndDate(rs.getString("ed"));
                bean.setTotalPrice(rs.getFloat("totalPrice"));
                bean.setTotalProduct(rs.getFloat("totalProduct"));
                bean.setBuyerName(rs.getString("buyerName"));
                bean.setBuyerAddress(rs.getString("buyerAddress"));
                bean.setBuyerPhone(rs.getString("buyerPhone"));
                bean.setBuyerMsg(rs.getString("buyerMsg"));
                bean.setExpressNum(rs.getString("expressNum"));
                bean.setStatus(rs.getInt("status"));
                bean.setCreateDate(rs.getString("cd"));
                bean.setUpdateDate(rs.getString("ud"));
                bean.setRemark(rs.getString("remark"));
                bean.setPayTypeId(rs.getInt("payId"));
                bean.setPayTypeName(rs.getString("payType"));
                bean.setExpressName(rs.getString("expressName"));
                bean.setExpressId(rs.getInt("expressId"));
                bean.setExpressPrice(rs.getFloat("expressPrice"));
                bean.setStatusName(Utils.ordersStatusToText(bean.getStatus()));
                return bean;
            }
        };
    }

    /**
     * 获取搜索返回对象
     * 
     * @return
     */
    private ParameterizedRowMapper<OrdersProduct> getOrdersProductMapper() {
        return new ParameterizedRowMapper<OrdersProduct>() {
            @Override
            public OrdersProduct mapRow(ResultSet rs, int arg1) throws SQLException {
                OrdersProduct opBean = new OrdersProduct();
                opBean = new OrdersProduct();
                opBean.setId(rs.getInt("id"));
                opBean.setOrdersId(rs.getInt("ordersId"));
                opBean.setOrdersNum(rs.getString("ordersNum"));
                opBean.setProId(rs.getString("proId"));
                opBean.setProNum(rs.getString("proNum"));
                opBean.setProName(rs.getString("name"));
                opBean.setProDesc(rs.getString("desc"));
                opBean.setProPrice(rs.getFloat("price"));
                opBean.setProCount(rs.getFloat("count"));
                opBean.setCreateDate(rs.getString("cd"));
                opBean.setUpdateDate(rs.getString("ud"));
                opBean.setStatus(rs.getInt("status"));
                opBean.setRemark(rs.getString("remark"));
                return opBean;
            }
        };
    }

}
