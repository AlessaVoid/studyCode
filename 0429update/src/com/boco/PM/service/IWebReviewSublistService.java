package com.boco.PM.service;

import java.util.HashMap;
import java.util.List;

import com.boco.SYS.entity.WebReviewSublist;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * WebReviewSublistҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebReviewSublistService extends IGenericService<WebReviewSublist,HashMap<String,Object>>{
	/**
	 * 
	 *
	 * TODO ����orderNo���������ѯ
	 *
	 * @param appNo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��12��18��    	   ������  �½�
	 * </pre>
	 */
	public List<WebReviewSublist> selectAppNo(String appNo) throws Exception;
}