package com.boco.SYS.service;

import java.util.HashMap;

import com.boco.SYS.entity.MsgTemplate;
import com.boco.SYS.base.IGenericService;

/**
 * 
 * 
 * 短信模板业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
public interface MsgTemplateService extends IGenericService<MsgTemplate, String>{
}