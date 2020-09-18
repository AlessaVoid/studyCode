package com.boco.PM.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.boco.SYS.entity.WebMsg;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * ��Ϣ�б�
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2016-04-23   ���ǽ�     �����½�
 * </pre>
 */
public interface IWebMsgService extends IGenericService<WebMsg,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ������Ϣҳ��
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	public List<Map<String, String>> getWebMsgList(String opercode) throws Exception;
	/**
	 * 
	 *
	 * TODO ��ȡ��ҳ������Ϣ�б�
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	public Map<String, List<WebMsg>> getHomePageWebMsg(String opercode) throws Exception;
	/**
	 * 
	 *
	 * TODO ��ȡ���ײ������ʹ���
	 * 
	 * @return 
	 *
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2016-04-23       ���ǽ�      �����½�
	 * </pre>
	 */
	public List<String> getMsgType(String opercode) throws Exception;
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
	public List<WebMsg> getMsgTypeUrl(WebMsg webMsg) throws Exception;
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
	public String getMsgCount(WebMsg webMsg) throws Exception;
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
	public String findUrl(String msgTypeCode) throws Exception;
	/**
	 * 
	 *
	 * TODO ���뵼������ģ����ʾ
	 *
	 * @param newMsg
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��15��    	   ������  �½�
	 * </pre>
	 */
	public void insertExportNotice(WebMsg newMsg)throws Exception;
	/**
	 * 
	 *
	 * TODO ����ˢ��
	 *
	 * @param newMsg
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��20��    	   ������  �½�
	 * </pre>
	 */
	public void refreshNow()throws Exception;
}