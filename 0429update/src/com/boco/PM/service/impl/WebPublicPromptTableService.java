package com.boco.PM.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.PM.service.IWebPublicPromptTableService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.cache.PromptMsgCache;
import com.boco.SYS.entity.WebPublicPromptTable;
import com.boco.SYS.mapper.WebPublicPromptTableMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.WebContextUtil;

/**
 * 
 * 
 * ������ʾ��||������ʾ��ҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
@Service
public class WebPublicPromptTableService extends GenericService<WebPublicPromptTable,java.lang.String> implements IWebPublicPromptTableService{
	//���Զ��巽��ʱʹ��
	@Autowired
	private WebPublicPromptTableMapper webPublicPromptTableMapper;
	@Autowired
	private IFdBusinessDateService fdBusinessDateService;

@Override
public void saveInfo(WebPublicPromptTable webPublicPromptTable) throws Exception {
	webPublicPromptTable.setId(BocoUtils.getUUID());
	webPublicPromptTable.setOperDate(fdBusinessDateService.getCommonDateDetails());
	webPublicPromptTable.setOperTime(BocoUtils.getNowTime());
	webPublicPromptTable.setOperCode(WebContextUtil.getSessionUser().getOpercode());
	webPublicPromptTable.setUseStatus(DicCache.getKeyByName_("����", "USE_STATUS"));//����
	webPublicPromptTableMapper.insertEntity(webPublicPromptTable);
}

@Override
public void refresh(WebPublicPromptTable webPublicPromptTable) throws Exception {
	List<WebPublicPromptTable> list=webPublicPromptTableMapper.selectPromptMsg();
	if (list.size()>0) {
		PromptMsgCache.setMsg(list);
	}else {
		PromptMsgCache.setMsg(null);
	}
}
}