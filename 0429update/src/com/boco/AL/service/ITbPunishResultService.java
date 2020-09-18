package com.boco.AL.service;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPunishResult;

import java.util.List;

/**
 * 
 * 
 * TbPunishResultҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface ITbPunishResultService extends IGenericService<TbPunishResult, Integer>{


    public int updateByListId(TbPunishResult tbPunishResult) ;



    /**
     * ��������ɾ��
     * @param tbPunishResult
     * @return
     */

    int deleteByAttr(TbPunishResult tbPunishResult);


   List<TbPunishResult> selectByListId(TbPunishResult tbPunishResult);

}