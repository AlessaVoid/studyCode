package com.boco.PUB.service.loanPlanadj.impl;

import com.boco.PUB.service.loanPlanadj.TbPlanadjDetailService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbPlanadjDetail;
import com.boco.SYS.mapper.TbPlanadjDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * �Ŵ��ƻ��������������ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class TbPlanadjDetailServiceImpl extends GenericService<TbPlanadjDetail, String> implements TbPlanadjDetailService{
	//���Զ��巽��ʱʹ��
	@Autowired
	private TbPlanadjDetailMapper tbPlanadjDetailMapper;
}