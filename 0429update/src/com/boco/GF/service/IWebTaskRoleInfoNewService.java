package com.boco.GF.service;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.WebTaskRoleInfoNew;

/**
 * 
 * 
 * WebTaskRoleInfoNewҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebTaskRoleInfoNewService extends IGenericService<WebTaskRoleInfoNew,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO �������������в�����ת������Ϣ
	 *
	 * @param webTaskRoleInfoList
	 * @param procDefId
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��8��8��    	   ������  �½�
	 * </pre>
	 */
public	void updateRoleInfoByBatch(List<WebTaskRoleInfoNew> webTaskRoleInfoList,String procDefId) throws Exception;
	/**
	 * 
	 *
	 * TODO �������̶����ѯ��¼
	 *
	 * @param procDefId
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��8��8��    	   ������  �½�
	 * </pre>
	 */
	public List<WebTaskRoleInfoNew> selectByProcDefId(String procDefId)throws Exception;
	/**
	 * 
	 *
	 * TODO �����������غ��Ʒ״̬
	 *
	 * @param taskId
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��9��6��    	   ������  �½�
	 * </pre>
	 */
	public void updateProdStatus(String prodCode,String taskId,String custType) throws Exception;
	
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