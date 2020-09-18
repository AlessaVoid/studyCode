package com.boco.PM.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.boco.PM.service.IFdUnichangeinfoService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.FdUnichangeinfo;

/**
 * 
 * 
 * FdUnichangeinfo业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class FdUnichangeinfoService extends GenericService<FdUnichangeinfo,HashMap<String,Object>> implements IFdUnichangeinfoService{
	//有自定义方法时使用
	//@Autowired
	//private FdUnichangeinfoMapper mapper;
}