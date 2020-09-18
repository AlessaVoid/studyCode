package com.boco.GF.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.WebTaskRoleInfo;

/**
 * 
 * 
 * ����ڵ��ɫ��Ӧ��Ϣ��||����ڵ��ɫ��Ӧ��Ϣ��ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebTaskRoleInfoService extends IGenericService<WebTaskRoleInfo,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO ��ȡ�������������˵Ľ�ɫ����.
	 * processDefinitionKey : ���̶���key
	 * processDefinitionId : ���̶���id
	 * taskId �� ��ǰ��ڵ�
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��6��    	  ����    �½�
	 * </pre>
	 */
	public String getAppUserRole(String processDefinitionKey,String ProcessDefinitionId,String taskId,Map<String,Object> map) throws Exception;
	/**
	 * 
	 *
	 * TODO ��ȡ�������������˵Ľ�ɫ����.
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��4��27��    	  ����    �½�
	 * </pre>
	 * 
	 */
	public String getAppUserRoleByAttr(String processDefinitionKey,WebTaskRoleInfo webTaskRoleInfo) throws Exception;
	/**
	 * 
	 *
	 * TODO �������̽ڵ��ɫ������Ϣ
	 *
	 * @param webTaskRoleInfoList
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��6��13��    	   ������  �½�
	 * </pre>
	 */
	public void updateRoleInfoByBatch(List<WebTaskRoleInfo> webTaskRoleInfoList,String procDefId)throws Exception;
	/**
	 * 
	 *
	 * TODO �������̶���id��ѯ��¼
	 *
	 * @param procDefId
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��6��13��    	   ������  �½�
	 * </pre>
	 */
	public List<WebTaskRoleInfo> selectByProcDefId(String procDefId)throws Exception;

	/**
	 * 
	 *
	 * TODO ���̽ڵ��ɫ����
	 *
	 * @param procDefId
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��15��    	   ������  �½�
	 * </pre>
	 */
	public void nodeConfig(String procDefId,String type)throws Exception;
}