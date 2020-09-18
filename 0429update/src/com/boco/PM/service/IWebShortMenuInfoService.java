package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebShortMenuInfo;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebShortMenuInfoҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebShortMenuInfoService extends IGenericService<WebShortMenuInfo,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO ά����ݲ˵���Ϣ.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public Json updateWebShortMenuInfo(WebShortMenuInfo webShortMenuInfo,String funCode) throws Exception;
	/**
	 * 
	 *
	 * TODO ���ݹ�Ա��ɫ��ѯ��Աӵ�еĹ���(��̬����where�����е�or).
	 *
	 * @param roleCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public List<WebShortMenuInfo> selectByRoleList(List<String> roleCode)throws Exception;
	
	/**
	 * 
	 *
	 * TODO ����������ѯ�˵���.
	 *
	 * @param webShortMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��21��    	     ����    �½�
	 * </pre>
	 */
	public List<String> selectMenuNoByAttr(WebShortMenuInfo webShortMenuInfo) throws Exception;
	
	/**
	 * 
	 *
	 * TODO ��ѯ���и��˵�.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��21��    	     ����    �½�
	 * </pre>
	 */
	public List<String> selectUpMenuNo() throws Exception;
}