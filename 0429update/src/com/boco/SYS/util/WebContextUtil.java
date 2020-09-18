package com.boco.SYS.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.FdOrgan;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.exception.SystemException;
/**
 * 
 * 
 *  Web容器工具类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年2月24日    	    杨滔    新建
 * </pre>
 */
public class WebContextUtil {
	
	/**
	 * 	
	 *
	 * TODO 获取HttpServletRequest.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月1日    	    杨滔    新建
	 * </pre>
	 */
	public static HttpServletRequest getRequest() throws Exception{
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 
	 *
	 * TODO 获取HttpServletResponse.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月22日    	    杨滔    新建
	 * </pre>
	 */
	public static HttpServletResponse getResponse() throws Exception{
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	/**
	 * 
	 *
	 * TODO 获取Session.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月1日    	    杨滔    新建
	 * </pre>
	 */
	public static HttpSession getSession() throws Exception{
		return getRequest().getSession();
	}
	
	/**
	 * 
	 *
	 * TODO 获取登录账户.
	 *
	 * @param request
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月9日    	    杨滔    新建
	 * </pre>
	 */
	public static FdOper getSessionUser() throws Exception{
		FdOper oper = null;
		try{
			oper = (FdOper) getRequest().getSession().getAttribute("sessionUser");
		}catch(Exception e){
			//获取Session信息失败!
			throw new SystemException("w113", e);
		}
		return oper;
	}
	
	/**
	 * 
	 *
	 * TODO 获取登录机构信息.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月20日    	    杨滔    新建
	 * </pre>
	 */
	public static FdOrgan getSessionOrgan() throws Exception{
		FdOrgan organ = null;
		try{
			organ = (FdOrgan) getRequest().getSession().getAttribute("sessionOrgan");
		}catch(Exception e){
			//获取Session信息失败!
			throw new SystemException("w113", e);
		}
		return organ;
	}
	
	/**
	 * 
	 *
	 * TODO 获取登录用户基本信息.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月29日    	    杨滔    新建
	 * </pre>
	 */
	public static WebOperInfo getSessionOperInfo() throws Exception{
		WebOperInfo operInfo = null;
		try{
			operInfo = (WebOperInfo) getRequest().getSession().getAttribute("sessionOperInfo");
		}catch(Exception e){
			//获取Session信息失败!
			throw new SystemException("w113", e);
		}
		return operInfo;
	}
	
	/**
	 * 
	 *
	 * TODO 获取Session变量.
	 *
	 * @param key
	 * @param T
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月26日    	    杨滔    新建
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionVariable(String key, Class<T> T) throws Exception {
		T value = null;
		try{
			value = (T) getRequest().getSession().getAttribute(key);
		}catch(Exception e){
			//获取Session信息失败!
			throw new SystemException("w113", e);
		}
		return value;
	}
}
