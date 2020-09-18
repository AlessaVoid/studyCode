package com.boco.PM.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebApproveModel;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebApproveModelҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebApproveModelService extends IGenericService<WebApproveModel,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ���������������ģ����Ϣ.
	 *
	 * @param webApproveModel
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json insertWebApproveMode(WebApproveModel webApproveModel,FdOper fdOper) throws Exception;
	/**
	 * 
	 *
	 * TODO �޸ĳ����������ģ����Ϣ.
	 *
	 * @param webApproveModel
	 * @param fdOper
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��9��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json updateWebApproveMode(WebApproveModel webApproveModel,FdOper fdOper) throws Exception;
}