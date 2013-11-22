package com.lost.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lost.util.Constants;



/**
 * 基础控制器
 * 
 */
public abstract class BaseControl {

    /**
     * 此处获取子类的对象
     */
    private Logger LOG = Logger.getLogger(this.getClass());
	/** 取得上下文路径
	 * @param request
	 * @return
	 */
	protected String getContextPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
	
	/**
	 * 取得上下文路径
	 * @param request
	 * @param path 相对路径
	 * @return
	 */
	protected String getContextPath(HttpServletRequest request, String path){
		return request.getSession().getServletContext().getRealPath(path);
	}
	
	/**
	 * 取得上下文路径
	 * @param request
	 * @param path 相对路径
	 * @return
	 */
	protected ServletContext getServletContext(HttpServletRequest request){
		return request.getSession().getServletContext();
	}
	
	protected void write(HttpServletResponse response, String write) {
        PrintWriter pw = null;
        try {
            response.setHeader(Constants.CONTENT_TYPE_KEY, Constants.CONTENT_TYPE);
            pw = response.getWriter();
            pw.write(write);
            pw.flush();
        } catch (IOException e) {
            LOG.error("Response write param = " + write);
            LOG.error("Response write Excetpion: " + e);
        } finally {
            if (null != pw) {
                pw.close();
                pw = null;
            }
        }
    }
}
