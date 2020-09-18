package com.boco.GF.service;

import java.util.HashMap;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.FdOper;
import com.boco.SYS.entity.WebTradeTime;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebTradeTimeҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebTradeTimeService extends IGenericService<WebTradeTime,java.lang.String>{
	/**
	 * 
	 *
	 * TODO �޸Ľ���ʱ��ڵ���Ϣ.
	 *
	 * @param webTradeTime
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��1��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json updateWebTradeTime(WebTradeTime webTradeTime,FdOper fdOper) throws Exception;
	/**
	 * 
	 *
	 * TODO ���ý���ʱ��ڵ���Ϣ
	 *
	 * @param webTradeTime
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��8��24��    	   ������  �½�
	 * </pre>
	 */
	public void reSetTradeTime(WebTradeTime webTradeTime);
}