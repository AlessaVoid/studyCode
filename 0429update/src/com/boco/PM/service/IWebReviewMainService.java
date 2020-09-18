package com.boco.PM.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.boco.SYS.base.IGenericService;
//import com.boco.SYS.entity.GfProdBaseInfo;
import com.boco.SYS.entity.WebReviewMain;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebReviewMainҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebReviewMainService extends IGenericService<WebReviewMain,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ��������ķ���˵��.
	 *
	 * @param appDto   ��ӻ��޸�ʱ��Ԫ��ֵ��ɵ�dto����
	 * @param dtoBeforeUpdate  ���޸�������Ҫ�Ĳ�������ʾ�޸�ǰ��dto������������Ϊnull
	 * @param
	 * @throws Exception
	 *
	 ** TODO �������빫������.
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��26��    	  ����    �½�
	 * </pre>
	 */
	@Transactional
	public void insertApproval(Object appDto, Object dtoBeforeUpdate, WebReviewMain webReviewMain) throws Exception;
	/**
	 * 
	 *
	 * TODO ���˴���������.
	 *
	 * @param webReviewMain
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��7��    	  ����    �½�
	 * </pre>
	 */
	public void doApproval(WebReviewMain webReviewMain) throws Exception;
	/**
	 * 
	 *
	 * TODO �����������»������һ����¼.
	 *
	 * @param
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��2��26��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public boolean saveOrUpdate(WebReviewMain webReviewMain) throws RuntimeException, Exception;
	/**
	 * 
	 *
	 * TODO �޸ĸ����������Ϣ.
	 *
	 * @param appNo
	 * @param type
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��12��    	  ����    �½�
	 * </pre>
	 */
	public Json updateWebReviewMain(String appNo, String type);
	/**
	 * 
	 *
	 * TODO ��ȡ������Ϣ��ʾ.
	 *
	 * @param
	 * @param
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��12��    	  ����    �½�
	 * </pre>
	 */
	public List<WebReviewMain> getReviewMsgs() throws Exception;
//	/**
//	 *
//	 *
//	 * TODO ��ȡ������Ϣ��ʾ.
//	 *
//	 * @param
//	 * @param
//	 *
//	 * <pre>
//	 * �޸�����        �޸���    �޸�ԭ��
//	 * 2016��5��12��    	����˧    �½�
//	 * </pre>
//	 */
//	public List<GfProdBaseInfo> getExchangeRate() throws Exception;
}