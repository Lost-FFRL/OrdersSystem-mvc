package com.lost.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lost.bean.Orders;
import com.lost.bean.User;
import com.lost.server.OrdersService;
import com.lost.server.UserService;
import com.lost.system.Session;
import com.lost.util.Utils;
import com.lost.vo.OrdersVo;
import com.lost.vo.Page;

@Controller
public class LoginControl extends BaseControl {

    @Resource
    UserService userService;
    
    @Resource
    OrdersService ordersService;

    @RequestMapping(value = "/login.do")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response, User user) {
        return new ModelAndView("re_login");
    }

    @RequestMapping(value = "/login_check.do", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("accout") String account,
                              @RequestParam("pwd") String pwd, HttpServletRequest request,
                              HttpServletResponse response, User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        String url = "re_login";
        if (userService.checkUser(account, pwd)) {
            // 登录成功，返回订单查询界面
            Session session = new Session();
            session.setLoginFlag(true);
            session.setUserId(request.getParameter("user"));
            request.getSession(true).setAttribute("session", session);    
            // 查询订单
            OrdersVo vo = new OrdersVo();
            url = "orders_list";
            Page<Orders> page =
                new Page<Orders>(vo.getCurPage(), vo.getPageSize(), ordersService.queryTotal(vo), ordersService.query(vo));
            map.put("page", page);
            map.put("name", Utils.isEmpty(vo.getName()) ? "" : vo.getName());
            map.put("num", Utils.isEmpty(vo.getOrdersNum()) ? "" : vo.getOrdersNum());
            map.put("buyer", Utils.isEmpty(vo.getBuyerName()) ? "" : vo.getBuyerName());
            map.put("expressNum", Utils.isEmpty(vo.getExpressNum()) ? "" : vo.getExpressNum());
            map.put("status", vo.getStatus());
            return new ModelAndView(url, map);
        } else {
            map.put("account", account);
            map.put("msg", "帐号或密码错误！");
            return new ModelAndView(url, map);
        }
        
    }
}
