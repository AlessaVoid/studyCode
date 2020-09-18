package com.boco.SYS.service.impl;

import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.MsgParam;
import com.boco.SYS.service.MsgParamService;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * 发送短信-参数表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class MsgParamServiceImpl extends GenericService<MsgParam, String> implements MsgParamService {
	//有自定义方法时使用
	//@Autowired
	//private MsgParamMapper mapper;
}