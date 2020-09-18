package com.boco.PUB.service.tbCalculateOne.impl;

import com.boco.PUB.service.tbCalculateOne.ITbCalculateOneProportionService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbCalculateOneProportion;
import com.boco.SYS.mapper.TbCalculateOneProportionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * ���� Ȩ�ر�ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class TbCalculateOneProportionService extends GenericService<TbCalculateOneProportion, String> implements ITbCalculateOneProportionService {
    //���Զ��巽��ʱʹ��
    @Autowired
    private TbCalculateOneProportionMapper mapper;


    @Override
    public void deleteAll() throws DataAccessException {
        mapper.deleteAll();
    }
}