package com.boco.PUB.service.tbCalculateOne.impl;

import java.util.HashMap;

import com.boco.PUB.service.tbCalculateOne.ITbCalculateOneRankService;
import com.boco.SYS.mapper.TbCalculateOneRankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbCalculateOneRank;
import com.boco.SYS.base.GenericService;

/**
 * 
 * 
 * TbCalculateOneRankҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbCalculateOneRankService extends GenericService<TbCalculateOneRank, String> implements ITbCalculateOneRankService {
	//���Զ��巽��ʱʹ��
	@Autowired
	private TbCalculateOneRankMapper tbCalculateOneRankMapper;
}