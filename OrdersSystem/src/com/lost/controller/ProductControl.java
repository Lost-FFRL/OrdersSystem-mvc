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

import com.lost.bean.Product;
import com.lost.server.ProductService;
import com.lost.util.Utils;
import com.lost.vo.ProductVo;
import com.lost.vo.Page;

@Controller
@RequestMapping("/product.do")
public class ProductControl extends BaseControl {

    @Resource
    ProductService productService;

    @RequestMapping(params = "type=add")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response, ProductVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("action", "product.do?type=addConfig");
        return new ModelAndView("product_add", map);
    }

    @RequestMapping(params = "type=addConfig")
    public ModelAndView addConfig(HttpServletRequest request, HttpServletResponse response,
                                  ProductVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = "添加失败!";
        if (productService.add(vo)) {
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
    public void delete(@RequestParam("id") String id, HttpServletRequest request,
                       HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isEmpty(id)) {
            map.put("type", "-1");
            map.put("desc", "删除失败，参数为空值！");
        } else if (productService.delById(id)) {
            map.put("type", "0");
            map.put("desc", "删除成功！");
        } else {
            map.put("type", "-1");
            map.put("desc", "删除失败！");
        }
        write(response, Utils.objectToJson(map));
    }

    @RequestMapping(params = "type=query")
    public ModelAndView query(HttpServletRequest request, HttpServletResponse response, ProductVo vo) {

        Map<String, Object> map = new HashMap<String, Object>();
        Page<Product> page = new Page<Product>(vo.getCurPage(), vo.getPageSize(),
            productService.queryTotal(vo), productService.query(vo));
        map.put("page", page);
        map.put("name", Utils.isEmpty(vo.getName()) ? "" : vo.getName());
        map.put("num", Utils.isEmpty(vo.getNumber()) ? "" : vo.getNumber());
        return new ModelAndView("product_list", map);
    }

    @RequestMapping(params = "type=update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("id") String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Utils.isEmpty(id)) {
            map.put("bean", null);
        } else {
            map.put("bean", productService.getById(id));
        }
        map.put("action", "product.do?type=updateConfig");
        return new ModelAndView("product_add", map);
    }

    @RequestMapping(params = "type=updateConfig")
    public ModelAndView updateConfig(HttpServletRequest request, HttpServletResponse response,
                                     ProductVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = "修改失败!";
        if (productService.update(vo)) {
            msg = "修改成功!";
        }
        map.put("msg", msg);
        return new ModelAndView("prompt", map);
    }

    /**
     * 供前台ajax获取数据
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "type=getAll")
    public void getAll(HttpServletRequest request, HttpServletResponse response) {
        write(response, Utils.objectToJson(productService.query()));
    }

    /**
     * 供前台Ajax调用
     * 
     * @param request
     * @param response
     * @param vo
     */
    @RequestMapping(params = "type=search")
    public void search(HttpServletRequest request, HttpServletResponse response, ProductVo vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        Page<Product> page = new Page<Product>(vo.getCurPage(), vo.getPageSize(),
            productService.queryTotal(vo), productService.query(vo));
        map.put("page", page);
        write(response, Utils.objectToJson(map));
    }

}
