package com.boco.SYS.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.boco.SYS.entity.GfErrInfo;
import com.boco.SYS.service.IGfErrInfoService;
import com.boco.SYS.base.GenericService;

/**
 * 
 * 
 * GfErrInfo业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class GfErrInfoService extends GenericService<GfErrInfo,HashMap<String,Object>> implements IGfErrInfoService{
	//有自定义方法时使用
	//@Autowired
	//private GfErrInfoMapper mapper;
}