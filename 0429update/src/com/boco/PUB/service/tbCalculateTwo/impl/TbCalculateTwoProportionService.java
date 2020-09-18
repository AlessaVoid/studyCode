package com.boco.PUB.service.tbCalculateTwo.impl;

import java.util.HashMap;

import com.boco.PUB.service.tbCalculateTwo.ITbCalculateTwoProportionService;
import com.boco.SYS.mapper.TbCalculateTwoProportionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbCalculateTwoProportion;
import com.boco.SYS.base.GenericService;

/**
 * 
 * 
 * ����� Ȩ�ز������ñ�ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbCalculateTwoProportionService extends GenericService<TbCalculateTwoProportion, String> implements ITbCalculateTwoProportionService {
	//���Զ��巽��ʱʹ��
	@Autowired
	private TbCalculateTwoProportionMapper mapper;

    @Override
    public void deleteAll() {
        mapper.deleteAll();
    }
}