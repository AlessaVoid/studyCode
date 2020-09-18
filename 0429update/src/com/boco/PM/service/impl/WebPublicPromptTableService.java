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
 * 公共提示表||公共提示表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebPublicPromptTableService extends GenericService<WebPublicPromptTable,java.lang.String> implements IWebPublicPromptTableService{
	//有自定义方法时使用
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
	webPublicPromptTable.setUseStatus(DicCache.getKeyByName_("启用", "USE_STATUS"));//启用
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