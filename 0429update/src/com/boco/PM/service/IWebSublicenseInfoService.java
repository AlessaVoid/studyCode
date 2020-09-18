package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.entity.WebSublicenseInfo;
import com.boco.SYS.util.Json;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * WebSublicenseInfoҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebSublicenseInfoService extends IGenericService<WebSublicenseInfo,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ά��ת��Ȩ��Ϣ.
	 *
	 * @param webSublicenseInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��2��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json outWebSub(WebSublicenseInfo webSublicenseInfo, String roles) throws Exception;
	/**
	 * 
	 *
	 * TODO ���ɫ���ܱ������ѯ��Ȩ�û������б�����.
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��7��    	  ����    �½�
	 * </pre>
	 */
	public List<Map<String,String>> getPowerList(Map<String,String> query) throws Exception;
}