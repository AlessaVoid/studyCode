package com.boco.PUB.service.tbEurekaStatus.impl;

import java.util.HashMap;

import com.boco.PUB.service.tbEurekaStatus.TbEurekaStatusService;
import com.boco.SYS.mapper.TbEurekaStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.SYS.entity.TbEurekaStatus;
import com.boco.SYS.base.GenericService;

/**
 * 
 * 
 * esb注册控制业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2019-09-19      txn      批量新建
 * </pre>
 */
@Service
public class TbEurekaStatusServiceImpl extends GenericService<TbEurekaStatus, String> implements TbEurekaStatusService {
	// 有自定义方法时使用
	@Autowired
	private TbEurekaStatusMapper mapper;
}