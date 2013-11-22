package com.lost.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lost.bean.Orders;
import com.lost.bean.OrdersProduct;
import com.lost.server.OrdersService;
import com.lost.util.Utils;
import com.lost.vo.OrdersVo;
import com.lost.vo.Page;

@Controller
@RequestMapping("/orders.do")
public class OrdersControl extends BaseControl {

    @Resource
    OrdersService ordersService;

    @RequestMapping(params = "type=add")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response, OrdersVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("action", "orders.do?type=addConfig");
        return new ModelAndView("orders_add", map);
    }

    @RequestMapping(params = "type=addConfig")
    public ModelAndView addConfig(HttpServletRequest request, HttpServletResponse response,
                                  OrdersVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = "添加失败!";
        if (null != vo) {
            String[] proId = vo.getProductId();
            int size = proId.length;
            List<OrdersProduct> proList = null;
            if (null != proId && size > 0) {
                proList = new ArrayList<OrdersProduct>(size);
                for (int i = 0; i < size; i++) {
                    OrdersProduct bean = new OrdersProduct();
                    bean.setOrdersId(vo.getId());
                    bean.setName(vo.getName());
                    bean.setOrdersNum(vo.getOrdersNum());
                    bean.setProId(vo.getProductId()[i]);
                    bean.setProName(vo.getProductName()[i]);
                    bean.setProNum(vo.getProductNum()[i]);
                    bean.setProDesc(vo.getProductDesc()[i]);
                    bean.setProPrice(Float.valueOf(vo.getProductPrice()[i]));
                    bean.setProCount(Float.valueOf(vo.getProductCount()[i]));
                    proList.add(bean);
                }
            }
            vo.setOrdersProduct(proList);
            if (ordersService.add(vo)) {
                msg = "添加成功";
            }
        }
        map.put("msg", msg);
        return new ModelAndView("prompt", map);
    }

    /**
     * 供前台Ajax调用
     * 
     * @param request
     * @param response
     * @param vo
     */
    @RequestMapping(params = "type=delById")
    public void delete(@RequestParam("id") String id, HttpServletRequest request,
                       HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isEmpty(id)) {
            map.put("type", "-1");
            map.put("desc", "删除失败，参数为空值！");
        } else if (ordersService.delById(id)) {
            map.put("type", "0");
            map.put("desc", "删除成功！");
        } else {
            map.put("type", "-1");
            map.put("desc", "删除失败！");
        }
        write(response, Utils.objectToJson(map));
    }

    @RequestMapping(params = "type=query")
    public ModelAndView query(HttpServletRequest request, HttpServletResponse response, OrdersVo vo) {

        Map<String, Object> map = new HashMap<String, Object>();
        Page<Orders> page = new Page<Orders>(vo.getCurPage(), vo.getPageSize(),
            ordersService.queryTotal(vo), ordersService.query(vo));
        map.put("page", page);
        map.put("name", Utils.isEmpty(vo.getName()) ? "" : vo.getName());
        map.put("num", Utils.isEmpty(vo.getOrdersNum()) ? "" : vo.getOrdersNum());
        map.put("buyer", Utils.isEmpty(vo.getBuyerName()) ? "" : vo.getBuyerName());
        map.put("expressNum", Utils.isEmpty(vo.getExpressNum()) ? "" : vo.getExpressNum());
        map.put("status", vo.getStatus());
        return new ModelAndView("orders_list", map);
    }

    @RequestMapping(params = "type=update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("id") String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isEmpty(id)) {
            map.put("bean", null);
        } else {
            map.put("bean", ordersService.getById(id));
        }
        map.put("action", "orders.do?type=updateConfig");
        return new ModelAndView("orders_update", map);
    }

    @RequestMapping(params = "type=updateConfig")
    public ModelAndView updateConfig(HttpServletRequest request, HttpServletResponse response,
                                     OrdersVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = "修改失败!";
        if (null != vo) {
            if (ordersService.update(vo)) {
                msg = "修改成功!";
            }
        }
        map.put("msg", msg);
        return new ModelAndView("prompt", map);
    }

    /**
     * 供前台Ajax调用
     * 
     * @param request
     * @param response
     * @param vo
     */
    @RequestMapping(params = "type=search")
    public void login(HttpServletRequest request, HttpServletResponse response, OrdersVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        Page<Orders> page = new Page<Orders>(vo.getCurPage(), vo.getPageSize(),
            ordersService.queryTotal(vo), ordersService.query(vo));
        map.put("page", page);
        write(response, Utils.objectToJson(map));
    }

    @RequestMapping(params = "type=delOP")
    public void delOrdersProduct(@RequestParam("id") String id, HttpServletRequest request,
                                 HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", -1);
        map.put("desc", "删除失败!");
        if (Utils.isNotEmpty(id)){
            if (ordersService.delOPById(id)){
                map.put("type", 0);
                map.put("desc", "删除成功!");
            }
        }
        write(response, Utils.objectToJson(map));
    }
    
    @RequestMapping(params = "type=detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("id") String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isEmpty(id)) {
            map.put("bean", null);
        } else {
            map.put("bean", ordersService.getById(id));
        }
        return new ModelAndView("orders_detail", map);
    }

}
