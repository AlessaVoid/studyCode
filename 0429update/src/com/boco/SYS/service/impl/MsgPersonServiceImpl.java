package com.boco.SYS.service.impl;

import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.MsgPerson;
import com.boco.SYS.mapper.MsgPersonMapper;
import com.boco.SYS.service.MsgPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 
 * 
 * ���Ͷ���-��Ա��ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2019-09-19      txn      �����½�
 * </pre>
 */
@Service
public class MsgPersonServiceImpl extends GenericService<MsgPerson,String> implements MsgPersonService {
	//���Զ��巽��ʱʹ��
	@Autowired
	private MsgPersonMapper mapper;
}