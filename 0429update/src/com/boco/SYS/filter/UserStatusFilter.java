package com.boco.SYS.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boco.SYS.util.EncodingUtils;
import com.boco.util.JsonUtils;

/**
 * 
 * 
 *  用户状态拦截器,用与判断用户登录状态.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年12月22日    	    杨滔    新建
 * </pre>
 */
public class UserStatusFilter implements Filter {

	private Log log = LogFactory.getLog(this.getClass());
	private static String noFilterList = null;

	public void destroy() {
	}

	// 用户状态过滤器
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		// arg0.setCharacterEncoding("UTF-8");
		// arg1.setCharacterEncoding("GBK");
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String url = request.getRequestURI();
		String realUrl = url.substring(url.lastIndexOf("/") + 1);
		try {
			if (noFilterList.indexOf(realUrl) == -1) {
				if (request.getSession().getAttribute("sessionUser") == null) {
					if (request.getParameter("number") != null) {
						log.debug("=========登录超时=======");
						response.setContentType("text/html;charset=UTF-8");
						response.setStatus(10000);
					} else {
						log.debug("=========登录超时=======");
						response.setContentType("text/html;charset=UTF-8");
						response.sendRedirect(request.getContextPath() + "/timeout.htm");
					}
				} else {
					arg2.doFilter(arg0, arg1);
				}
			} else {
				arg2.doFilter(arg0, arg1);
			}
		} catch (Exception e) {
			String msg = e.getMessage();
			msg = msg.replaceAll("Request processing failed; nested exception is java.lang.Exception:", "");
			if (request.getParameter("number") != null) {
				log.debug("=========Ajax异常=======");
				response.setContentType("text/html;charset=UTF-8");
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("ex", e);
				response.getWriter().write(JsonUtils.toJson(map));
				response.getWriter().close();
			} else {
				log.debug("=========http异常=======");
				response.setContentType("text/html;charset=UTF-8");
				msg = EncodingUtils.ToBMZiFu(msg);
				response.getWriter().print("<script> window.location.href ='" + request.getContextPath() + "/error/error.htm?msg=" + msg + "'</script>");
				response.getWriter().close();
			}
		}
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		if (noFilterList == null) {
			noFilterList = arg0.getInitParameter("noFilterList");
			log.info("初始化用户状态过滤器，不拦截的URL:" + noFilterList);
		}
	}
}
