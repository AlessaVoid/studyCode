package com.boco.PUB.service.tbCalculateThree.impl;


import com.boco.PUB.service.tbCalculateThree.ITbCalculateThreeProportionService;
import com.boco.SYS.mapper.TbCalculateThreeProportionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbCalculateThreeProportion;
import com.boco.SYS.base.GenericService;

/**
 * TbCalculateThreeProportionҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbCalculateThreeProportionService extends GenericService<TbCalculateThreeProportion, String> implements ITbCalculateThreeProportionService {

    //���Զ��巽��ʱʹ��
    @Autowired
    private TbCalculateThreeProportionMapper mapper;

    @Override
    public void deleteAll() {
        mapper.deleteAll();
    }

}