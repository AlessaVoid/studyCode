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
 * 发送短信-人员表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class MsgPersonServiceImpl extends GenericService<MsgPerson,String> implements MsgPersonService {
	//有自定义方法时使用
	@Autowired
	private MsgPersonMapper mapper;
}