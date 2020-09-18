package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebAreaOrganInfo;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebAreaOrganInfoҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebAreaOrganInfoService extends IGenericService<WebAreaOrganInfo,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO ����������Ϣ.
	 *
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json insertWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo,String organs,FdOper fdOper) throws Exception;
	/**
	 * 
	 *
	 * TODO �޸ĵ�����Ϣ.
	 *
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json updateWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo,String organs,FdOper fdOper) throws Exception;
	/**
	 * 
	 *
	 * TODO ɾ��������Ϣ.
	 *
	 * @param areaCode
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public void deleteWebAreaOrgan(WebAreaOrganInfo webAreaOrganInfo) throws Exception;
	/**
	 * 
	 *
	 * TODO ��ѯ����������ѡ��Ļ�����Ϣ.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public List<WebAreaOrganInfo> selectNotEqualOrgan(String areaCode);
}