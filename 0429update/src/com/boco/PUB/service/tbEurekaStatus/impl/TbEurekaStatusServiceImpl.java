package com.boco.PUB.service.tbEurekaStatus.impl;

import java.util.HashMap;

import com.boco.PUB.service.tbEurekaStatus.TbEurekaStatusService;
import com.boco.SYS.mapper.TbEurekaStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbEurekaStatus;
import com.boco.SYS.base.GenericService;

/**
 * 
 * 
 * esbע�����ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class TbEurekaStatusServiceImpl extends GenericService<TbEurekaStatus, String> implements TbEurekaStatusService {
	// ���Զ��巽��ʱʹ��
	@Autowired
	private TbEurekaStatusMapper mapper;
}