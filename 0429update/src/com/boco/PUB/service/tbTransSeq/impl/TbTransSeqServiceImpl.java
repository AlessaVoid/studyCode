package com.boco.PUB.service.tbTransSeq.impl;

import com.boco.PUB.service.tbTransSeq.TbTransSeqService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbTransSeq;
import com.boco.SYS.mapper.TbTransSeqMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * TbTransSeqҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbTransSeqServiceImpl extends GenericService<TbTransSeq,HashMap<String,Object>> implements TbTransSeqService {

    @Autowired
    private TbTransSeqMapper tbTransSeqMapper;


    @Override
    public List<TbTransSeq> selectTbTransSeq(TbTransSeq tbTransSeq) {
        List<TbTransSeq> list = tbTransSeqMapper.selectTbTransSeq(tbTransSeq);
        return list;
    }
}