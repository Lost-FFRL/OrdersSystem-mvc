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

import com.lost.bean.PayType;
import com.lost.server.PayTypeService;
import com.lost.util.Utils;
import com.lost.vo.PayTypeVo;
import com.lost.vo.Page;

@Controller
@RequestMapping("/payType.do")
public class PayTypeControl extends BaseControl {
    
    @Resource
    PayTypeService payTypeService;
    
    @RequestMapping(params = "type=add")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response, PayTypeVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("action", "payType.do?type=addConfig");
        return new ModelAndView("payType_add", map);
    }
    
    @RequestMapping(params = "type=addConfig")
    public ModelAndView addConfig(HttpServletRequest request, HttpServletResponse response, PayTypeVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = "添加失败!";
        if (payTypeService.add(vo)) {
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
        } else if (payTypeService.delById(id)) {
            map.put("type", "0");
            map.put("desc", "删除成功！");
        } else {
            map.put("type", "-1");
            map.put("desc", "删除失败！");
        }
        write(response, Utils.objectToJson(map));
    }
    
    @RequestMapping(params = "type=query")
    public ModelAndView query(HttpServletRequest request, HttpServletResponse response, PayTypeVo vo) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        Page<PayType> page =
            new Page<PayType>(vo.getCurPage(), vo.getPageSize(), payTypeService.queryTotal(vo),
                payTypeService.query(vo));
        map.put("page", page);
        map.put("name", Utils.isEmpty(vo.getName()) ? "" : vo.getName());
        map.put("num", Utils.isEmpty(vo.getNumber()) ? "" : vo.getNumber());
        return new ModelAndView("payType_list", map);
    }
    
    @RequestMapping(params = "type=update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isEmpty(id)) {
            map.put("bean", null);
        } else {
            map.put("bean", payTypeService.getById(id));
        }
        map.put("action", "payType.do?type=updateConfig");
        return new ModelAndView("payType_add", map);
    }
    
    @RequestMapping(params = "type=updateConfig")
    public ModelAndView updateConfig(HttpServletRequest request, HttpServletResponse response, PayTypeVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = "修改失败!";
        if (payTypeService.update(vo)) {
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
    public void login(HttpServletRequest request, HttpServletResponse response, PayTypeVo vo) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        Page<PayType> page =
            new Page<PayType>(vo.getCurPage(), vo.getPageSize(), payTypeService.queryTotal(vo),
                payTypeService.query(vo));
        map.put("page", page);
        write(response, Utils.objectToJson(map));
    }
    
    @RequestMapping(params = "type=getNames")
    public void getDisplayName(HttpServletRequest request, HttpServletResponse response){
        write(response, Utils.objectToJson(payTypeService.getDisplayName()));
    }
    
}
