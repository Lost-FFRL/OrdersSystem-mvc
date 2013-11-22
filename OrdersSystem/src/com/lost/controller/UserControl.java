package com.lost.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lost.bean.User;
import com.lost.server.UserService;
import com.lost.util.Utils;
import com.lost.vo.UserVo;
import com.lost.vo.Page;

@Controller
@RequestMapping("/user.do")
public class UserControl extends BaseControl {
    
    @Resource
    UserService userService;
    
    @RequestMapping(params = "type=add")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response, UserVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("action", "user.do?type=addConfig");
        return new ModelAndView("user_add", map);
    }
    
    @RequestMapping(params = "type=addConfig")
    public ModelAndView addConfig(HttpServletRequest request, HttpServletResponse response, UserVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = "添加失败!";
        if (userService.add(vo)) {
            msg = "添加成功";
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
    public void delete(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isEmpty(id)) {
            map.put("type", "-1");
            map.put("desc", "删除失败，参数为空值！");
        } else if (userService.delById(id)) {
            map.put("type", "0");
            map.put("desc", "删除成功！");
        } else {
            map.put("type", "-1");
            map.put("desc", "删除失败！");
        }
        write(response, Utils.objectToJson(map));
    }
    
    @RequestMapping(params = "type=query")
    public ModelAndView query(HttpServletRequest request, HttpServletResponse response, UserVo vo) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        Page<User> page =
            new Page<User>(vo.getCurPage(), vo.getPageSize(), userService.queryTotal(vo),
                userService.query(vo));
        map.put("page", page);
        map.put("name", Utils.isEmpty(vo.getName()) ? "" : vo.getName());
        map.put("mobile", Utils.isEmpty(vo.getMobile()) ? "" : vo.getMobile());
        map.put("phone", Utils.isEmpty(vo.getPhone()) ? "" : vo.getPhone());
        return new ModelAndView("user_list", map);
    }
    
    @RequestMapping(params = "type=update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isEmpty(id)) {
            map.put("bean", null);
        } else {
            map.put("bean", userService.getById(id));
        }
        map.put("action", "user.do?type=updateConfig");
        return new ModelAndView("user_add", map);
    }
    
    @RequestMapping(params = "type=updateConfig")
    public ModelAndView updateConfig(HttpServletRequest request, HttpServletResponse response, UserVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = "修改失败!";
        if (userService.update(vo)) {
            msg = "修改成功!";
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
    public void login(HttpServletRequest request, HttpServletResponse response, UserVo vo) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        Page<User> page =
            new Page<User>(vo.getCurPage(), vo.getPageSize(), userService.queryTotal(vo),
                userService.query(vo));
        map.put("page", page);
        write(response, Utils.objectToJson(map));
    }
    
}
