package com.boco.SYS.mapper;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebShortMenuInfo;
/**
 * 
 * 
 * WebShortMenuInfo���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface WebShortMenuInfoMapper extends GenericMapper<WebShortMenuInfo,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO ���ݹ�Ա��ɾ����ݲ˵���Ϣ.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��4��    	  ����    �½�
	 * </pre>
	 */
	public Integer deleteByOperCode(String operCode) throws Exception;

	/**
	 * 
	 *
	 * TODO ��ѯ�˵���.
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
	 * TODO ��ѯ�������¼��˵��Ĳ˵���.
	 *
	 * @param webShortMenuInfo
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��21��    	     ����    �½�
	 * </pre>
	 */
	public List<String> selectUpMenuNo() throws Exception;
}