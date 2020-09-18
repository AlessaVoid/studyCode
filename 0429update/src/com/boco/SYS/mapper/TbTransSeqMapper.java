package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbTransSeq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * TbTransSeq���ݷ��ʲ�(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
public interface TbTransSeqMapper extends GenericMapper<TbTransSeq,HashMap<String,Object>>{
    /**
     * ��ѯ��ˮ��
     * @param tbTransSeq
     * @return
     */
    List<TbTransSeq> selectTbTransSeq(TbTransSeq tbTransSeq);

}