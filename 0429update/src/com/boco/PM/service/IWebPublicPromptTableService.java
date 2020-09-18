package com.boco.PM.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.WebPublicPromptTable;

/**
 * 
 * 
 * ������ʾ��||������ʾ��ҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebPublicPromptTableService extends IGenericService<WebPublicPromptTable,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ���淽��
	 *
	 * @param publicPromptTable
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��11��11��    	   ������  �½�
	 * </pre>
	 */
	public void saveInfo(WebPublicPromptTable webPublicPromptTable) throws Exception;
	/**
	 * 
	 *
	 * TODO ˢ�¹�����Ϣ 
	 *
	 * @param publicPromptTable
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��11��11��    	   ������  �½�
	 * </pre>
	 */
	public void refresh(WebPublicPromptTable webPublicPromptTable) throws Exception;
}