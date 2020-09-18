package com.boco.SYS.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.boco.SYS.entity.ActicitiRoleConfig;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.service.IActicitiRoleConfigService;

/**
 * 
 * 
 * ActicitiRoleConfig业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class ActicitiRoleConfigService extends GenericService<ActicitiRoleConfig,HashMap<String,Object>> implements IActicitiRoleConfigService{
	//有自定义方法时使用
	//@Autowired
	//private ActicitiRoleConfigMapper mapper;
}