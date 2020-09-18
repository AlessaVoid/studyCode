package com.boco.SYS.service;

import java.util.HashMap;

import com.boco.SYS.entity.FdBusinessDate;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * FdBusinessDateҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ���     �����½�
 * </pre>
 */
public interface IFdBusinessDateService extends IGenericService<FdBusinessDate,HashMap<String,Object>>{
    /**
     * ��ͨҵ��ʱ��
     * @return
     */
    public String getCommonDateDetails();

    /**
     * �����Ͳ�Ʒʱ��
     * @return
     */
    public String getCurrencyDateDetails();
    /**
     * ��ÿ�����״̬��ʶ
     * @return
     */
    public String getBusinessFlag();
    /**
     * ��ȡ����ʱ�����
     */
    public FdBusinessDate getCommonFdBusinessDate();
    /**
     * ��ȡ�����Ͳ�Ʒʱ�����
     */
    public FdBusinessDate getCurrencyFdBusinessDate();
    /**
	 * TODO �����ֵ�����.
	 *
	 */
	public void RestartParam() ;
}