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
 *  Web����������.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2016��2��24��    	    ����    �½�
 * </pre>
 */
public class WebContextUtil {
	
	/**
	 * 	
	 *
	 * TODO ��ȡHttpServletRequest.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��1��    	    ����    �½�
	 * </pre>
	 */
	public static HttpServletRequest getRequest() throws Exception{
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 
	 *
	 * TODO ��ȡHttpServletResponse.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��22��    	    ����    �½�
	 * </pre>
	 */
	public static HttpServletResponse getResponse() throws Exception{
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	/**
	 * 
	 *
	 * TODO ��ȡSession.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��1��    	    ����    �½�
	 * </pre>
	 */
	public static HttpSession getSession() throws Exception{
		return getRequest().getSession();
	}
	
	/**
	 * 
	 *
	 * TODO ��ȡ��¼�˻�.
	 *
	 * @param request
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��9��    	    ����    �½�
	 * </pre>
	 */
	public static FdOper getSessionUser() throws Exception{
		FdOper oper = null;
		try{
			oper = (FdOper) getRequest().getSession().getAttribute("sessionUser");
		}catch(Exception e){
			//��ȡSession��Ϣʧ��!
			throw new SystemException("w113", e);
		}
		return oper;
	}
	
	/**
	 * 
	 *
	 * TODO ��ȡ��¼������Ϣ.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��11��20��    	    ����    �½�
	 * </pre>
	 */
	public static FdOrgan getSessionOrgan() throws Exception{
		FdOrgan organ = null;
		try{
			organ = (FdOrgan) getRequest().getSession().getAttribute("sessionOrgan");
		}catch(Exception e){
			//��ȡSession��Ϣʧ��!
			throw new SystemException("w113", e);
		}
		return organ;
	}
	
	/**
	 * 
	 *
	 * TODO ��ȡ��¼�û�������Ϣ.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��29��    	    ����    �½�
	 * </pre>
	 */
	public static WebOperInfo getSessionOperInfo() throws Exception{
		WebOperInfo operInfo = null;
		try{
			operInfo = (WebOperInfo) getRequest().getSession().getAttribute("sessionOperInfo");
		}catch(Exception e){
			//��ȡSession��Ϣʧ��!
			throw new SystemException("w113", e);
		}
		return operInfo;
	}
	
	/**
	 * 
	 *
	 * TODO ��ȡSession����.
	 *
	 * @param key
	 * @param T
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��26��    	    ����    �½�
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSessionVariable(String key, Class<T> T) throws Exception {
		T value = null;
		try{
			value = (T) getRequest().getSession().getAttribute(key);
		}catch(Exception e){
			//��ȡSession��Ϣʧ��!
			throw new SystemException("w113", e);
		}
		return value;
	}
}
