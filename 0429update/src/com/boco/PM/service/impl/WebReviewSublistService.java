package com.boco.PM.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebReviewSublistService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.WebReviewSublist;
import com.boco.SYS.mapper.WebReviewSublistMapper;

/**
 * 
 * 
 * WebReviewSublistҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebReviewSublistService extends GenericService<WebReviewSublist,HashMap<String,Object>> implements IWebReviewSublistService{
	//���Զ��巽��ʱʹ��
	@Autowired
	private WebReviewSublistMapper webReviewSublistMapper;

	@Override
	public List<WebReviewSublist> selectAppNo(String appNo) throws Exception {
		return webReviewSublistMapper.selectAppNo(appNo);
	}
}