package com.boco.PUB.service.tbTransSeq;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbTransSeq;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * TbTransSeqҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbTransSeqService extends IGenericService<TbTransSeq,HashMap<String,Object>>{

    /**
     * ��ѯ��ˮ
     * @param tbTransSeq
     * @return
     */
    List<TbTransSeq> selectTbTransSeq(TbTransSeq tbTransSeq);

}