package com.lost.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;

import com.lost.system.Session;
import com.lost.util.Utils;


public class SysFilter implements Filter {

	public static Logger LOG = Logger.getLogger(SysFilter.class);

	public List<String> filterList;

	public List<String> notfilterList;
	
	//过滤器开启标识：ture,开启
	public boolean FILTER_OPEN = false;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		if (!FILTER_OPEN) {
			chain.doFilter(request, response);
			return;
		}
		String url = req.getRequestURL().toString();
		boolean flag = false;
		if (null != notfilterList){
		    for (String str : notfilterList) {
                if(url.endsWith(str)){
                    flag = true;
                    break;
                }
            }
		}
		if (flag) {
			chain.doFilter(request, response);
		} else {
			Object reqInfo = req.getSession(true).getAttribute("session");
			if (null == reqInfo) {
				resp.sendError(404);
				return;
			} else {
				Session session = (Session) reqInfo;
				if (session.isLogin()) {
					chain.doFilter(request, response);
				} else {
					resp.sendError(404);
					return;
				}
			}
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		filterList = new ArrayList<String>();
		notfilterList = new ArrayList<String>();

		FILTER_OPEN = (0 == Integer.parseInt(config.getInitParameter("isOpen")) ? true
				: false);
		String value = config.getInitParameter("filter");
		String[] valArr;
		if (Utils.isNotEmpty(value)) {
			valArr = value.split(",");
			if (null != valArr) {
				for (String val : valArr) {
					filterList.add(val);
				}
			}
		}

		value = config.getInitParameter("notFilter");
		if (Utils.isNotEmpty(value)) {
			valArr = null;
			valArr = value.split(",");
			if (null != valArr) {
				for (String val : valArr) {
					notfilterList.add(val);
				}
			}
		}
	}

}
