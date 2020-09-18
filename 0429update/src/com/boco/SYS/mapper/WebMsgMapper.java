package com.boco.SYS.mapper;


import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.WebMsg;
/**
 * 
 * 
 * ��Ϣ�б�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2016-04-23   ���ǽ�      �����½�
 * </pre>
 */
public interface WebMsgMapper extends GenericMapper<WebMsg,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ��ѯ���ײ�����������
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	public List<String> getMsgType(String opercode) throws DataAccessException;
	
	/**
	 * 
	 *
	 * TODO ���������û������ѯ�����¼��.
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��27��    	    ����    �½�
	 * </pre>
	 */
	public List<Map<String,String>> countByRepuser() throws DataAccessException;
	/**
	 * 
	 *
	 * TODO  ��ѯ���ײ������ͣ�������ϸҳ��.
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��03��    ���ǽ�    �½�
	 * </pre>
	 */
	public List<WebMsg> getMsgTypeUrl(WebMsg webMsg) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO  ��ѯÿ���ļ�¼��
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��03��    ���ǽ�    �½�
	 * </pre>
	 */
	public String getMsgCount(WebMsg webMsg) throws DataAccessException;
	/**
	 * 
	 *
	 * TODO   ��ѯ��ת��ַ
	 *
	 * @return
	 * @throws DataAccessException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��03��    ���ǽ�    �½�
	 * </pre>
	 */
	public String fingUrl(String msgTypeCode) throws DataAccessException;
}